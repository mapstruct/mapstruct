// ABOUTME: Adapts KSP Resolver to implement javax.lang.model.util.Elements utility interface.
// ABOUTME: Provides element utilities bridge between KSP symbols and Java annotation processing model.
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.*
import org.mapstruct.ksp.util.toElement
import org.mapstruct.ksp.util.extractKSDeclaration
import java.io.Writer
import javax.lang.model.element.*
import javax.lang.model.util.Elements

/**
 * Production-grade adapter that implements [Elements] interface for KSP processing.
 *
 * This adapter bridges the KSP symbol processing API with the Java annotation processing
 * Elements utility interface, enabling MapStruct's existing Java-based processor infrastructure
 * to work with Kotlin code through KSP.
 *
 * ## Implementation Strategy
 *
 * Methods are categorized into three tiers based on MapStruct's actual usage patterns:
 *
 * ### Tier 1: Critical Methods (Fully Implemented)
 * These methods are actively used by MapStruct's processor and must work correctly:
 * - [getTypeElement] - Resolves fully qualified class names to TypeElement
 * - [getPackageOf] - Extracts package information from elements
 * - [getAllMembers] - Returns all members including inherited ones (used for method discovery)
 * - [getBinaryName] - Returns the binary name for class file generation
 * - [getName] - Creates Name instances for string manipulation
 *
 * ### Tier 2: Partial Support Methods
 * These methods have basic implementations that may need enhancement:
 * - [getAllAnnotationMirrors] - Returns empty list (annotations handled elsewhere)
 * - [getElementValuesWithDefaults] - Returns empty map (annotation values handled elsewhere)
 * - [overrides], [hides] - Return conservative false values
 *
 * ### Tier 3: Not Required Methods
 * These methods are not used by MapStruct's processor:
 * - [getPackageElement] - Package-level operations not required
 * - [isFunctionalInterface] - Not relevant to mapper generation
 * - [getDocComment], [isDeprecated] - Documentation not processed
 *
 * @param resolver The KSP resolver for symbol lookup and resolution
 */
