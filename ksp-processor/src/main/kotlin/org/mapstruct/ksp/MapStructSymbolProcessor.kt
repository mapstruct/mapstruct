// ABOUTME: Main KSP processor that discovers @Mapper annotated interfaces and generates implementations.
// ABOUTME: Coordinates the multi-phase processing pipeline adapted from the Java annotation processor.
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import org.mapstruct.ksp.adapter.KspProcessingEnvironmentAdapter
import org.mapstruct.ksp.adapter.KspClassTypeElement
import org.mapstruct.ksp.internal.processor.KspProcessorContext

/**
 * KSP symbol processor for MapStruct that generates mapper implementations.
 *
 * This processor mirrors the architecture of the Java annotation processor [MappingProcessor]
 * but adapted for KSP's symbol processing model. It follows the same multi-phase approach:
 *
 * 1. Element Discovery - Find all @Mapper annotated interfaces/abstract classes
 * 2. Model Building - Create internal Mapper model objects
 * 3. Model Processing - Apply sequence of processors (via adapted pipeline)
 * 4. Code Generation - Write generated implementations using KSP's CodeGenerator
 *
 * @param environment KSP processing environment providing logger, code generator, and options
 */
class MapStructSymbolProcessor(
    private val environment: SymbolProcessorEnvironment
) : SymbolProcessor {

    private val logger: KSPLogger = environment.logger
    private val codeGenerator: CodeGenerator = environment.codeGenerator
    private val options: Map<String, String> = environment.options

    // Deferred mappers from previous rounds (due to incomplete type hierarchies)
    private val deferredMappers: MutableSet<KSClassDeclaration> = mutableSetOf()

    // Adapter to make KSP environment compatible with existing MapStruct infrastructure
    private lateinit var processingEnvironmentAdapter: KspProcessingEnvironmentAdapter

    override fun process(resolver: Resolver): List<KSAnnotated> {
        // Initialize adapter on first round
        if (!::processingEnvironmentAdapter.isInitialized) {
            processingEnvironmentAdapter = KspProcessingEnvironmentAdapter(
                environment = environment,
                resolver = resolver,
                logger = logger,
            )
        }

        // Process deferred mappers from previous rounds
        val stillDeferred = processDeferredMappers(resolver)

        // Find new mappers annotated with @Mapper
        logger.info("Starting mapper processing round...")
        val newMappers = findMappers(resolver)
        logger.info("Finished mapper processing round: ${newMappers.size} mappers found")

        // Process new mappers
        val newlyDeferred = processMappers(newMappers, resolver)

        // Update deferred set
        deferredMappers.clear()
        deferredMappers.addAll(stillDeferred)
        deferredMappers.addAll(newlyDeferred)

        // Return symbols that couldn't be processed (for KSP to defer)
        return (stillDeferred + newlyDeferred).toList()
    }

    override fun finish() {
        // Report errors for any mappers that couldn't be processed
        if (deferredMappers.isNotEmpty()) {
            for (mapper in deferredMappers) {
                logger.error(
                    "No implementation was created for ${mapper.simpleName.asString()} " +
                            "due to unresolved dependencies. " +
                            "Hint: this often means that some other processor was supposed to " +
                            "process the erroneous element.",
                    mapper
                )
            }
        }
    }

    /**
     * Finds all symbols annotated with @Mapper.
     */
    private fun findMappers(resolver: Resolver): Set<KSClassDeclaration> {
        val mapperAnnotation = "org.mapstruct.Mapper"

        return resolver
            .getSymbolsWithAnnotation(mapperAnnotation)
            .filterIsInstance<KSClassDeclaration>()
            .toSet()
    }

    /**
     * Processes a set of mapper declarations, returning those that must be deferred.
     */
    private fun processMappers(
        mappers: Set<KSClassDeclaration>,
        resolver: Resolver
    ): Set<KSClassDeclaration> {
        val deferred = mutableSetOf<KSClassDeclaration>()

        for (mapper in mappers) {
            try {
                processMapper(mapper, resolver)
            } catch (e: Exception) {
                // If processing fails due to incomplete types, defer for next round
                if (isTypeHierarchyError(e)) {
                    deferred.add(mapper)
                    logger.warn("Deferring mapper ${mapper.simpleName.asString()}: ${e.message}")
                } else {
                    logger.error("Error processing mapper ${mapper.simpleName.asString()}: ${e.message}", mapper)
                }
            }
        }

        return deferred
    }

    /**
     * Processes deferred mappers, returning those still unresolved.
     */
    private fun processDeferredMappers(resolver: Resolver): Set<KSClassDeclaration> {
        val stillDeferred = mutableSetOf<KSClassDeclaration>()

        for (mapper in deferredMappers) {
            try {
                processMapper(mapper, resolver)
            } catch (e: Exception) {
                if (isTypeHierarchyError(e)) {
                    stillDeferred.add(mapper)
                } else {
                    logger.error(
                        "Error processing deferred mapper ${mapper.simpleName.asString()}: ${e.message}",
                        mapper
                    )
                }
            }
        }

        return stillDeferred
    }

    /**
     * Processes a single mapper declaration through the processing pipeline.
     *
     * This method implements the core processing pipeline that:
     * 1. Converts KSP symbols to javax.lang.model types via adapter layer
     * 2. Loads and executes the chain of ModelElementProcessors in priority order:
     *    - Priority 1: MethodRetrievalProcessor - extracts mapping methods
     *    - Priority 1000: MapperCreationProcessor - builds Mapper model
     *    - Priority 1100: Component processors - add DI annotations (Spring, CDI, etc.)
     *    - Priority 9999: MapperRenderingProcessor - writes generated code
     *    - Priority 10000: MapperServiceProcessor - generates service files
     * 3. Each processor transforms the model, passing output to the next processor
     * 4. If errors occur or model becomes null (after rendering), processing stops
     */
    private fun processMapper(mapper: KSClassDeclaration, resolver: Resolver) {
        logger.info("Processing mapper: ${mapper.qualifiedName?.asString()}")

        // Create processor context for this mapper
        val context = KspProcessorContext(
            environment = environment,
            resolver = resolver,
            mapper = mapper,
            options = options,
        )

        // Convert KSP symbol to TypeElement adapter
        val mapperTypeElement = KspClassTypeElement(mapper, resolver, logger)

        // Create ProcessorContext compatible with existing processors
        val processorContext = context.createProcessorContext(mapperTypeElement)

        // Load and execute ModelElementProcessor chain
        var model: Any? = null
        for (processor in loadProcessors()) {
            try {
                logger.info("Executing processor: ${processor.javaClass.simpleName}")
                @Suppress("UNCHECKED_CAST")
                model = (processor as org.mapstruct.ap.internal.processor.ModelElementProcessor<Any?, Any?>)
                    .process(processorContext, mapperTypeElement, model)

                // If processing encountered errors, stop
                if (processorContext.isErroneous) {
                    logger.warn("Processing stopped due to errors for mapper: ${mapper.simpleName.asString()}")
                    break
                }

                // MapperRenderingProcessor returns null after writing the file
                if (model == null) {
                    logger.info("Mapper implementation written for: ${mapper.simpleName.asString()}")
                    break
                }

                logger.info("Processor ${processor.javaClass.simpleName} completed, model type: ${model?.javaClass?.simpleName}")
            } catch (e: Exception) {
                logger.error("Error in processor ${processor.javaClass.simpleName}: ${e.message}", mapper)
                logger.exception(e)
                logger.error(e.stackTraceToString())
                throw e
            }
        }
    }

    /**
     * Loads and sorts ModelElementProcessors via ServiceLoader.
     * Processors are sorted by priority (ascending: 1 -> 10000).
     */
    private fun loadProcessors(): List<org.mapstruct.ap.internal.processor.ModelElementProcessor<*, *>> {
        val serviceLoader = java.util.ServiceLoader.load(
            org.mapstruct.ap.internal.processor.ModelElementProcessor::class.java,
            org.mapstruct.ap.internal.processor.ModelElementProcessor::class.java.classLoader
        )

        return serviceLoader.toList()
            .sortedBy { it.priority }
            .also { processors ->
                logger.info("Loaded ${processors.size} processors:")
                processors.forEach { processor ->
                    logger.info("  - ${processor.javaClass.simpleName} (priority: ${processor.priority})")
                }
            }
    }

    /**
     * Checks if an exception indicates incomplete type hierarchy.
     */
    private fun isTypeHierarchyError(e: Exception): Boolean {
        return e.message?.contains("type hierarchy", ignoreCase = true) == true ||
                e.message?.contains("unresolved", ignoreCase = true) == true
    }
}
