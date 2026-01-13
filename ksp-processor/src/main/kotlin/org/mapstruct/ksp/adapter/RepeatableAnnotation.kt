package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.DeclaredType

internal class RepeatableAnnotation(
    val annotation: KSType,
    val args: List<KSAnnotation>,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : AnnotationMirror {
    override fun toString(): String = annotation.toString()

    override fun getAnnotationType(): DeclaredType {
        val decl = annotation.declaration
        if (decl is KSClassDeclaration) {
            val typeElement = KspClassTypeElement(decl, resolver, logger, null)
            return KspTypeMirror(typeElement, resolver, logger)
        } else {
            error("Annotation type is not a class declaration: ${decl::class.simpleName}")
        }
    }

    override fun getElementValues(): Map<out ExecutableElement, AnnotationValue> {
        // For repeatable annotations, we need to return a map containing a single entry:
        // the "value" method of the container annotation, mapped to an array of annotations
        val decl = annotation.declaration
        if (decl is KSClassDeclaration) {
            // Find the "value" method in the container annotation
            val valueMethod = decl.declarations.firstOrNull { it.simpleName.asString() == "value" }
            check(valueMethod != null) {
                "Could not find 'value' method in container annotation ${decl.qualifiedName?.asString()}"
            }

            val executableElement = KspExecutableElement(valueMethod, resolver, logger)

            // Convert each KSAnnotation to a KspAnnotationMirror and wrap in KspAnnotationValue
            val annotationMirrors = args.map { KspAnnotationMirror(it, resolver, logger) }
            val value = KspAnnotationValue(annotationMirrors, resolver, logger)

            return mapOf(executableElement to value)
        } else {
            error("Annotation type is not a class declaration: ${decl::class.simpleName}")
        }
    }
}