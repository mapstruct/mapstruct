// ABOUTME: Adapts KSP's SymbolProcessorEnvironment to look like javax.annotation.processing.ProcessingEnvironment.
// ABOUTME: Provides bridge between KSP and existing MapStruct infrastructure that expects Java annotation processing types.
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import java.util.*

/**
 * Adapts KSP's [SymbolProcessorEnvironment] to implement [ProcessingEnvironment].
 * This allows reuse of existing MapStruct infrastructure that expects Java annotation processing types.
 *
 * Key adaptations:
 * - KSP Logger → Messager
 * - KSP CodeGenerator → Filer
 * - KSP Resolver → Elements/Types utilities
 * - KSP options → ProcessingEnvironment options
 */
class KspProcessingEnvironmentAdapter(
    private val environment: SymbolProcessorEnvironment,
    private val resolver: Resolver,
     private val logger: KSPLogger
) : ProcessingEnvironment {

//    private val messagerAdapter by lazy { KspMessagerAdapter(environment.logger) }
//    private val filerAdapter by lazy { KspFilerAdapter(environment.codeGenerator) }
//    private val elementsAdapter by lazy { KspElementsAdapter(resolver) }
//    private val typesAdapter by lazy { KspTypesAdapter(resolver) }

    override fun getOptions(): Map<String, String> {
        return environment.options
    }

    override fun getMessager(): Messager = KspMessager(logger)

    override fun getFiler(): Filer {
        TODO()
//        return filerAdapter
    }

    override fun getElementUtils(): Elements = KspElements(environment, resolver, logger)

    override fun getTypeUtils(): Types = KspTypes(environment, resolver, logger)

    override fun getSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getLocale(): Locale {
        return Locale.getDefault()
    }
}
