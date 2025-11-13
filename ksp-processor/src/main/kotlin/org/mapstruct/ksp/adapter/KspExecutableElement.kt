// ABOUTME: Adapter for KSP function declarations to javax.lang.model ExecutableElement
// ABOUTME: Bridges KSP method/function representation to Java annotation processing API for MapStruct processor
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.Modifier as KspModifier
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ElementVisitor
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.element.Name
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror

class KspExecutableElement(
    val declaration: KSFunctionDeclaration,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : ExecutableElement {

    override fun getTypeParameters(): List<TypeParameterElement> {
        return declaration.typeParameters.map { typeParam ->
            KspTypeParameterElement(typeParam, resolver, logger)
        }
    }

    override fun getReturnType(): TypeMirror {
        val returnType = declaration.returnType?.resolve()
        if (returnType != null) {
            val decl = returnType.declaration
            if (decl is com.google.devtools.ksp.symbol.KSClassDeclaration) {
                return KspTypeMirror(
                    KspClassTypeElement(decl, resolver, logger),
                    resolver,
                    logger
                )
            }
        }
        return KspNoType(javax.lang.model.type.TypeKind.NONE)
    }

    override fun getParameters(): List<VariableElement> {
        return declaration.parameters.map { param ->
            KspVariableElement(param, resolver, logger)
        }
    }

    override fun getReceiverType(): TypeMirror? {
        val extensionReceiver = declaration.extensionReceiver
        if (extensionReceiver != null) {
            val resolved = extensionReceiver.resolve()
            val decl = resolved.declaration
            if (decl is com.google.devtools.ksp.symbol.KSClassDeclaration) {
                return KspTypeMirror(
                    KspClassTypeElement(decl, resolver, logger),
                    resolver,
                    logger
                )
            }
        }
        return null
    }

    override fun isVarArgs(): Boolean {
        return declaration.parameters.any { it.isVararg }
    }

    override fun isDefault(): Boolean {
        return false
    }

    override fun getThrownTypes(): List<TypeMirror> {
        return emptyList()
    }

    override fun getDefaultValue(): AnnotationValue? {
        val parent = declaration.parentDeclaration
        if (parent is com.google.devtools.ksp.symbol.KSClassDeclaration &&
            parent.classKind == com.google.devtools.ksp.symbol.ClassKind.ANNOTATION_CLASS) {

            val methodName = declaration.simpleName.asString()
            val returnType = declaration.returnType?.resolve()

            if (returnType != null) {
                val returnTypeDecl = returnType.declaration
                val qualifiedName = returnTypeDecl.qualifiedName?.asString()

                val defaultValue: Any? = when {
                    qualifiedName == "java.lang.Class" || qualifiedName == "kotlin.reflect.KClass" -> {
                        try {
                            val voidClass = resolver.getClassDeclarationByName(resolver.getKSNameFromString("java.lang.Void"))
                            if (voidClass != null) {
                                KspTypeMirror(KspClassTypeElement(voidClass, resolver, logger), resolver, logger)
                            } else {
                                KspNoType(javax.lang.model.type.TypeKind.VOID)
                            }
                        } catch (e: Exception) {
                            KspNoType(javax.lang.model.type.TypeKind.VOID)
                        }
                    }
                    qualifiedName == "java.lang.String" || qualifiedName == "kotlin.String" -> ""
                    returnType.isMarkedNullable -> null
                    returnTypeDecl is com.google.devtools.ksp.symbol.KSClassDeclaration &&
                        returnTypeDecl.classKind == com.google.devtools.ksp.symbol.ClassKind.ENUM_CLASS -> {
                        null
                    }
                    returnType.arguments.isNotEmpty() -> {
                        emptyList<Any>()
                    }
                    else -> null
                }

                return if (defaultValue != null) KspAnnotationValue(defaultValue, resolver, logger) else null
            }
        }

        return null
    }

    override fun getSimpleName(): Name {
        return StringName(declaration.simpleName.asString())
    }

    override fun getEnclosingElement(): Element {
        val parent = declaration.parentDeclaration
        return when (parent) {
            is com.google.devtools.ksp.symbol.KSClassDeclaration ->
                KspClassTypeElement(parent, resolver, logger)
            else -> error("Unexpected parent type for function: ${parent!!::class.simpleName}")
        }
    }

    override fun getEnclosedElements(): List<Element> {
        return emptyList()
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

    override fun asType(): TypeMirror {
        return getReturnType()
    }

    override fun getKind(): ElementKind {
        return ElementKind.METHOD
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

    override fun <R : Any?, P : Any?> accept(v: ElementVisitor<R?, P?>?, p: P?): R? {
        return v?.visitExecutable(this, p)
    }

    override fun toString(): String = "KspExecutableElement[${declaration.simpleName.asString()}]"
}
