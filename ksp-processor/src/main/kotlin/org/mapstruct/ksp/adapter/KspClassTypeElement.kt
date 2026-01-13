package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSName
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.Modifier as KspModifier
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ElementVisitor
import javax.lang.model.element.Modifier
import javax.lang.model.element.Name
import javax.lang.model.element.NestingKind
import javax.lang.model.element.RecordComponentElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.type.TypeMirror

class KspClassTypeElement(
    val declaration: KSClassDeclaration,
    private val resolver: Resolver,
    private val logger: KSPLogger,
    private val sourceElement: KSNode? = null
) : TypeElement {

    override fun equals(other: Any?): Boolean {
        if (other !is KspClassTypeElement) return false
        // Compare by qualified name since KSP may create different KSClassDeclaration instances
        // for the same class across different resolution contexts
        return declaration.qualifiedName?.asString() == other.declaration.qualifiedName?.asString()
    }

    override fun hashCode(): Int {
        // Use qualified name for hash code to match equals()
        return declaration.qualifiedName?.asString()?.hashCode() ?: 0
    }

    override fun asType(): TypeMirror {
        return KspTypeMirror(this, resolver, logger)
    }

    private val _enclosedElements: List<Element> by lazy {
        val elements = mutableListOf<Element>()
        val declarations = declaration.declarations.toList()
        val source = sourceElement as? KSAnnotation

        for (child in declarations) {
            when (child) {
                is KSPropertyDeclaration -> {
                    if (declaration.classKind in setOf(ClassKind.ENUM_ENTRY, ClassKind.ANNOTATION_CLASS)) {
                        elements.add(KspExecutableElement(child, resolver, logger, source))
                    } else {
                        elements.add(KspVariableElement(child, resolver, logger))

                        child.getter?.let { getter ->
                            elements.add(KspPropertyAccessorExecutableElement(child, getter, resolver, logger))
                        }

                        child.setter?.let { setter ->
                            elements.add(KspPropertyAccessorExecutableElement(child, setter, resolver, logger))
                        }
                    }
                }

                is KSFunctionDeclaration -> {
                    elements.add(KspExecutableElement(child, resolver, logger))
                }

                is KSClassDeclaration -> {
                    elements.add(KspClassTypeElement(child, resolver, logger, sourceElement))
                }

                else -> error("Unexpected child type for enclosed element: ${child::class.simpleName}")
            }
        }

        elements
    }

    override fun getEnclosedElements(): List<Element> = _enclosedElements

    override fun getNestingKind(): NestingKind {
        return when {
            declaration.parentDeclaration is KSClassDeclaration -> NestingKind.MEMBER
            else -> NestingKind.TOP_LEVEL
        }
    }

    override fun getQualifiedName(): Name? {
        val name: KSName = declaration.qualifiedName ?: return null
        val qualifiedName = name.asString()
        val javaQualifiedName = KotlinToJavaTypeMapper.mapToJavaType(qualifiedName)
        return StringName(javaQualifiedName)
    }

    override fun getSimpleName(): Name {
        return StringName(declaration.simpleName.asString())
    }

    override fun getSuperclass(): TypeMirror {
        val superTypes = declaration.superTypes.toList()
        val superClass = superTypes.firstOrNull { superTypeRef ->
            val resolved = superTypeRef.resolve()
            val decl = resolved.declaration
            decl is KSClassDeclaration && decl.classKind == ClassKind.CLASS
        }

        return if (superClass != null) {
            val resolved = superClass.resolve()
            val decl = resolved.declaration as KSClassDeclaration
            KspTypeMirror(KspClassTypeElement(decl, resolver, logger), resolver, logger)
        } else {
            KspNoType(javax.lang.model.type.TypeKind.NONE)
        }
    }

    private val _interfaces: List<TypeMirror> by lazy {
        val interfaces = mutableListOf<TypeMirror>()

        declaration.superTypes.forEach { superTypeRef ->
            val resolved = superTypeRef.resolve()
            val decl = resolved.declaration
            if (decl is KSClassDeclaration && decl.classKind == ClassKind.INTERFACE) {
                interfaces.add(
                    KspTypeMirror(
                        KspClassTypeElement(decl, resolver, logger),
                        resolver,
                        logger
                    )
                )
            }
        }

        interfaces
    }

    override fun getInterfaces(): List<TypeMirror> = _interfaces

    private val _typeParameters: List<TypeParameterElement> by lazy {
        declaration.typeParameters.map { typeParam ->
            KspTypeParameterElement(typeParam, resolver, logger)
        }
    }

    override fun getTypeParameters(): List<TypeParameterElement> = _typeParameters

    private val _enclosingElement: Element? by lazy {
        when (val parent = declaration.parentDeclaration) {
            is KSClassDeclaration -> KspClassTypeElement(parent, resolver, logger)
            else -> null
        }
    }

    override fun getEnclosingElement(): Element? = _enclosingElement

    override fun getKind(): ElementKind {
        return when (declaration.classKind) {
            ClassKind.ANNOTATION_CLASS -> ElementKind.ANNOTATION_TYPE
            ClassKind.INTERFACE -> ElementKind.INTERFACE
            ClassKind.ENUM_CLASS -> ElementKind.ENUM
            else -> ElementKind.CLASS
        }
    }

    override fun getModifiers(): Set<Modifier> {
        val modifiers = mutableSetOf<Modifier>()

        if (KspModifier.PUBLIC in declaration.modifiers) modifiers.add(Modifier.PUBLIC)
        if (KspModifier.PRIVATE in declaration.modifiers) modifiers.add(Modifier.PRIVATE)
        if (KspModifier.PROTECTED in declaration.modifiers) modifiers.add(Modifier.PROTECTED)
        if (KspModifier.ABSTRACT in declaration.modifiers) modifiers.add(Modifier.ABSTRACT)
        if (KspModifier.FINAL in declaration.modifiers) modifiers.add(Modifier.FINAL)

        return modifiers
    }

    private val _annotationMirrors: List<AnnotationMirror> by lazy {
        toAnnotationMirrors(declaration.annotations.toList(), resolver, logger)
    }

    override fun getAnnotationMirrors(): List<AnnotationMirror> = _annotationMirrors

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? {
        if (annotationType == null) return null

        val targetName = annotationType.name
        val annotation = declaration.annotations.firstOrNull { anno ->
            anno.annotationType.resolve().declaration.qualifiedName?.asString() == targetName
        } ?: return null

        @Suppress("UNCHECKED_CAST")
        return AnnotationBuilder.buildAnnotation(
            annotation,
            annotationType as Class<out Annotation>,
            resolver,
            logger
        ) as? A
    }

    override fun <A : Annotation?> getAnnotationsByType(annotationType: Class<A?>?): Array<A?> {
        val annotation = getAnnotation(annotationType)
        @Suppress("UNCHECKED_CAST")
        return if (annotation != null) {
            arrayOf(annotation) as Array<A?>
        } else {
            arrayOfNulls<Annotation>(0) as Array<A?>
        }
    }

    override fun <R, P> accept(
        v: ElementVisitor<R?, P?>?,
        p: P?
    ): R? {
        return v?.visitType(this, p)
    }

    override fun toString(): String = "KspClassTypeElement[${declaration.qualifiedName?.asString()}]"
}

