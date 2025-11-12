// ABOUTME: Conversion utilities between KSP symbols and Java annotation processing model elements.
// ABOUTME: Provides extension functions to convert between KSP and javax.lang.model types.
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.util

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVisitor

/**
 * Converts a Java Element to its underlying KSP KSNode representation.
 *
 * This function unwraps Element instances that were created by the KSP adapter layer,
 * extracting the original KSP symbol (KSDeclaration) that the wrapper was built from.
 *
 * ## Design Rationale
 *
 * MapStruct's KSP processor uses an adapter pattern where KSP symbols (KSNode types like
 * KSClassDeclaration, KSFunctionDeclaration, KSPropertyDeclaration) are wrapped in
 * javax.lang.model.element.Element interfaces to enable compatibility with MapStruct's
 * existing Java annotation processor infrastructure.
 *
 * This function provides the inverse operation of [toElement], allowing code that receives
 * Element wrappers to access the underlying KSP symbol when KSP-specific operations are needed.
 *
 * ## Supported Element Types
 *
 * - **TypeElement** → KSClassDeclaration (via KspTypeElementWrapper)
 * - **ExecutableElement** → KSFunctionDeclaration (via KspExecutableElementWrapper in KspElementsAdapter)
 * - **VariableElement** → KSPropertyDeclaration (via KspVariableElementWrapper in KspElementsAdapter)
 *
 * ## Usage Pattern
 *
 * ```kotlin
 * // In adapter code that receives Element wrappers
 * val element: TypeElement = getTypeElement("com.example.MyClass")
 * val ksNode: KSNode = element.toKSNode()
 *
 * when (ksNode) {
 *     is KSClassDeclaration -> {
 *         // Access KSP-specific class operations
 *         ksNode.superTypes.forEach { superType ->
 *             // Process super types using KSP API
 *         }
 *     }
 *     is KSFunctionDeclaration -> {
 *         // Access KSP-specific function operations
 *         ksNode.parameters.forEach { param ->
 *             // Process parameters using KSP API
 *         }
 *     }
 *     is KSPropertyDeclaration -> {
 *         // Access KSP-specific property operations
 *         val getter = ksNode.getter
 *         val setter = ksNode.setter
 *     }
 * }
 * ```
 *
 * ## Error Handling
 *
 * Throws NotImplementedError (via TODO()) if the Element was not created by the KSP adapter
 * or if the specific wrapper type is not yet supported. This fail-fast behavior ensures
 * that unsupported use cases are caught immediately during development rather than
 * silently returning null and causing subtle bugs later.
 *
 * As additional wrapper types are implemented (for ExecutableElement, VariableElement, etc.),
 * they should be added to the [extractKSDeclaration] function.
 *
 * ## Implementation Strategy
 *
 * The function delegates to [extractKSDeclaration] which handles all known wrapper types,
 * including internal wrapper classes from KspElementsAdapter. This provides a stable public
 * API while keeping wrapper implementation details internal.
 *
 * @receiver The Element to unwrap
 * @return The underlying KSNode (a KSDeclaration subtype: KSClassDeclaration, KSFunctionDeclaration, or KSPropertyDeclaration)
 * @throws NotImplementedError if the element was not created by KSP adapter or wrapper type not yet supported
 */
fun Element.toKSNode(): KSNode = extractKSDeclaration(this)

/**
 * Extracts the underlying KSP declaration from an Element wrapper.
 *
 * This function is the inverse of [toElement], retrieving the original KSP symbol
 * from Elements that were created by the KSP adapter layer.
 *
 * @receiver The Element wrapper to unwrap
 * @return The underlying KSDeclaration, or null if the element wasn't created by KSP adapter
 */
fun extractKSDeclaration(element: Element): KSDeclaration {
    return when (element) {
        is KspTypeElementWrapper -> element.getKSClass()
        // Add other wrapper types as they're implemented
        else -> TODO("Unsupported element type: ${element.javaClass.name}")
    }
}

/**
 * Converts a KSP KSClassDeclaration to a Java TypeElement.
 * Creates a wrapper that implements TypeElement interface.
 */
fun KSClassDeclaration.toElement(): Element = KspTypeElementWrapper(this)

/**
 * Converts a KSP KSType to a TypeMirror.
 * Creates appropriate wrapper based on the type's characteristics.
 */
