// ABOUTME: Adapter for synthetic property accessor methods (getters/setters) to ExecutableElement
// ABOUTME: Enables Kotlin-Java interop by exposing property accessors as executable methods
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.isOpen
import com.google.devtools.ksp.isPrivate
import com.google.devtools.ksp.isProtected
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyAccessor
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
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

/**
 * Represents a synthetic getter or setter method for a Kotlin property.
 * This enables Kotlin-Java interop by exposing property accessors as executable methods
 * that Java code (and MapStruct processor) can discover and use.
 */
class KspPropertyAccessorExecutableElement(
    private val property: KSPropertyDeclaration,
    private val accessor: KSPropertyAccessor,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : ExecutableElement {

    private val isGetter = accessor == property.getter
    private val isSetter = accessor == property.setter

    companion object {
        // Cache primitive type instances for reuse
        private val primitiveTypeCache = mutableMapOf<javax.lang.model.type.TypeKind, KspPrimitiveType>()

        /**
         * Creates appropriate TypeMirror for a property type, handling primitive types.
         * This ensures consistency with KspVariableElement's type handling.
         */
        private fun createTypeMirrorForProperty(
            property: KSPropertyDeclaration,
            resolver: Resolver,
            logger: KSPLogger
        ): TypeMirror {
            val propType = property.type.resolve()
            val decl = propType.declaration

            if (decl !is KSClassDeclaration) {
                return KspNoType(javax.lang.model.type.TypeKind.NONE)
            }

            // Check if this is a Kotlin built-in primitive type
            // BUT: Only use primitive if NOT nullable (nullable types must be boxed in Java)
            val builtins = resolver.builtIns
            val ksType = decl.asStarProjectedType()
            val isNullable = propType.isMarkedNullable

            val primitiveKind = if (!isNullable) {
                when (ksType) {
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

    override fun getTypeParameters(): List<TypeParameterElement> {
        // Property accessors don't have type parameters
        return emptyList()
    }

    override fun getReturnType(): TypeMirror {
        return if (isGetter) {
            // Getter returns the property type
            createTypeMirrorForProperty(property, resolver, logger)
        } else {
            // Setter returns void
            KspNoType(javax.lang.model.type.TypeKind.VOID)
        }
    }

    override fun getParameters(): List<VariableElement> {
        return if (isSetter) {
            // Setter has one parameter of the property type
            val paramType = createTypeMirrorForProperty(property, resolver, logger)

            // Create a synthetic parameter element for the setter
            listOf(KspSyntheticParameterElement(property.simpleName.asString(), paramType, resolver, logger))
        } else {
            // Getter has no parameters
            emptyList()
        }
    }

    override fun getReceiverType(): TypeMirror? {
        // Kotlin extension receivers are not applicable to regular property accessors
        return null
    }

    override fun isVarArgs(): Boolean {
        return false
    }

    override fun isDefault(): Boolean {
        return false
    }

    override fun getThrownTypes(): List<TypeMirror> {
        return emptyList()
    }

    override fun getDefaultValue(): AnnotationValue? {
        return null
    }

    override fun getSimpleName(): Name {
        // Generate Java-style accessor name
        val propertyName = property.simpleName.asString()
        val accessorName = if (isGetter) {
            // Convert property name to getPropertyName format
            "get${propertyName.replaceFirstChar { it.uppercase() }}"
        } else {
            // Convert property name to setPropertyName format
            "set${propertyName.replaceFirstChar { it.uppercase() }}"
        }
        return StringName(accessorName)
    }

    override fun getEnclosingElement(): Element {
        val parent = property.parentDeclaration
        return when (parent) {
            is KSClassDeclaration ->
                KspClassTypeElement(parent, resolver, logger)
            else -> error("Unexpected parent type for property: ${parent!!::class.simpleName}")
        }
    }

    override fun getEnclosedElements(): List<Element> {
        return emptyList()
    }

    override fun getAnnotationMirrors(): List<AnnotationMirror> {
        // Include annotations from the property accessor
        return toAnnotationMirrors(accessor.annotations.toList(), resolver, logger)
    }

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? {
        if (annotationType == null) return null

        val targetName = annotationType.name
        val annotation = accessor.annotations.firstOrNull { anno ->
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

    override fun asType(): TypeMirror = returnType

    override fun getKind(): ElementKind {
        return ElementKind.METHOD
    }

    override fun getModifiers(): Set<Modifier> {
        val modifiers = mutableSetOf<Modifier>()

        // Use property visibility for the accessor
        if (property.isPublic()) modifiers.add(Modifier.PUBLIC)
        if (property.isPrivate()) modifiers.add(Modifier.PRIVATE)
        if (property.isProtected()) modifiers.add(Modifier.PROTECTED)

        // Property accessors are final unless the property is abstract/open
        val propMods = property.modifiers
        if (KspModifier.ABSTRACT in propMods) {
            modifiers.add(Modifier.ABSTRACT)
        } else if (!property.isOpen()) {
            modifiers.add(Modifier.FINAL)
        }

        return modifiers
    }

    override fun <R : Any?, P : Any?> accept(v: ElementVisitor<R?, P?>?, p: P?): R? {
        return v?.visitExecutable(this, p)
    }

    override fun toString(): String {
        return "KspPropertyAccessorExecutableElement[${simpleName}]"
    }
}

/**
 * Synthetic parameter element for setter methods
 */
private class KspSyntheticParameterElement(
    private val paramName: String,
    private val paramType: TypeMirror,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : AbstractKspAnnotatedConstruct(), VariableElement {

    override fun getConstantValue(): Any? = null

    override fun getSimpleName(): Name = StringName(paramName)

    override fun asType(): TypeMirror = paramType

    override fun getKind(): ElementKind = ElementKind.PARAMETER

    override fun getModifiers(): Set<Modifier> = setOf(Modifier.FINAL)

    override fun getEnclosingElement(): Element? = null

    override fun getEnclosedElements(): List<Element> = emptyList()

    override fun <R : Any?, P : Any?> accept(v: ElementVisitor<R?, P?>?, p: P?): R? {
        return v?.visitVariable(this, p)
    }

    override fun toString(): String = "KspSyntheticParameterElement[$paramName]"
}
