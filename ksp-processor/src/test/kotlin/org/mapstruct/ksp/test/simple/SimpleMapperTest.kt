/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.simple

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for simple property mapping with KSP processor.
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

    @Test
    fun shouldMapPropertiesWithDifferentNames() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(
            val sourceValue: String
        )

        data class Target(
            val targetValue: String
        )

        @Mapper
        interface RenamedPropertyMapper {
            @Mapping(source = "sourceValue", target = "targetValue")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = RenamedPropertyMapperImpl()
            val source = Source("Alice")
            val target = mapper.map(source)

            assert(target.targetValue == "Alice") { "Expected targetValue to be 'Alice' but was '${'$'}{target.targetValue}'" }
        }
    """)

    @Test
    fun shouldMapMultipleProperties() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val firstName: String,
            val lastName: String,
            val age: Int,
            val email: String,
            val active: Boolean
        )

        data class Target(
            val firstName: String,
            val lastName: String,
            val age: Int,
            val email: String,
            val active: Boolean
        )

        @Mapper
        interface MultiPropertyMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = MultiPropertyMapperImpl()
            val source = Source("John", "Doe", 30, "john@example.com", true)
            val target = mapper.map(source)

            assert(target.firstName == "John") {
                "Expected firstName 'John' but was '${'$'}{target.firstName}'"
            }
            assert(target.lastName == "Doe") {
                "Expected lastName 'Doe' but was '${'$'}{target.lastName}'"
            }
            assert(target.age == 30) {
                "Expected age 30 but was ${'$'}{target.age}"
            }
            assert(target.email == "john@example.com") {
                "Expected email 'john@example.com' but was '${'$'}{target.email}'"
            }
            assert(target.active == true) {
                "Expected active true but was ${'$'}{target.active}"
            }
        }
    """)

    @Test
    fun shouldMapWithMixedTypes() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val id: Long,
            val score: Double,
            val count: Short,
            val flag: Boolean,
            val initial: Char
        )

        data class Target(
            val id: Long,
            val score: Double,
            val count: Short,
            val flag: Boolean,
            val initial: Char
        )

        @Mapper
        interface MixedTypeMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = MixedTypeMapperImpl()
            val source = Source(12345L, 99.5, 10.toShort(), true, 'A')
            val target = mapper.map(source)

            assert(target.id == 12345L) {
                "Expected id 12345L but was ${'$'}{target.id}"
            }
            assert(target.score == 99.5) {
                "Expected score 99.5 but was ${'$'}{target.score}"
            }
            assert(target.count == 10.toShort()) {
                "Expected count 10 but was ${'$'}{target.count}"
            }
            assert(target.flag == true) {
                "Expected flag true but was ${'$'}{target.flag}"
            }
            assert(target.initial == 'A') {
                "Expected initial 'A' but was '${'$'}{target.initial}'"
            }
        }
    """)

    @Test
    fun shouldMapEmptyDataClass() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val placeholder: String = "")
        data class Target(val placeholder: String = "")

        @Mapper
        interface EmptyMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = EmptyMapperImpl()
            val source = Source()
            val target = mapper.map(source)

            assert(target.placeholder == "") {
                "Expected empty placeholder but was '${'$'}{target.placeholder}'"
            }
        }
    """)

    @Test
    fun shouldMapNullableProperty() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val name: String?)
        data class Target(val name: String?)

        @Mapper
        interface NullableMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = NullableMapperImpl()

            val withValue = Source("test")
            val resultValue = mapper.map(withValue)
            assert(resultValue.name == "test") { "Expected name 'test'" }

            val withNull = Source(null)
            val resultNull = mapper.map(withNull)
            assert(resultNull.name == null) { "Expected null name" }
        }
    """)

    @Test
    fun shouldMapWithSingleProperty() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val id: Long)
        data class Target(val id: Long)

        @Mapper
        interface SinglePropertyMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = SinglePropertyMapperImpl()
            val source = Source(12345L)
            val target = mapper.map(source)

            assert(target.id == 12345L) { "Expected id 12345L but was ${'$'}{target.id}" }
        }
    """)

    @Test
    fun shouldMapFloatProperty() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: Float)
        data class Target(val value: Float)

        @Mapper
        interface FloatMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = FloatMapperImpl()
            val source = Source(3.14f)
            val target = mapper.map(source)

            assert(target.value == 3.14f) { "Expected value 3.14f but was ${'$'}{target.value}" }
        }
    """)
}