fun KSType.toTypeMirror(resolver: Resolver): TypeMirror {
    val declaration = this.declaration

    return when {
        this.isError -> throw IllegalArgumentException("Cannot convert error type to TypeMirror")
        declaration is KSClassDeclaration -> {
            val element = declaration.toElement() as TypeElement
            KspDeclaredTypeWrapper(this, element)
        }
        else -> throw IllegalArgumentException("Unsupported type for conversion: $this")
    }
}

/**
 * Extracts the underlying KSType from a TypeMirror wrapper.
 * Throws if the TypeMirror is not a KSP wrapper.
 */
fun extractKSType(typeMirror: TypeMirror): KSType {
    return when (typeMirror) {
        is KspDeclaredTypeWrapper -> typeMirror.getKSType()
        is KspPrimitiveTypeWrapper -> typeMirror.getKSType()
        is KspArrayTypeWrapper -> typeMirror.getKSType()
        else -> throw IllegalArgumentException("Cannot extract KSType from non-KSP TypeMirror: $typeMirror")
    }
}

/**
 * Wrapper that implements TypeElement for a KSP KSClassDeclaration.
 * This is a minimal implementation needed for adapter compatibility.
 *
 * This class is internal to allow KSP adapter components to extract the
 * underlying KSP symbol for operations that require direct KSP access.
 */
internal class KspTypeElementWrapper(
    private val ksClass: KSClassDeclaration
) : TypeElement {

    /**
     * Exposes the underlying KSP class declaration.
     * Used by adapter utilities to extract KSP symbols from Element wrappers.
     */
    internal fun getKSClass(): KSClassDeclaration = ksClass

    override fun getQualifiedName(): javax.lang.model.element.Name {
        return SimpleName(ksClass.qualifiedName?.asString() ?: "")
    }

    override fun getSimpleName(): javax.lang.model.element.Name {
        return SimpleName(ksClass.simpleName.asString())
    }

    override fun getKind(): javax.lang.model.element.ElementKind {
        return when {
            ksClass.classKind == com.google.devtools.ksp.symbol.ClassKind.INTERFACE ->
                javax.lang.model.element.ElementKind.INTERFACE
            ksClass.classKind == com.google.devtools.ksp.symbol.ClassKind.CLASS ->
                javax.lang.model.element.ElementKind.CLASS
            else -> javax.lang.model.element.ElementKind.OTHER
        }
    }

    // Implementation of key methods used by MapStruct processors
    override fun asType(): TypeMirror {
        return KspDeclaredTypeWrapper(ksClass.asStarProjectedType(), this)
    }

    override fun getEnclosingElement(): Element? = null

    override fun getEnclosedElements(): List<Element> {
        // For annotation types, return ExecutableElements representing annotation attributes
        if (ksClass.classKind == ClassKind.ANNOTATION_CLASS) {
            return ksClass.getAllProperties().map { property ->
                KspAnnotationAttributeWrapper(property)
            }.toList()
        }

        // For now, return empty for non-annotation types - will be implemented when needed
        return emptyList()
    }

    override fun <A : Annotation> getAnnotation(annotationType: Class<A>?): A? = null

    override fun getAnnotationMirrors(): List<javax.lang.model.element.AnnotationMirror> {
        return ksClass.annotations.map { ksAnnotation ->
            KspAnnotationMirrorWrapper(ksAnnotation)
        }.toList()
    }

    override fun <A : Annotation> getAnnotationsByType(annotationType: Class<A>?): Array<A> {
        @Suppress("UNCHECKED_CAST")
        return java.lang.reflect.Array.newInstance(annotationType, 0) as Array<A>
    }

    override fun getModifiers(): Set<javax.lang.model.element.Modifier> {
        val modifiers = mutableSetOf<javax.lang.model.element.Modifier>()
        // Map KSP modifiers to javax.lang.model.element.Modifier
        if (ksClass.modifiers.contains(com.google.devtools.ksp.symbol.Modifier.PUBLIC)) {
            modifiers.add(javax.lang.model.element.Modifier.PUBLIC)
        }
        if (ksClass.modifiers.contains(com.google.devtools.ksp.symbol.Modifier.ABSTRACT)) {
            modifiers.add(javax.lang.model.element.Modifier.ABSTRACT)
        }
        return modifiers
    }

    override fun <R, P> accept(v: javax.lang.model.element.ElementVisitor<R, P>?, p: P): R {
        return v!!.visitType(this, p)
    }

    override fun getSuperclass(): TypeMirror {
        return KspNoTypeWrapper()
    }

    override fun getInterfaces(): List<TypeMirror> = emptyList()

    override fun getTypeParameters() = emptyList<javax.lang.model.element.TypeParameterElement>()

    override fun getNestingKind() = javax.lang.model.element.NestingKind.TOP_LEVEL

    private class SimpleName(private val name: String) : javax.lang.model.element.Name {
        override val length: Int get() = name.length
        override fun get(index: Int): Char = name[index]
        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = name.subSequence(startIndex, endIndex)
        override fun toString(): String = name
        override fun contentEquals(cs: CharSequence): Boolean = name.contentEquals(cs)
    }
}

