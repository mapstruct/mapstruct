/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.injectionstrategy

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for component model generation with KSP processor.
 *
 * NOTE: Spring component tests require Spring on the classpath.
 * This test verifies the default component model works.
 */
class ComponentModelTest {

    @Test
    fun shouldGenerateDefaultComponentModel() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val name: String)
        data class Target(val name: String)

        @Mapper  // Default component model - no DI
        interface DefaultMapper {
            fun map(source: Source): Target
        }

        fun test() {
            // Verify that the mapper can be instantiated directly
            val mapper = DefaultMapperImpl()
            val source = Source("test")
            val target = mapper.map(source)
            assert(target.name == "test") {
                "Expected name to be 'test' but was '${'$'}{target.name}'"
            }
        }
    """)

    // Spring component test requires Spring on classpath
    @org.junit.jupiter.api.Disabled("Requires Spring dependency on test classpath")
    @Test
    fun shouldGenerateSpringComponent() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val name: String)
        data class Target(val name: String)

        @Mapper(componentModel = "spring")
        interface SpringMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = SpringMapperImpl()
            val source = Source("test")
            val target = mapper.map(source)
            assert(target.name == "test")
        }
    """)
}
