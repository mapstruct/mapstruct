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
        if (other.sourceElement != sourceElement) return false
        return declaration == other.declaration
    }

    override fun hashCode(): Int {
        return declaration.hashCode()
    }

    override fun asType(): TypeMirror {
        return KspTypeMirror(this, resolver, logger)
    }

    override fun getEnclosedElements(): List<Element> {
        val elements = mutableListOf<Element>()

        // For annotation classes, properties are actually annotation attributes (methods)
        if (declaration.classKind == ClassKind.ANNOTATION_CLASS) {
            declaration.getAllProperties().forEach { property: KSPropertyDeclaration ->
                val annotation = sourceElement as? KSAnnotation
                elements.add(KspAnnotationAttributeElement(property, annotation, resolver, logger))
            }
        } else {
            // For regular classes, return methods and fields
            declaration.getAllFunctions().forEach { function: KSFunctionDeclaration ->
                elements.add(KspExecutableElement(function, resolver, logger))
            }

            declaration.getAllProperties().forEach { property: KSPropertyDeclaration ->
                elements.add(KspVariableElement(property, resolver, logger))
            }
        }

        return elements
    }

    override fun getNestingKind(): NestingKind {
        return when {
            declaration.parentDeclaration is KSClassDeclaration -> NestingKind.MEMBER
            else -> NestingKind.TOP_LEVEL
        }
    }

    override fun getQualifiedName(): Name? {
        val name: KSName = declaration.qualifiedName ?: return null
        return StringName(name.asString())
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

    override fun getInterfaces(): List<TypeMirror> {
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

        return interfaces
    }

    override fun getTypeParameters(): List<TypeParameterElement> {
        return declaration.typeParameters.map { typeParam ->
            KspTypeParameterElement(typeParam, resolver, logger)
        }
    }

    override fun getEnclosingElement(): Element? {
        val parent = declaration.parentDeclaration
        return when (parent) {
            is KSClassDeclaration -> KspClassTypeElement(parent, resolver, logger)
            else -> null
        }
    }

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

    override fun getAnnotationMirrors(): List<AnnotationMirror> {
        return declaration.annotations.map { annotation ->
            KspAnnotationMirror(annotation, resolver, logger)
        }.toList()
    }

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? {
        if (annotationType == null) return null

        val targetName = annotationType.name
        val annotation = declaration.annotations.firstOrNull { anno ->
            anno.annotationType.resolve().declaration.qualifiedName?.asString() == targetName
        } ?: return null

        @Suppress("UNCHECKED_CAST")
        return AnnotationBuilder.buildAnnotation(annotation, annotationType as Class<out Annotation>, resolver, logger) as? A
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