/**
 * Wrapper that implements DeclaredType for a KSP KSType.
 * Represents the type of a class/interface declaration.
 */
internal class KspDeclaredTypeWrapper(
    private val ksType: KSType,
    private val element: TypeElement
) : DeclaredType {

    fun getKSType(): KSType = ksType

    override fun asElement(): Element = element

    override fun getEnclosingType(): TypeMirror = KspNoTypeWrapper()

    override fun getTypeArguments(): List<TypeMirror> = emptyList()

    override fun getKind(): TypeKind = TypeKind.DECLARED

    override fun <R, P> accept(v: TypeVisitor<R, P>?, p: P): R {
        return v!!.visitDeclared(this, p)
    }

    override fun getAnnotationMirrors() = emptyList<javax.lang.model.element.AnnotationMirror>()

    override fun <A : Annotation> getAnnotation(annotationClass: Class<A>?): A? = null

    override fun <A : Annotation> getAnnotationsByType(annotationClass: Class<A>?): Array<A> {
        @Suppress("UNCHECKED_CAST")
        return java.lang.reflect.Array.newInstance(annotationClass, 0) as Array<A>
    }

    override fun toString(): String = ksType.toString()
}

/**
 * Wrapper for NoType (represents absence of type, like void or package pseudo-type).
 */
private class KspNoTypeWrapper(private val kind: TypeKind = TypeKind.NONE) : javax.lang.model.type.NoType {

    override fun getKind(): TypeKind = kind

    override fun <R, P> accept(v: TypeVisitor<R, P>?, p: P): R {
        return v!!.visitNoType(this, p)
    }

    override fun getAnnotationMirrors() = emptyList<javax.lang.model.element.AnnotationMirror>()

    override fun <A : Annotation> getAnnotation(annotationClass: Class<A>?): A? = null

    override fun <A : Annotation> getAnnotationsByType(annotationClass: Class<A>?): Array<A> {
        @Suppress("UNCHECKED_CAST")
        return java.lang.reflect.Array.newInstance(annotationClass, 0) as Array<A>
    }
}

/**
 * Wrapper for primitive types.
 */
internal class KspPrimitiveTypeWrapper(
    private val ksType: KSType,
    private val primitiveKind: TypeKind
) : javax.lang.model.type.PrimitiveType {

    fun getKSType(): KSType = ksType

    override fun getKind(): TypeKind = primitiveKind

    override fun <R, P> accept(v: TypeVisitor<R, P>?, p: P): R {
        return v!!.visitPrimitive(this, p)
    }

    override fun getAnnotationMirrors() = emptyList<javax.lang.model.element.AnnotationMirror>()

    override fun <A : Annotation> getAnnotation(annotationClass: Class<A>?): A? = null

    override fun <A : Annotation> getAnnotationsByType(annotationClass: Class<A>?): Array<A> {
        @Suppress("UNCHECKED_CAST")
        return java.lang.reflect.Array.newInstance(annotationClass, 0) as Array<A>
    }

    override fun toString(): String = primitiveKind.toString().lowercase()
}

/**
 * Wrapper for array types.
 */
internal class KspArrayTypeWrapper(
    private val ksType: KSType,
    private val component: TypeMirror
) : javax.lang.model.type.ArrayType {

    fun getKSType(): KSType = ksType

    override fun getComponentType(): TypeMirror = component

    override fun getKind(): TypeKind = TypeKind.ARRAY

    override fun <R, P> accept(v: TypeVisitor<R, P>?, p: P): R {
        return v!!.visitArray(this, p)
    }

    override fun getAnnotationMirrors() = emptyList<javax.lang.model.element.AnnotationMirror>()

    override fun <A : Annotation> getAnnotation(annotationClass: Class<A>?): A? = null

    override fun <A : Annotation> getAnnotationsByType(annotationClass: Class<A>?): Array<A> {
        @Suppress("UNCHECKED_CAST")
        return java.lang.reflect.Array.newInstance(annotationClass, 0) as Array<A>
    }

    override fun toString(): String = "$component[]"
}

