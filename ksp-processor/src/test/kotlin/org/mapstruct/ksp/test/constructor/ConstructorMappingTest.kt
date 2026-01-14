/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.constructor

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for constructor-based mapping with KSP processor.
 */
class ConstructorMappingTest {

    @Test
    fun shouldMapUsingConstructor() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val name: String,
            val age: Int
        )

        // Target is immutable (data class with val properties)
        data class Target(
            val name: String,
            val age: Int
        )

        @Mapper
        interface ConstructorMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConstructorMapperImpl()
            val source = Source("Alice", 30)
            val target = mapper.map(source)

            assert(target.name == "Alice") {
                "Expected name to be 'Alice' but was '${'$'}{target.name}'"
            }
            assert(target.age == 30) {
                "Expected age to be 30 but was ${'$'}{target.age}"
            }
        }
    """)

    @Test
    fun shouldMapWithNullableConstructorParams() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val name: String?,
            val description: String?
        )

        data class Target(
            val name: String?,
            val description: String?
        )

        @Mapper
        interface NullableConstructorMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = NullableConstructorMapperImpl()

            val sourceWithValues = Source("Test", "Description")
            val targetWithValues = mapper.map(sourceWithValues)
            assert(targetWithValues.name == "Test") { "Expected name 'Test'" }
            assert(targetWithValues.description == "Description") { "Expected description" }

            val sourceWithNulls = Source(null, null)
            val targetWithNulls = mapper.map(sourceWithNulls)
            assert(targetWithNulls.name == null) { "Expected null name" }
            assert(targetWithNulls.description == null) { "Expected null description" }
        }
    """)

    @Test
    fun shouldMapWithManyConstructorParams() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val field1: String,
            val field2: Int,
            val field3: Boolean,
            val field4: Long,
            val field5: Double
        )

        data class Target(
            val field1: String,
            val field2: Int,
            val field3: Boolean,
            val field4: Long,
            val field5: Double
        )

        @Mapper
        interface ManyParamsMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ManyParamsMapperImpl()
            val source = Source("a", 1, true, 100L, 3.14)
            val target = mapper.map(source)

            assert(target.field1 == "a") { "Expected field1 'a'" }
            assert(target.field2 == 1) { "Expected field2 1" }
            assert(target.field3 == true) { "Expected field3 true" }
            assert(target.field4 == 100L) { "Expected field4 100L" }
            assert(target.field5 == 3.14) { "Expected field5 3.14" }
        }
    """)

    @Test
    fun shouldMapWithTypeConversionInConstructor() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: Int)
        data class Target(val value: String)

        @Mapper
        interface ConversionConstructorMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConversionConstructorMapperImpl()
            val source = Source(42)
            val target = mapper.map(source)

            assert(target.value == "42") { "Expected value '42' but was '${'$'}{target.value}'" }
        }
    """)
}
