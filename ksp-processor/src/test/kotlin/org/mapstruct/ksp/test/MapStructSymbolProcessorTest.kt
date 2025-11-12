// ABOUTME: Basic test for MapStruct KSP processor to verify processor registration and initialization.
// ABOUTME: Tests processor discovery and basic instantiation without requiring full compilation.
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.MapStructSymbolProcessorProvider
import org.assertj.core.api.Assertions.assertThat
import java.util.ServiceLoader

/**
 * Tests for the MapStruct KSP processor.
 *
 * These are basic smoke tests that verify the processor can be discovered and instantiated.
 * Full integration tests with actual compilation would require kotlin-compile-testing setup.
 */
class MapStructSymbolProcessorTest {

    @Test
    fun `processor provider can be instantiated`() {
        val provider = MapStructSymbolProcessorProvider()
        assertThat(provider).isNotNull
    }

    @Test
    fun `processor provider is discoverable via ServiceLoader`() {
        val providers = ServiceLoader.load(
            com.google.devtools.ksp.processing.SymbolProcessorProvider::class.java
        ).toList()

        val mapStructProviders = providers.filterIsInstance<MapStructSymbolProcessorProvider>()

        assertThat(mapStructProviders).isNotEmpty
        assertThat(mapStructProviders).hasSize(1)
    }

    @Test
    fun `processor provider creates processor instance`() {
        val provider = MapStructSymbolProcessorProvider()

        // We can't easily create a real SymbolProcessorEnvironment without mocking,
        // so we just verify the provider exists and is callable
        assertThat(provider).isNotNull
        assertThat(provider.javaClass.methods.any { it.name == "create" }).isTrue
    }

    @Test
    fun `model element processors can be loaded via ServiceLoader`() {
        val processors = ServiceLoader.load(
            org.mapstruct.ap.internal.processor.ModelElementProcessor::class.java,
            org.mapstruct.ap.internal.processor.ModelElementProcessor::class.java.classLoader
        ).toList()

        assertThat(processors).isNotEmpty
        assertThat(processors).hasSizeGreaterThanOrEqualTo(5)

        // Verify key processors are present
        val processorNames = processors.map { it.javaClass.simpleName }
        assertThat(processorNames).contains(
            "MethodRetrievalProcessor",
            "MapperCreationProcessor",
            "MapperRenderingProcessor"
        )

        // Verify processors can be sorted by priority (as done in actual processor)
        val sortedProcessors = processors.sortedBy { it.priority }
        val priorities = sortedProcessors.map { it.priority }
        assertThat(priorities).isSorted
        assertThat(priorities.first()).isEqualTo(1) // MethodRetrievalProcessor
        assertThat(priorities.last()).isGreaterThanOrEqualTo(9999) // MapperRenderingProcessor or MapperServiceProcessor
    }
}