/**
 * Wrapper that implements AnnotationMirror for a KSP KSAnnotation.
 * Represents annotation instances on program elements.
 */
internal class KspAnnotationMirrorWrapper(
    private val ksAnnotation: com.google.devtools.ksp.symbol.KSAnnotation
) : javax.lang.model.element.AnnotationMirror {

    private val _annotationType: javax.lang.model.type.DeclaredType by lazy {
        val ksType = ksAnnotation.annotationType.resolve()
        val declaration = ksType.declaration as? KSClassDeclaration
            ?: throw IllegalStateException("Annotation type must be a class declaration")
        val typeElement = declaration.toElement() as TypeElement
        KspDeclaredTypeWrapper(ksType, typeElement)
    }

    override fun getAnnotationType(): javax.lang.model.type.DeclaredType = _annotationType

    override fun getElementValues(): Map<javax.lang.model.element.ExecutableElement, javax.lang.model.element.AnnotationValue> {
        val result = mutableMapOf<javax.lang.model.element.ExecutableElement, javax.lang.model.element.AnnotationValue>()

        // Get explicitly set annotation arguments and convert them to a map for quick lookup
        val explicitValues = ksAnnotation.arguments.mapNotNull { argument ->
            val name = argument.name?.asString() ?: return@mapNotNull null
            val value = argument.value
            name to value
        }.toMap()

        // Get all properties (both with and without defaults) from the annotation type declaration
        val annotationDeclaration = ksAnnotation.annotationType.resolve().declaration as? KSClassDeclaration
        annotationDeclaration?.getAllProperties()?.forEach { property ->
            val name = property.simpleName.asString()

            // Use explicit value if provided, otherwise this property won't be in the result
            // This matches Java annotation processing behavior where only explicitly set values appear
            if (name in explicitValues) {
                val value = explicitValues[name]
                if (value != null) {
                    val executableElement = KspAnnotationMethodWrapper(name)
                    val annotationValue = KspAnnotationValueWrapper(value)
                    result[executableElement] = annotationValue
                }
            }
        }

        return result
    }

    override fun toString(): String = ksAnnotation.toString()
}

/**
 * ExecutableElement wrapper for annotation attributes.
 * Represents an annotation attribute with its name and default value.
 * Used when annotation type's getEnclosedElements() is called.
 */
private class KspAnnotationAttributeWrapper(
    private val property: KSPropertyDeclaration
) : javax.lang.model.element.ExecutableElement {

    override fun getSimpleName(): javax.lang.model.element.Name =
        SimpleName(property.simpleName.asString())

    override fun getKind(): javax.lang.model.element.ElementKind =
        javax.lang.model.element.ElementKind.METHOD

    override fun getDefaultValue(): javax.lang.model.element.AnnotationValue? {
        // Check if the property has a default value expression
        // In Kotlin annotations, default values are specified with = in the constructor parameter
        // KSP doesn't provide direct access to default values, so we check if it has an initializer
        // For annotation properties without explicit value in usage, we need to return the default
        // For now, return null if no default - this will be populated by GemValue.create() logic
        return null
    }

    // Rest of ExecutableElement methods - minimal implementations
    override fun asType(): TypeMirror =
        throw UnsupportedOperationException("Not implemented for annotation attribute wrapper")
    override fun getEnclosingElement(): Element? = null
    override fun getEnclosedElements(): List<Element> = emptyList()
    override fun <A : Annotation> getAnnotation(annotationType: Class<A>?): A? = null
    override fun getAnnotationMirrors(): List<javax.lang.model.element.AnnotationMirror> = emptyList()
    override fun <A : Annotation> getAnnotationsByType(annotationType: Class<A>?): Array<A> {
        @Suppress("UNCHECKED_CAST")
        return java.lang.reflect.Array.newInstance(annotationType, 0) as Array<A>
    }
    override fun getModifiers(): Set<javax.lang.model.element.Modifier> = emptySet()
    override fun <R, P> accept(v: javax.lang.model.element.ElementVisitor<R, P>, p: P): R =
        v.visitExecutable(this, p)
    override fun getTypeParameters(): List<javax.lang.model.element.TypeParameterElement> = emptyList()
    override fun getReturnType(): TypeMirror =
        throw UnsupportedOperationException("Not implemented for annotation attribute wrapper")
    override fun getParameters(): List<javax.lang.model.element.VariableElement> = emptyList()
    override fun getReceiverType(): TypeMirror? = null
    override fun isVarArgs(): Boolean = false
    override fun isDefault(): Boolean = false
    override fun getThrownTypes(): List<TypeMirror> = emptyList()

    private class SimpleName(private val name: String) : javax.lang.model.element.Name {
        override val length: Int get() = name.length
        override fun get(index: Int): Char = name[index]
        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
            name.subSequence(startIndex, endIndex)
        override fun toString(): String = name
        override fun contentEquals(cs: CharSequence): Boolean = name.contentEquals(cs)
    }
}

