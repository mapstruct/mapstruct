// ABOUTME: Utility functions for converting KSP annotations to Java annotation mirrors
// ABOUTME: Provides centralized annotation handling to ensure consistency across adapter classes
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import javax.lang.model.element.AnnotationMirror

private const val REPEATABLE = "Repeatable"

/**
 * Converts a sequence of KSP annotations to a list of AnnotationMirror instances.
 * This is the standard conversion used by getAnnotationMirrors() implementations.
 *
 * Note: This does NOT handle Java repeatable annotations specially. In Java annotation processing,
 * repeatable annotations are wrapped in a container annotation at the source level, and that's
 * what getAnnotationMirrors() returns. The unwrapping of repeatable annotations is handled by
 * getAnnotationsByType(), not by getAnnotationMirrors().
 *
 * @param annotations the KSP annotation sequence to convert
 * @param resolver the KSP resolver for type resolution
 * @param logger the KSP logger for diagnostics
 * @return an immutable list of AnnotationMirror instances
 */
fun toAnnotationMirrors(
    annotations: List<KSAnnotation>, resolver: Resolver, logger: KSPLogger
): List<AnnotationMirror> {
    val repeatables: List<KSAnnotation> = annotations.filter { it.shortName.asString() == REPEATABLE }
    var processed: List<KSAnnotation> = annotations.filter { it.shortName.asString() != REPEATABLE }

    val repeatableMirror: MutableList<AnnotationMirror> = mutableListOf()

    for (repeatable in repeatables) {
        val result = processed.extractRepeatable(repeatable, resolver, logger)
        processed = result.first
        if (result.second != null) {
            repeatableMirror.add(result.second!!)
        }
    }

    return processed.map { annotation ->
        KspAnnotationMirror(annotation, resolver, logger)
    }.toList() + repeatableMirror
}

private fun List<KSAnnotation>.extractRepeatable(
    repeatable: KSAnnotation,
    resolver: Resolver,
    logger: KSPLogger
): Pair<List<KSAnnotation>, AnnotationMirror?> {
    val repeatableType = repeatable.arguments.firstOrNull()?.value as? KSType
        ?: error("Could not resolve repeatable type: $repeatable")
    val repeatableArgument = repeatable.extractRepeatableArgumentType()

    val arguments = filter { it.annotationType.resolve() == repeatableArgument }
    val notArguments = filter { it.annotationType.resolve() != repeatableArgument }

    // Don't collapse if there's only one annotation of the repeatable type
    if (arguments.size < 2) return this to null

    val collapsed = RepeatableAnnotation(repeatableType, arguments, resolver, logger)
    return notArguments to collapsed
}

private fun KSAnnotation.extractRepeatableArgumentType(): KSType {
    return (((arguments[0].value as KSType).declaration as KSClassDeclaration).declarations.toList()
        .firstOrNull { it.simpleName.asString() == "value" } as? KSPropertyDeclaration)
        ?.getter?.returnType?.resolve()?.arguments?.firstOrNull()?.type?.resolve()
        ?: error("Could not resolve repeatable type: $this")
}
