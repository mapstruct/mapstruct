/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.nullvalue

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for null value handling with KSP processor.
 */
class NullValueMappingTest {

    @Test
    fun shouldReturnNullWhenSourceIsNull() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val name: String)
        data class Target(val name: String)

        @Mapper
        interface NullMapper {
            fun map(source: Source?): Target?
        }

        fun test() {
            val mapper = NullMapperImpl()
            val target = mapper.map(null)

            assert(target == null) {
                "Expected null when source is null but got ${'$'}target"
            }
        }
    """)

    @Test
    fun shouldMapNonNullSourceCorrectly() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val name: String)
        data class Target(val name: String)

        @Mapper
        interface NonNullMapper {
            fun map(source: Source?): Target?
        }

        fun test() {
            val mapper = NonNullMapperImpl()
            val source = Source("Hello")
            val target = mapper.map(source)

            assert(target != null) {
                "Expected non-null target but got null"
            }
            assert(target!!.name == "Hello") {
                "Expected name 'Hello' but got '${'$'}{target.name}'"
            }
        }
    """)

    @Test
    fun shouldHandleNullablePropertyInSource() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val name: String?)
        data class Target(val name: String?)

        @Mapper
        interface NullablePropertyMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = NullablePropertyMapperImpl()

            // Test with null property
            val sourceWithNull = Source(null)
            val targetWithNull = mapper.map(sourceWithNull)
            assert(targetWithNull.name == null) {
                "Expected null name but got '${'$'}{targetWithNull.name}'"
            }

            // Test with non-null property
            val sourceWithValue = Source("World")
            val targetWithValue = mapper.map(sourceWithValue)
            assert(targetWithValue.name == "World") {
                "Expected 'World' but got '${'$'}{targetWithValue.name}'"
            }
        }
    """)

    @Test
    fun shouldMapNullableToNonNullableWithDefaultValue() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val value: String?)
        data class Target(val value: String)

        @Mapper
        interface NullableToNonNullMapper {
            @Mapping(source = "value", target = "value", defaultValue = "default")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = NullableToNonNullMapperImpl()

            // When source property is null, should use default
            val sourceWithNull = Source(null)
            val targetWithNull = mapper.map(sourceWithNull)
            assert(targetWithNull.value == "default") {
                "Expected 'default' but got '${'$'}{targetWithNull.value}'"
            }

            // When source property is not null, should use value
            val sourceWithValue = Source("actual")
            val targetWithValue = mapper.map(sourceWithValue)
            assert(targetWithValue.value == "actual") {
                "Expected 'actual' but got '${'$'}{targetWithValue.value}'"
            }
        }
    """)

    @Test
    fun shouldHandleMultipleNullableStringProperties() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val firstName: String?,
            val lastName: String?,
            val email: String?
        )

        data class Target(
            val firstName: String?,
            val lastName: String?,
            val email: String?
        )

        @Mapper
        interface MultiNullableMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = MultiNullableMapperImpl()

            val source = Source("John", null, "john@test.com")
            val target = mapper.map(source)

            assert(target.firstName == "John") { "Expected firstName 'John'" }
            assert(target.lastName == null) { "Expected lastName null" }
            assert(target.email == "john@test.com") { "Expected email 'john@test.com'" }
        }
    """)

    @Test
    fun shouldHandleNullableLongProperty() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val count: Long?)
        data class Target(val count: Long?)

        @Mapper
        interface NullableLongMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = NullableLongMapperImpl()

            val withValue = Source(42L)
            val resultValue = mapper.map(withValue)
            assert(resultValue.count == 42L) { "Expected count 42" }

            val withNull = Source(null)
            val resultNull = mapper.map(withNull)
            assert(resultNull.count == null) { "Expected null count" }
        }
    """)

    @Test
    fun shouldHandleAllNullProperties() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val a: String?, val b: String?)
        data class Target(val a: String?, val b: String?)

        @Mapper
        interface AllNullMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = AllNullMapperImpl()

            val allNull = Source(null, null)
            val result = mapper.map(allNull)
            assert(result.a == null) { "Expected a null" }
            assert(result.b == null) { "Expected b null" }
        }
    """)
}