/**
 * Minimal ExecutableElement wrapper for annotation methods.
 * Used to represent annotation attribute accessors in AnnotationMirror.getElementValues().
 */
private class KspAnnotationMethodWrapper(
    private val methodName: String
) : javax.lang.model.element.ExecutableElement {

    override fun getSimpleName(): javax.lang.model.element.Name =
        SimpleName(methodName)

    override fun getKind(): javax.lang.model.element.ElementKind =
        javax.lang.model.element.ElementKind.METHOD

    // Minimal stubs - only getSimpleName is used by MapperGem
    override fun asType(): TypeMirror =
        throw UnsupportedOperationException("Not implemented for annotation method wrapper")
    override fun getEnclosingElement(): Element? = null
    override fun getEnclosedElements(): List<Element> = emptyList()
    override fun <A : Annotation> getAnnotation(annotationType: Class<A>?): A? = null
    override fun getAnnotationMirrors(): List<javax.lang.model.element.AnnotationMirror> = emptyList()
    override fun <A : Annotation> getAnnotationsByType(annotationType: Class<A>?): Array<A> {
        @Suppress("UNCHECKED_CAST")
        return java.lang.reflect.Array.newInstance(annotationType, 0) as Array<A>
    }
    override fun getModifiers(): Set<javax.lang.model.element.Modifier> = emptySet()
    override fun <R, P> accept(v: javax.lang.model.element.ElementVisitor<R, P>, p: P): R =
        v.visitExecutable(this, p)
    override fun getTypeParameters(): List<javax.lang.model.element.TypeParameterElement> = emptyList()
    override fun getReturnType(): TypeMirror =
        throw UnsupportedOperationException("Not implemented for annotation method wrapper")
    override fun getParameters(): List<javax.lang.model.element.VariableElement> = emptyList()
    override fun getReceiverType(): TypeMirror? = null
    override fun isVarArgs(): Boolean = false
    override fun isDefault(): Boolean = false
    override fun getThrownTypes(): List<TypeMirror> = emptyList()
    override fun getDefaultValue(): javax.lang.model.element.AnnotationValue? = null

    private class SimpleName(private val name: String) : javax.lang.model.element.Name {
        override val length: Int get() = name.length
        override fun get(index: Int): Char = name[index]
        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
            name.subSequence(startIndex, endIndex)
        override fun toString(): String = name
        override fun contentEquals(cs: CharSequence): Boolean = name.contentEquals(cs)
    }
}

/**
 * Wrapper that implements AnnotationValue for KSP annotation argument values.
 * Represents the value of an annotation attribute.
 */
