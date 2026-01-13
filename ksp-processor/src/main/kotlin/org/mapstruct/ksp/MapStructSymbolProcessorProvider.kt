// ABOUTME: KSP SymbolProcessorProvider that creates MapStruct symbol processor instances.
// ABOUTME: Entry point for KSP to discover and instantiate the MapStruct processor.
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

/**
 * Provider for the MapStruct KSP symbol processor. This is the entry point that KSP uses
 * to discover and instantiate the MapStruct processor.
 *
 * The provider is registered via META-INF/services and creates instances of
 * [MapStructSymbolProcessor] for each compilation.
 */
class MapStructSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return MapStructSymbolProcessor(environment)
    }
}
