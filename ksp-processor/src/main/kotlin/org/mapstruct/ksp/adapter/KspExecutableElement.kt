// ABOUTME: Adapter for KSP function declarations to javax.lang.model ExecutableElement
// ABOUTME: Bridges KSP method/function representation to Java annotation processing API for MapStruct processor
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.isOpen
import com.google.devtools.ksp.isPrivate
import com.google.devtools.ksp.isProtected
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
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
    val declaration: KSDeclaration,
    private val resolver: Resolver,
    private val logger: KSPLogger,
    /**
     * Optional annotation instance. Only needed when [declaration] is a [KSPropertyDeclaration]
     * that represents an annotation attribute (Java method in annotation type). KSP exposes
     * default values for such attributes via `KSAnnotation.defaultArguments`.
     */
    private val annotationInstance: KSAnnotation? = null
) : ExecutableElement {

    override fun getTypeParameters(): List<TypeParameterElement> {
        return when (declaration) {
            is KSFunctionDeclaration -> declaration.typeParameters.map { typeParam ->
                KspTypeParameterElement(typeParam, resolver, logger)
            }

            is KSPropertyDeclaration -> emptyList()
            else -> emptyList()
        }
    }

    override fun getReturnType(): TypeMirror {
        return when (declaration) {
            is KSFunctionDeclaration -> getReturnTypeFromKSFunctionDeclaration()
            is KSPropertyDeclaration -> getReturnTypeFromKSPropertyDeclaration()
            else -> error("Unexpected declaration type for executable element: ${declaration::class.simpleName}")
        }
    }

    private fun getReturnTypeFromKSFunctionDeclaration(): TypeMirror {
        check(declaration is KSFunctionDeclaration)

        val returnType = declaration.returnType?.resolve()
        if (returnType != null) {
            return createTypeMirrorForType(returnType, resolver, logger)
        }
        return KspNoType(javax.lang.model.type.TypeKind.NONE)
    }

    private fun getReturnTypeFromKSPropertyDeclaration(): TypeMirror {
        check(declaration is KSPropertyDeclaration)

        val propType = declaration.type.resolve()
        return createTypeMirrorForType(propType, resolver, logger)
    }

    companion object {
        // Cache primitive type instances for reuse
        private val primitiveTypeCache = mutableMapOf<javax.lang.model.type.TypeKind, KspPrimitiveType>()

        /**
         * Creates appropriate TypeMirror for a KSType, handling primitive types.
         * Kotlin primitive types (Int, Long, etc.) are represented as their Java boxed equivalents
         * in KSP, but MapStruct needs primitive types for non-nullable Kotlin primitives.
         * Nullable Kotlin primitives (Boolean?, Int?, etc.) must be boxed in Java.
         */
        private fun createTypeMirrorForType(
            ksType: com.google.devtools.ksp.symbol.KSType,
            resolver: Resolver,
            logger: KSPLogger
        ): TypeMirror {
            val decl = ksType.declaration

            if (decl !is com.google.devtools.ksp.symbol.KSClassDeclaration) {
                return KspNoType(javax.lang.model.type.TypeKind.NONE)
            }

            // Check if this is a Kotlin built-in primitive type
            // BUT: Only use primitive if NOT nullable (nullable types must be boxed in Java)
            val builtins = resolver.builtIns
            val starProjectedType = decl.asStarProjectedType()
            val isNullable = ksType.isMarkedNullable

            val primitiveKind = if (!isNullable) {
                when (starProjectedType) {
                    builtins.booleanType -> javax.lang.model.type.TypeKind.BOOLEAN
                    builtins.byteType -> javax.lang.model.type.TypeKind.BYTE
                    builtins.shortType -> javax.lang.model.type.TypeKind.SHORT
                    builtins.intType -> javax.lang.model.type.TypeKind.INT
                    builtins.longType -> javax.lang.model.type.TypeKind.LONG
                    builtins.charType -> javax.lang.model.type.TypeKind.CHAR
                    builtins.floatType -> javax.lang.model.type.TypeKind.FLOAT
                    builtins.doubleType -> javax.lang.model.type.TypeKind.DOUBLE
                    else -> null
                }
            } else {
                null // Nullable types are always boxed
            }

            return if (primitiveKind != null) {
                // Return cached instance to ensure identity equality
                primitiveTypeCache.getOrPut(primitiveKind) { KspPrimitiveType(primitiveKind) }
            } else {
                KspTypeMirror(
                    KspClassTypeElement(decl, resolver, logger),
                    resolver,
                    logger
                )
            }
        }
    }

    override fun getParameters(): List<VariableElement> {
        return when (declaration) {
            is KSFunctionDeclaration -> declaration.parameters.map { param ->
                KspVariableElement(param, resolver, logger)
            }

            is KSPropertyDeclaration -> emptyList()
            else -> emptyList()
        }
    }

    override fun getReceiverType(): TypeMirror? {
        return when (declaration) {
            is KSFunctionDeclaration -> {
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
                null
            }

            else -> null
        }
    }

    override fun isVarArgs(): Boolean {
        return when (declaration) {
            is KSFunctionDeclaration -> declaration.parameters.any { it.isVararg }
            else -> false
        }
    }

    override fun isDefault(): Boolean {
        return false
    }

    override fun getThrownTypes(): List<TypeMirror> {
        return emptyList()
    }

    override fun getDefaultValue(): AnnotationValue? {
        return when (val decl = declaration) {
            is KSFunctionDeclaration -> {
                val parent = decl.parentDeclaration
                if (parent is com.google.devtools.ksp.symbol.KSClassDeclaration &&
                    parent.classKind == com.google.devtools.ksp.symbol.ClassKind.ANNOTATION_CLASS
                ) {

                    val returnType = decl.returnType?.resolve()

                    if (returnType != null) {
                        val returnTypeDecl = returnType.declaration
                        val qualifiedName = returnTypeDecl.qualifiedName?.asString()

                        val defaultValue: Any? = when {
                            qualifiedName == "java.lang.Class" || qualifiedName == "kotlin.reflect.KClass" -> {
                                try {
                                    val voidClass =
                                        resolver.getClassDeclarationByName(resolver.getKSNameFromString("java.lang.Void"))
                                    if (voidClass != null) {
                                        KspTypeMirror(
                                            KspClassTypeElement(voidClass, resolver, logger),
                                            resolver,
                                            logger
                                        )
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
                null
            }

            is KSPropertyDeclaration -> {
                // Default value for annotation attribute represented as property comes from the annotation instance
                val instance = annotationInstance
                    ?: return null

                val propertyName = decl.simpleName.asString()
                val defaultArg = instance.defaultArguments.firstOrNull { it.name?.asString() == propertyName }
                if (defaultArg != null) {
                    KspAnnotationValue(defaultArg.value, resolver, logger)
                } else null
            }

            else -> null
        }
    }

    override fun getSimpleName(): Name = StringName(declaration.simpleName.asString())

    override fun getEnclosingElement(): Element {
        val parent = declaration.parentDeclaration
        return when (parent) {
            is com.google.devtools.ksp.symbol.KSClassDeclaration ->
                KspClassTypeElement(parent, resolver, logger)

            else -> error("Unexpected parent type for declaration: ${parent!!::class.simpleName}")
        }
    }

    override fun getEnclosedElements(): List<Element> {
        return emptyList()
    }

    private val _annotationMirrors by lazy {
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

    override fun asType(): TypeMirror = getReturnType()

    override fun getKind(): ElementKind {
        return when (val decl = declaration) {
            is KSFunctionDeclaration -> {
                if ("<init>" in decl.simpleName.asString()) ElementKind.CONSTRUCTOR else ElementKind.METHOD
            }

            is KSPropertyDeclaration -> {
                val parentKind =
                    (decl.parentDeclaration as? com.google.devtools.ksp.symbol.KSClassDeclaration)?.classKind
                when (parentKind) {
                    com.google.devtools.ksp.symbol.ClassKind.ANNOTATION_CLASS -> ElementKind.METHOD
                    com.google.devtools.ksp.symbol.ClassKind.ENUM_CLASS -> ElementKind.METHOD
                    com.google.devtools.ksp.symbol.ClassKind.CLASS -> ElementKind.FIELD
                    else -> ElementKind.METHOD
                }
            }

            else -> ElementKind.METHOD
        }
    }

    override fun getModifiers(): Set<Modifier> {
        val modifiers = mutableSetOf<Modifier>()

        when (val decl = declaration) {
            is KSFunctionDeclaration -> {
                if (decl.isPublic()) modifiers.add(Modifier.PUBLIC)
                if (decl.isPrivate()) modifiers.add(Modifier.PRIVATE)
                if (decl.isProtected()) modifiers.add(Modifier.PROTECTED)

                val isAbstract = decl.isAbstract
                if (isAbstract) modifiers.add(Modifier.ABSTRACT)
                if (!decl.isOpen() && !isAbstract) modifiers.add(Modifier.FINAL)
            }

            is KSPropertyDeclaration -> {
                if (decl.isPublic()) modifiers.add(Modifier.PUBLIC)
                if (decl.isPrivate()) modifiers.add(Modifier.PRIVATE)
                if (decl.isProtected()) modifiers.add(Modifier.PROTECTED)

                // KSP doesn't expose `isAbstract`/`isOpen` as extensions on KSPropertyDeclaration, use modifiers
                val mods = decl.modifiers
                if (com.google.devtools.ksp.symbol.Modifier.ABSTRACT in mods) modifiers.add(Modifier.ABSTRACT)
                if (com.google.devtools.ksp.symbol.Modifier.FINAL in mods) modifiers.add(Modifier.FINAL)
            }

            else -> {}
        }

        return modifiers
    }

    override fun <R : Any?, P : Any?> accept(v: ElementVisitor<R?, P?>?, p: P?): R? = v?.visitExecutable(this, p)

    override fun toString(): String = "KspExecutableElement[${declaration.simpleName.asString()}]"
}