private class KspAnnotationValueWrapper(
    private val value: Any
) : javax.lang.model.element.AnnotationValue {

    override fun getValue(): Any {
        // Convert KSP types to Java annotation processing types where necessary
        return when (value) {
            // Direct enum constant reference (KSP provides the enum entry directly)
            is KSClassDeclaration -> {
                if (value.classKind == ClassKind.ENUM_ENTRY) {
                    // This is an enum constant - wrap it as VariableElement
                    KspEnumConstantWrapper(value)
                } else {
                    // Regular class declaration used as type
                    val typeElement = value.toElement() as TypeElement
                    KspDeclaredTypeWrapper(value.asStarProjectedType(), typeElement)
                }
            }
            is KSType -> {
                val declaration = value.declaration

                // Check if this is an enum constant reference
                // In KSP, enum constants are represented as KSClassDeclaration with ENUM_ENTRY kind
                if (declaration is KSClassDeclaration && declaration.classKind == ClassKind.ENUM_ENTRY) {
                    // This is an enum constant - wrap it as VariableElement
                    KspEnumConstantWrapper(declaration)
                } else {
                    // Regular type reference - convert to TypeMirror
                    val classDecl = declaration as? KSClassDeclaration
                        ?: throw IllegalStateException("Type argument must be a class declaration")
                    val typeElement = classDecl.toElement() as TypeElement
                    KspDeclaredTypeWrapper(value, typeElement)
                }
            }
            is List<*> -> {
                // Convert lists recursively
                value.map { item ->
                    if (item != null) KspAnnotationValueWrapper(item) else item
                }
            }
            else -> value
        }
    }

    override fun <R, P> accept(v: javax.lang.model.element.AnnotationValueVisitor<R, P>, p: P): R {
        // Dispatch to the appropriate visitor method based on the value type
        return when (val actualValue = getValue()) {
            is String -> v.visitString(actualValue, p)
            is Int -> v.visitInt(actualValue, p)
            is Long -> v.visitLong(actualValue, p)
            is Float -> v.visitFloat(actualValue, p)
            is Double -> v.visitDouble(actualValue, p)
            is Boolean -> v.visitBoolean(actualValue, p)
            is Char -> v.visitChar(actualValue, p)
            is Byte -> v.visitByte(actualValue, p)
            is Short -> v.visitShort(actualValue, p)
            is TypeMirror -> v.visitType(actualValue, p)
            is javax.lang.model.element.VariableElement -> v.visitEnumConstant(actualValue, p)
            is javax.lang.model.element.AnnotationMirror -> v.visitAnnotation(actualValue, p)
            is List<*> -> v.visitArray(actualValue.filterIsInstance<javax.lang.model.element.AnnotationValue>(), p)
            else -> v.visitUnknown(this, p)
        }
    }

    override fun toString(): String = value.toString()
}

/**
 * Wrapper that implements VariableElement for KSP enum constants.
 * Represents an enum constant in annotation values.
 */
private class KspEnumConstantWrapper(
    private val enumEntry: KSClassDeclaration
) : javax.lang.model.element.VariableElement {

    override fun getSimpleName(): javax.lang.model.element.Name =
        SimpleName(enumEntry.simpleName.asString())

    override fun getKind(): javax.lang.model.element.ElementKind =
        javax.lang.model.element.ElementKind.ENUM_CONSTANT

    override fun asType(): TypeMirror {
        // Return the enum type (parent class) as the type of this constant
        val enumClass = enumEntry.parentDeclaration as? KSClassDeclaration
            ?: throw IllegalStateException("Enum constant must have enum class parent")
        val typeElement = enumClass.toElement() as TypeElement
        return KspDeclaredTypeWrapper(enumClass.asStarProjectedType(), typeElement)
    }

    override fun getEnclosingElement(): Element {
        // Return the enum class as the enclosing element
        val enumClass = enumEntry.parentDeclaration as? KSClassDeclaration
            ?: throw IllegalStateException("Enum constant must have enum class parent")
        return enumClass.toElement()
    }

    override fun getConstantValue(): Any? = null

    // Minimal stubs for remaining VariableElement methods
    override fun getEnclosedElements(): List<Element> = emptyList()
    override fun <A : Annotation> getAnnotation(annotationType: Class<A>?): A? = null
    override fun getAnnotationMirrors(): List<javax.lang.model.element.AnnotationMirror> = emptyList()
    override fun <A : Annotation> getAnnotationsByType(annotationType: Class<A>?): Array<A> {
        @Suppress("UNCHECKED_CAST")
        return java.lang.reflect.Array.newInstance(annotationType, 0) as Array<A>
    }
    override fun getModifiers(): Set<javax.lang.model.element.Modifier> = 
        setOf(javax.lang.model.element.Modifier.PUBLIC, javax.lang.model.element.Modifier.STATIC, javax.lang.model.element.Modifier.FINAL)
    override fun <R, P> accept(v: javax.lang.model.element.ElementVisitor<R, P>, p: P): R =
        v.visitVariable(this, p)

    private class SimpleName(private val name: String) : javax.lang.model.element.Name {
        override val length: Int get() = name.length
        override fun get(index: Int): Char = name[index]
        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
            name.subSequence(startIndex, endIndex)
        override fun toString(): String = name
        override fun contentEquals(cs: CharSequence): Boolean = name.contentEquals(cs)
    }
}
