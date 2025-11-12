// ABOUTME: Context holder for KSP processing that aggregates environment, resolver, and configuration.
// ABOUTME: Provides centralized access to KSP processing resources for mapper generation.
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.internal.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import org.mapstruct.ksp.adapter.KspProcessingEnvironmentAdapter
import javax.lang.model.element.TypeElement

/**
 * Context for KSP mapper processing.
 * Aggregates all resources needed to process a single mapper declaration.
 *
 * @param environment The KSP processing environment
 * @param resolver The KSP resolver for symbol resolution
 * @param mapper The mapper class declaration being processed
 * @param options Processor options from build configuration
 */
data class KspProcessorContext(
    val environment: SymbolProcessorEnvironment,
    val resolver: Resolver,
    val mapper: KSClassDeclaration,
    val options: Map<String, String>
) {
    val logger: KSPLogger = environment.logger
    val codeGenerator: CodeGenerator = environment.codeGenerator

    /**
     * Gets a processor option value.
     */
    fun getOption(key: String): String? = options[key]

    /**
     * Checks if an option is enabled (value is "true").
     */
    fun isOptionEnabled(key: String): Boolean = options[key]?.toBoolean() ?: false

    /**
     * Gets an option value with a default.
     */
    fun getOptionOrDefault(key: String, default: String): String = options[key] ?: default

    /**
     * Creates a ProcessorContext compatible with existing MapStruct processors.
     * This bridges KSP types to the javax.annotation.processing types that existing processors expect.
     */
    fun createProcessorContext(mapperTypeElement: TypeElement): org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext {
        // Create adapter that bridges KSP environment to ProcessingEnvironment
        val processingEnvironment = KspProcessingEnvironmentAdapter(
            environment = environment,
            resolver = resolver
        )

        // Create options instance
        val mapstructOptions = org.mapstruct.ap.internal.option.Options(options)

        // Create annotation processor context
        val annotationProcessorContext = org.mapstruct.ap.internal.util.AnnotationProcessorContext(
            processingEnvironment.elementUtils,
            processingEnvironment.typeUtils,
            processingEnvironment.messager,
            mapstructOptions.isDisableBuilders,
            mapstructOptions.isVerbose,
            emptyMap() // additional options from SPI providers
        )

        // Create round context
        val roundContext = org.mapstruct.ap.internal.util.RoundContext(annotationProcessorContext)

        // Get types that should not be imported (inner classes of the mapper)
        val declaredTypesNotToBeImported = mutableMapOf<String, String>()
        // For now, leave empty - will be populated if needed

        // Create and return DefaultModelElementProcessorContext
        return org.mapstruct.ap.internal.processor.DefaultModelElementProcessorContext(
            processingEnvironment,
            mapstructOptions,
            roundContext,
            declaredTypesNotToBeImported,
            mapperTypeElement
        )
    }
}
