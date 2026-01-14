/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.source

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for constant value mapping with @Mapping(constant = ...) in KSP processor.
 */
class ConstantMappingTest {

    @Test
    fun shouldMapConstantValue() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(
            val name: String
        )

        data class Target(
            val name: String,
            val type: String
        )

        @Mapper
        interface ConstantMapper {
            @Mapping(target = "type", constant = "PERSON")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConstantMapperImpl()
            val source = Source("Alice")
            val target = mapper.map(source)

            assert(target.name == "Alice") {
                "Expected name to be 'Alice' but was '${'$'}{target.name}'"
            }
            assert(target.type == "PERSON") {
                "Expected type to be 'PERSON' but was '${'$'}{target.type}'"
            }
        }
    """)

    @Test
    fun shouldMapMultipleConstants() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping
        import org.mapstruct.Mappings

        data class Source(val value: String)
        data class Target(val value: String, val version: String, val status: String)

        @Mapper
        interface MultiConstantMapper {
            @Mappings(
                Mapping(target = "version", constant = "1.0.0"),
                Mapping(target = "status", constant = "ACTIVE")
            )
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = MultiConstantMapperImpl()
            val source = Source("data")
            val target = mapper.map(source)

            assert(target.value == "data") { "Expected value 'data'" }
            assert(target.version == "1.0.0") { "Expected version '1.0.0'" }
            assert(target.status == "ACTIVE") { "Expected status 'ACTIVE'" }
        }
    """)

    @Test
    fun shouldMapConstantWithTypeConversion() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val name: String)
        data class Target(val name: String, val count: Int)

        @Mapper
        interface ConstantWithConversionMapper {
            @Mapping(target = "count", constant = "42")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConstantWithConversionMapperImpl()
            val source = Source("test")
            val target = mapper.map(source)

            assert(target.name == "test") { "Expected name 'test'" }
            assert(target.count == 42) { "Expected count 42 but was ${'$'}{target.count}" }
        }
    """)

    // TODO: Fix constant to Long conversion in KSP adapter
    @org.junit.jupiter.api.Disabled("KSP adapter has issues with String to Long constant conversion")
    @Test
    fun shouldMapConstantToLong() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val name: String)
        data class Target(val name: String, val id: Long)

        @Mapper
        interface ConstantLongMapper {
            @Mapping(target = "id", constant = "999999999")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConstantLongMapperImpl()
            val source = Source("test")
            val target = mapper.map(source)

            assert(target.name == "test") { "Expected name 'test'" }
            assert(target.id == 999999999L) { "Expected id 999999999L" }
        }
    """)

    @Test
    fun shouldMapConstantToBoolean() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val name: String)
        data class Target(val name: String, val active: Boolean)

        @Mapper
        interface ConstantBoolMapper {
            @Mapping(target = "active", constant = "true")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConstantBoolMapperImpl()
            val source = Source("test")
            val target = mapper.map(source)

            assert(target.name == "test") { "Expected name 'test'" }
            assert(target.active == true) { "Expected active true" }
        }
    """)
}
