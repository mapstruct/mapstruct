/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.simple

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Basic test for simple property mapping with KSP processor.
 */
class SimpleMapperTest {

    @Test
    fun shouldGenerateSimpleMapper() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val name: String,
            val age: Int
        )

        data class Target(
            val name: String,
            val age: Int
        )

        @Mapper
        interface SimpleMapper {
            fun sourceToTarget(source: Source): Target
        }

        fun test() {
            val mapper = SimpleMapperImpl()
            val source = Source("John Doe", 30)
            val target = mapper.sourceToTarget(source)

            assert(target.name == "John Doe") { "Expected name to be 'John Doe' but was '${'$'}{target.name}'" }
            assert(target.age == 30) { "Expected age to be 30 but was ${'$'}{target.age}" }
        }
    """)
}
