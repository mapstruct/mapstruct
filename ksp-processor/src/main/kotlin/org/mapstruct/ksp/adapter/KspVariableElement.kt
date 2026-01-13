// ABOUTME: Adapter for KSP parameters and properties to javax.lang.model VariableElement
// ABOUTME: Bridges KSP variable representation to Java annotation processing API for MapStruct processor
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.Modifier as KspModifier
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ElementVisitor
import javax.lang.model.element.Modifier
import javax.lang.model.element.Name
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror

class KspVariableElement : VariableElement {
    private val annotated: KSAnnotated
    private val resolver: Resolver
    private val logger: KSPLogger
    private val name: String
    private val typeMirror: TypeMirror

    companion object {
        // Cache primitive type instances for reuse
        private val primitiveTypeCache = mutableMapOf<TypeKind, KspPrimitiveType>()

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
                return KspNoType(TypeKind.NONE)
            }

            // Check if this is a Kotlin built-in primitive type
            // BUT: Only use primitive if NOT nullable (nullable types must be boxed in Java)
            val builtins = resolver.builtIns
            val starProjectedType = decl.asStarProjectedType()
            val isNullable = ksType.isMarkedNullable

            val primitiveKind = if (!isNullable) {
                when (starProjectedType) {
                    builtins.booleanType -> TypeKind.BOOLEAN
                    builtins.byteType -> TypeKind.BYTE
                    builtins.shortType -> TypeKind.SHORT
                    builtins.intType -> TypeKind.INT
                    builtins.longType -> TypeKind.LONG
                    builtins.charType -> TypeKind.CHAR
                    builtins.floatType -> TypeKind.FLOAT
                    builtins.doubleType -> TypeKind.DOUBLE
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

    constructor(parameter: KSValueParameter, resolver: Resolver, logger: KSPLogger) {
        this.annotated = parameter
        this.resolver = resolver
        this.logger = logger
        this.name = parameter.name?.asString() ?: ""

        val paramType = parameter.type.resolve()
        this.typeMirror = createTypeMirrorForType(paramType, resolver, logger)
    }

    constructor(property: KSPropertyDeclaration, resolver: Resolver, logger: KSPLogger) {
        this.annotated = property
        this.resolver = resolver
        this.logger = logger
        this.name = property.simpleName.asString()

        val propType = property.type.resolve()
        this.typeMirror = createTypeMirrorForType(propType, resolver, logger)
    }

    override fun getConstantValue(): Any? = null

    override fun getSimpleName(): Name = StringName(name)

    override fun getEnclosingElement(): Element? {
        return when (val item = annotated) {
            is KSValueParameter -> {
                val funcDecl = item.parent as? com.google.devtools.ksp.symbol.KSFunctionDeclaration
                funcDecl?.let { KspExecutableElement(it, resolver, logger) }
            }
            is KSPropertyDeclaration -> {
                val parent = item.parentDeclaration
                when (parent) {
                    is com.google.devtools.ksp.symbol.KSClassDeclaration ->
                        KspClassTypeElement(parent, resolver, logger)
                    else -> null
                }
            }
            else -> null
        }
    }

    override fun getEnclosedElements(): List<Element> = emptyList()

    override fun asType(): TypeMirror = typeMirror

    override fun getKind(): ElementKind {
        return when (annotated) {
            is KSValueParameter -> ElementKind.PARAMETER
            is KSPropertyDeclaration -> ElementKind.FIELD
            else -> ElementKind.OTHER
        }
    }

    override fun getModifiers(): Set<Modifier> {
        val modifiers = mutableSetOf<Modifier>()

        when (val item = annotated) {
            is KSPropertyDeclaration -> {
                if (KspModifier.PUBLIC in item.modifiers) modifiers.add(Modifier.PUBLIC)
                if (KspModifier.PRIVATE in item.modifiers) modifiers.add(Modifier.PRIVATE)
                if (KspModifier.PROTECTED in item.modifiers) modifiers.add(Modifier.PROTECTED)
                if (KspModifier.FINAL in item.modifiers) modifiers.add(Modifier.FINAL)
            }
        }

        return modifiers
    }

    private val _annotationMirrors by lazy {
        toAnnotationMirrors(annotated.annotations.toList(), resolver, logger)
    }

    override fun getAnnotationMirrors(): List<AnnotationMirror> = _annotationMirrors

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? {
        if (annotationType == null) return null

        val targetName = annotationType.name
        val annotation = annotated.annotations.firstOrNull { anno ->
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

    override fun <R : Any?, P : Any?> accept(v: ElementVisitor<R?, P?>?, p: P?): R? {
        return v?.visitVariable(this, p)
    }

    override fun toString(): String = "KspVariableElement[$name]"
}