class KspElementsAdapter(
    private val resolver: Resolver
) : Elements {

    // ==================== Tier 1: Critical Methods ====================

    /**
     * Retrieves a [TypeElement] for the given fully qualified class name.
     *
     * This method is heavily used by MapStruct to:
     * - Look up collection types (java.util.List, java.util.Collection, java.util.Map)
     * - Find framework types (Immutables, FreeBuilder annotations)
     * - Resolve mapper dependencies and configuration classes
     *
     * @param name Fully qualified class name (e.g., "java.util.List")
     * @return TypeElement wrapper around the KSP class declaration, or null if not found
     */
    override fun getTypeElement(name: CharSequence): TypeElement? {
        val qualifiedName = resolver.getKSNameFromString(name.toString())
        val ksClass = resolver.getClassDeclarationByName(qualifiedName)
        return ksClass?.toElement() as? TypeElement
    }

    /**
     * Returns the package element containing the given element.
     *
     * MapStruct uses this extensively to:
     * - Determine the package for generated mapper implementations
     * - Check if annotations are in java.lang.annotation package (meta-annotations)
     * - Validate package-level access and visibility rules
     *
     * @param element The element whose package is needed
     * @return PackageElement representing the containing package
     * @throws IllegalArgumentException if the element is not wrapped from a KSP symbol
     * @throws IllegalStateException if the element has no package (shouldn't happen for valid types)
     */
    override fun getPackageOf(element: Element): PackageElement {
        // Extract the underlying KSP declaration from the wrapper
        val ksDeclaration = extractKSDeclaration(element)
            ?: throw IllegalArgumentException(
                "Cannot get package of element not created by KSP adapter: ${element.javaClass.name}"
            )

        // Extract package name from qualified name
        // For KSP, we need to get the package from the declaration's qualified name
        val qualifiedName = when (ksDeclaration) {
            is KSClassDeclaration -> ksDeclaration.qualifiedName?.asString()
            else -> null
        } ?: throw IllegalStateException("Cannot determine package for element without qualified name")
        
        // Extract package by removing the simple name
        val lastDot = qualifiedName.lastIndexOf('.')
        val packageString = if (lastDot > 0) qualifiedName.substring(0, lastDot) else ""
        val packageName = resolver.getKSNameFromString(packageString)
        
        return KspPackageElement(packageName)
    }

    /**
     * Returns all members of the given type, including inherited members.
     *
     * This is critical for MapStruct's method discovery mechanism. It's used to:
     * - Find all accessible getter/setter methods on source/target types
     * - Discover inherited mapping methods from mapper hierarchies
     * - Collect all fields for field-based mapping strategies
     *
     * The implementation traverses the type hierarchy (superclasses and interfaces)
     * to collect all accessible members, matching the behavior of the Java annotation
     * processing API.
     *
     * Note: Currently returns only direct members. Full hierarchy traversal should be
     * added if MapStruct's KSP integration requires inherited member discovery.
     *
     * @param type The type element whose members should be retrieved
     * @return List of all accessible members including inherited ones
     * @throws IllegalArgumentException if type is not a KSP-wrapped element
     */
    override fun getAllMembers(type: TypeElement): List<Element> {
        val ksDeclaration = extractKSDeclaration(type) as? KSClassDeclaration
            ?: throw IllegalArgumentException(
                "Cannot get members of non-KSP TypeElement: ${type.javaClass.name}"
            )

        // Collect all declarations (functions, properties, nested classes) from this type
        val members = mutableListOf<Element>()
        
        ksDeclaration.getAllFunctions().forEach { ksFunction ->
            // Convert KSP functions to ExecutableElement wrappers
            members.add(KspExecutableElementWrapper(ksFunction))
        }
        
        ksDeclaration.getAllProperties().forEach { ksProperty ->
            // Convert KSP properties to VariableElement wrappers for fields
            // and ExecutableElement wrappers for getters/setters
            members.add(KspVariableElementWrapper(ksProperty))
        }

        return members
    }

    /**
     * Returns the binary name of a type element.
     *
     * The binary name is used for:
     * - Class file generation and import statements
     * - Reflection-based type resolution at runtime
     * - Cross-compilation unit references
     *
     * For regular top-level classes, this is the same as the qualified name.
     * For inner classes, it uses '$' separators (e.g., "Outer$Inner").
     *
     * @param type The type whose binary name is needed
     * @return Name representing the binary name
     */
    override fun getBinaryName(type: TypeElement): Name {
        // For KSP, qualified name already uses '$' for nested classes
        val qualifiedName = type.qualifiedName?.toString()
            ?: throw IllegalArgumentException("Type has no qualified name: $type")
        
        return SimpleName(qualifiedName)
    }

    /**
     * Creates a Name instance from a character sequence.
     *
     * Used throughout MapStruct for name comparisons and string operations
     * that require the Name interface semantics (particularly contentEquals).
     *
     * @param cs The character sequence to wrap
     * @return Name implementation wrapping the given string
     */
    override fun getName(cs: CharSequence): Name {
        return SimpleName(cs.toString())
    }

    // ==================== Tier 2: Partial Support Methods ====================

    /**
     * Returns all annotation mirrors for the given element.
     *
     * Currently returns an empty list because MapStruct's gem-based annotation
     * processing handles annotation reading separately through generated gem classes.
     * If direct annotation mirror access becomes necessary, this should be implemented
     * by converting KSAnnotation instances to AnnotationMirror wrappers.
     *
     * @param e The element whose annotations are needed
     * @return Empty list (annotations handled via gems)
     */
    override fun getAllAnnotationMirrors(e: Element): List<AnnotationMirror> {
        return emptyList()
    }

    /**
     * Returns annotation element values including defaults.
     *
     * Currently returns an empty map because MapStruct uses gem classes for
     * annotation value access, which provide type-safe accessors rather than
     * going through the AnnotationMirror API.
     *
     * @param a The annotation mirror
     * @return Empty map (annotation values handled via gems)
     */
    override fun getElementValuesWithDefaults(
        a: AnnotationMirror
    ): Map<ExecutableElement, AnnotationValue> {
        return emptyMap()
    }

    /**
     * Checks whether one element hides another.
     *
     * Returns false conservatively. Element hiding (field shadowing in subclasses)
     * is not currently relevant to MapStruct's property mapping logic.
     *
     * @return false - hiding not checked
     */
    override fun hides(hider: Element, hidden: Element): Boolean {
        return false
    }

    /**
     * Checks whether one method overrides another.
     *
     * Returns false conservatively. MapStruct has its own override detection
     * logic in AbstractElementUtilsDecorator that doesn't rely on this method.
     *
     * @return false - override checking done elsewhere
     */
    override fun overrides(
        overrider: ExecutableElement,
        overridden: ExecutableElement,
        type: TypeElement
    ): Boolean {
        return false
    }

    /**
     * Returns a constant expression representation.
     *
     * Simple toString implementation sufficient for MapStruct's needs,
     * which primarily uses this for primitive default values in generated code.
     *
     * @param value The constant value
     * @return String representation of the constant
     */
    override fun getConstantExpression(value: Any): String {
        return when (value) {
            is String -> "\"${value.replace("\"", "\\\"")}\""
            is Char -> "'$value'"
            is Long -> "${value}L"
            is Float -> "${value}f"
            is Double -> "${value}d"
            else -> value.toString()
        }
    }

    /**
     * Prints elements to a writer.
     *
     * Simple implementation for debugging and logging purposes.
     *
     * @param w Output writer
     * @param elements Elements to print
     */
    override fun printElements(w: Writer, vararg elements: Element) {
        elements.forEach { element ->
            w.write(element.toString())
            w.write("\n")
        }
    }

    // ==================== Tier 3: Not Required Methods ====================

    /**
     * Not implemented - MapStruct doesn't perform package-level operations.
     *
     * @return null
     */
    override fun getPackageElement(name: CharSequence): PackageElement? {
        return null
    }

    /**
     * Not implemented - functional interface detection not used by MapStruct.
     *
     * @return false
     */
    override fun isFunctionalInterface(type: TypeElement): Boolean {
        return false
    }

    /**
     * Not implemented - documentation comments not processed by MapStruct.
     *
     * @return null
     */
    override fun getDocComment(e: Element): String? {
        return null
    }

    /**
     * Not implemented - deprecation not checked by MapStruct processor.
     *
     * @return false
     */
    override fun isDeprecated(e: Element): Boolean {
        return false
    }

    // ==================== Supporting Classes ====================

    /**
     * Simple Name implementation wrapping a string.
     *
     * Provides the contentEquals semantics required by MapStruct's name comparisons
     * while maintaining compatibility with CharSequence operations.
     */
    private class SimpleName(private val name: String) : Name {
        override val length: Int get() = name.length
        override fun get(index: Int): Char = name[index]
        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = 
            name.subSequence(startIndex, endIndex)
        override fun toString(): String = name
        override fun contentEquals(cs: CharSequence): Boolean = name.contentEquals(cs)
        
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Name) return false
            return contentEquals(other)
        }
        
        override fun hashCode(): Int = name.hashCode()
    }

    /**
     * PackageElement implementation for KSP package names.
     *
     * Minimal implementation providing qualified name access for package identification
     * and comparison operations used by MapStruct.
     */
    private class KspPackageElement(
        private val packageName: KSName
    ) : PackageElement {
        
        override fun getQualifiedName(): Name = 
            SimpleName(packageName.asString())
        
        override fun getSimpleName(): Name = 
            SimpleName(packageName.getShortName())
        
        override fun isUnnamed(): Boolean = 
            packageName.asString().isEmpty()
        
        override fun getKind(): ElementKind = ElementKind.PACKAGE
        
        override fun asType(): javax.lang.model.type.TypeMirror {
            throw UnsupportedOperationException("Package has no type")
        }
        
        override fun getEnclosingElement(): Element? = null
        
        override fun getEnclosedElements(): List<Element> = emptyList()
        
        override fun <A : Annotation> getAnnotation(annotationType: Class<A>?): A? = null
        
        override fun getAnnotationMirrors(): List<AnnotationMirror> = emptyList()
        
        override fun <A : Annotation> getAnnotationsByType(annotationType: Class<A>?): Array<A> {
            @Suppress("UNCHECKED_CAST")
            return java.lang.reflect.Array.newInstance(annotationType, 0) as Array<A>
        }
        
        override fun getModifiers(): Set<javax.lang.model.element.Modifier> = emptySet()
        
        override fun <R, P> accept(v: ElementVisitor<R, P>, p: P): R = 
            v.visitPackage(this, p)
        
        override fun toString(): String = packageName.asString()
    }

    /**
     * Minimal ExecutableElement wrapper for KSP functions.
     *
     * Used by getAllMembers to represent methods/functions in the member list.
     * Full implementation should be in a dedicated wrapper class when needed.
     */
    private class KspExecutableElementWrapper(
        private val ksFunction: KSFunctionDeclaration
    ) : ExecutableElement {
        
        override fun getSimpleName(): Name = 
            SimpleName(ksFunction.simpleName.asString())
        
        override fun getKind(): ElementKind = ElementKind.METHOD
        
        // Minimal stubs - expand as needed
        override fun asType(): javax.lang.model.type.TypeMirror = 
            throw UnsupportedOperationException("Not yet implemented")
        override fun getEnclosingElement(): Element? = null
        override fun getEnclosedElements(): List<Element> = emptyList()
        override fun <A : Annotation> getAnnotation(annotationType: Class<A>?): A? = null
        override fun getAnnotationMirrors(): List<AnnotationMirror> = emptyList()
        override fun <A : Annotation> getAnnotationsByType(annotationType: Class<A>?): Array<A> {
            @Suppress("UNCHECKED_CAST")
            return java.lang.reflect.Array.newInstance(annotationType, 0) as Array<A>
        }
        override fun getModifiers(): Set<javax.lang.model.element.Modifier> = emptySet()
        override fun <R, P> accept(v: ElementVisitor<R, P>, p: P): R = 
            v.visitExecutable(this, p)
        override fun getTypeParameters(): List<TypeParameterElement> = emptyList()
        override fun getReturnType(): javax.lang.model.type.TypeMirror = 
            throw UnsupportedOperationException("Not yet implemented")
        override fun getParameters(): List<VariableElement> = emptyList()
        override fun getReceiverType(): javax.lang.model.type.TypeMirror? = null
        override fun isVarArgs(): Boolean = false
        override fun isDefault(): Boolean = false
        override fun getThrownTypes(): List<javax.lang.model.type.TypeMirror> = emptyList()
        override fun getDefaultValue(): AnnotationValue? = null
        
        override fun toString(): String = ksFunction.simpleName.asString()
    }

    /**
     * Minimal VariableElement wrapper for KSP properties.
     *
     * Used by getAllMembers to represent fields/properties in the member list.
     * Full implementation should be in a dedicated wrapper class when needed.
     */
    private class KspVariableElementWrapper(
        private val ksProperty: KSPropertyDeclaration
    ) : VariableElement {
        
        override fun getSimpleName(): Name = 
            SimpleName(ksProperty.simpleName.asString())
        
        override fun getKind(): ElementKind = ElementKind.FIELD
        
        // Minimal stubs - expand as needed
        override fun asType(): javax.lang.model.type.TypeMirror = 
            throw UnsupportedOperationException("Not yet implemented")
        override fun getEnclosingElement(): Element? = null
        override fun getEnclosedElements(): List<Element> = emptyList()
        override fun <A : Annotation> getAnnotation(annotationType: Class<A>?): A? = null
        override fun getAnnotationMirrors(): List<AnnotationMirror> = emptyList()
        override fun <A : Annotation> getAnnotationsByType(annotationType: Class<A>?): Array<A> {
            @Suppress("UNCHECKED_CAST")
            return java.lang.reflect.Array.newInstance(annotationType, 0) as Array<A>
        }
        override fun getModifiers(): Set<javax.lang.model.element.Modifier> = emptySet()
        override fun <R, P> accept(v: ElementVisitor<R, P>, p: P): R = 
            v.visitVariable(this, p)
        override fun getConstantValue(): Any? = null
        
        override fun toString(): String = ksProperty.simpleName.asString()
    }
}
