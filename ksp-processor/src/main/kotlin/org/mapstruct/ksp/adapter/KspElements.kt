package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import java.io.Writer
import javax.lang.model.element.*
import javax.lang.model.util.Elements

class KspElements(
    private val environment: SymbolProcessorEnvironment,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : Elements {
    override fun getPackageElement(name: CharSequence): PackageElement {
        TODO("Not yet implemented")
    }

    override fun getTypeElement(name: CharSequence): TypeElement? {
        logger.info("resolve class declaration by name: $name")
        val declaration: KSClassDeclaration = resolver.getClassDeclarationByName(name.toString()) ?: return null
        return KspClassTypeElement(declaration, resolver, logger)
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

    override fun getPackageOf(e: Element): PackageElement {
        check(e is KspClassTypeElement) { "Element is not a KspClassTypeElement: $e "}
        return KspPackageElement(e.declaration.packageName.asString(), resolver, logger)
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
        TODO("Not yet implemented")
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
