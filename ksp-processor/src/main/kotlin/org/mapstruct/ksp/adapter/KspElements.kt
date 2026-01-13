package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import java.io.Writer
import javax.lang.model.element.*
import javax.lang.model.util.Elements

class KspElements(
    private val environment: SymbolProcessorEnvironment,
    private val resolver: Resolver,
) : Elements {
    private val logger = environment.logger

    override fun getPackageElement(name: CharSequence): PackageElement {
        TODO("Not yet implemented")
    }

    private val typeCache = mutableMapOf<String, TypeElement>()

    override fun getTypeElement(name: CharSequence): TypeElement? {
        val nameString = name.toString()
        if (nameString in typeCache) {
            return typeCache[nameString]
        }

        logger.info("resolve class declaration by name: $name")
        val declaration: KSClassDeclaration = resolver.getClassDeclarationByName(nameString) ?: return null
        val result = KspClassTypeElement(declaration, resolver, logger)
        typeCache[nameString] = result
        return result
    }

    override fun getElementValuesWithDefaults(a: AnnotationMirror): Map<out ExecutableElement, AnnotationValue> {
        TODO("Not yet implemented")
    }

    override fun getDocComment(e: Element): String {
        TODO("Not yet implemented")
    }

    override fun isDeprecated(e: Element): Boolean {
        TODO("Not yet implemented")
    }

    override fun getBinaryName(type: TypeElement): Name {
        TODO("Not yet implemented")
    }

    private val packageCache = mutableMapOf<String, PackageElement>()
    override fun getPackageOf(e: Element): PackageElement {
        check(e is KspClassTypeElement) { "Element is not a KspClassTypeElement: $e" }

        // Get the mapped qualified name (Kotlin types are mapped to Java equivalents)
        val qualifiedName = e.qualifiedName?.toString() ?: e.declaration.packageName.asString()

        // Extract package from qualified name
        val packageName = if (qualifiedName.contains('.')) {
            qualifiedName.substringBeforeLast('.')
        } else {
            ""
        }

        if (packageName in packageCache) {
            return packageCache[packageName]!!
        }
        val result = KspPackageElement(packageName, resolver, logger)
        packageCache[packageName] = result
        return result
    }

    override fun getAllMembers(type: TypeElement): List<Element> {
        TODO("Not yet implemented")
    }

    override fun getAllAnnotationMirrors(e: Element): List<AnnotationMirror> {
        TODO("Not yet implemented")
    }

    override fun hides(
        hider: Element,
        hidden: Element
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun overrides(
        overrider: ExecutableElement,
        overridden: ExecutableElement,
        type: TypeElement
    ): Boolean {
        // Extract KSP function declarations from the executable elements
        val overriderFunc = (overrider as? KspExecutableElement)?.declaration
        check(overriderFunc is KSFunctionDeclaration?)

        val overriddenFunc = (overridden as? KspExecutableElement)?.declaration
        check(overriddenFunc is KSFunctionDeclaration?)

        // If either is not a KspExecutableElement, we can't determine override relationship
        if (overriderFunc == null || overriddenFunc == null) {
            return false
        }

        // Methods must have the same name
        if (overriderFunc.simpleName.asString() != overriddenFunc.simpleName.asString()) {
            return false
        }

        // Methods must have the same number of parameters
        if (overriderFunc.parameters.size != overriddenFunc.parameters.size) {
            return false
        }

        // Check that parameter types match
        for (i in overriderFunc.parameters.indices) {
            val overriderParam = overriderFunc.parameters[i]
            val overriddenParam = overriddenFunc.parameters[i]

            val overriderType = overriderParam.type.resolve()
            val overriddenType = overriddenParam.type.resolve()

            // Compare qualified names of the types
            val overriderTypeName = overriderType.declaration.qualifiedName?.asString()
            val overriddenTypeName = overriddenType.declaration.qualifiedName?.asString()

            if (overriderTypeName != overriddenTypeName) {
                return false
            }

            // Also check type arguments if present
            if (overriderType.arguments.size != overriddenType.arguments.size) {
                return false
            }
        }

        // Check that the overrider's declaring class is a subclass of or is the same as
        // the overridden's declaring class
        val overriderDeclaringClass = overriderFunc.parentDeclaration as? KSClassDeclaration
        val overriddenDeclaringClass = overriddenFunc.parentDeclaration as? KSClassDeclaration

        if (overriderDeclaringClass == null || overriddenDeclaringClass == null) {
            return false
        }

        // If they're in the same class, check if it's the same method (not an override)
        if (overriderDeclaringClass.qualifiedName?.asString() ==
            overriddenDeclaringClass.qualifiedName?.asString()) {
            // Same class - only counts as override if it's literally the same method
            return overriderFunc == overriddenFunc
        }

        // Check if overriderDeclaringClass is a subclass of overriddenDeclaringClass
        return isSubclass(overriderDeclaringClass, overriddenDeclaringClass)
    }

    private fun isSubclass(
        potentialSubclass: KSClassDeclaration,
        potentialSuperclass: KSClassDeclaration
    ): Boolean {
        val superclassQName = potentialSuperclass.qualifiedName?.asString() ?: return false

        // Check all supertypes recursively
        fun checkSuperTypes(classDecl: KSClassDeclaration): Boolean {
            for (superTypeRef in classDecl.superTypes) {
                val superType = superTypeRef.resolve()
                val superDecl = superType.declaration as? KSClassDeclaration ?: continue

                if (superDecl.qualifiedName?.asString() == superclassQName) {
                    return true
                }

                // Recursively check the supertypes
                if (checkSuperTypes(superDecl)) {
                    return true
                }
            }
            return false
        }

        return checkSuperTypes(potentialSubclass)
    }

    override fun getConstantExpression(value: Any): String {
        TODO("Not yet implemented")
    }

    override fun printElements(w: Writer, vararg elements: Element) {
        TODO("Not yet implemented")
    }

    override fun getName(cs: CharSequence): Name {
        TODO("Not yet implemented")
    }

    override fun isFunctionalInterface(type: TypeElement): Boolean {
        TODO("Not yet implemented")
    }
}
