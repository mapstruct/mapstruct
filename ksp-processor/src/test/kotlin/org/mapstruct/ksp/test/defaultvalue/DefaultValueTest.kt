/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.defaultvalue

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for default value mapping with @Mapping(defaultValue = ...) in KSP processor.
 */
class DefaultValueTest {

    @Test
    fun shouldMapDefaultValue() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(
            val name: String,
            val nickname: String?
        )

        data class Target(
            val name: String,
            val nickname: String
        )

        @Mapper
        interface DefaultValueMapper {
            @Mapping(target = "nickname", source = "nickname", defaultValue = "N/A")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = DefaultValueMapperImpl()

            // Test with non-null nickname - should use actual value
            val source1 = Source("Alice", "Ali")
            val target1 = mapper.map(source1)
            assert(target1.nickname == "Ali") {
                "Expected nickname to be 'Ali' but was '${'$'}{target1.nickname}'"
            }

            // Test with null nickname - should use default value
            val source2 = Source("Bob", null)
            val target2 = mapper.map(source2)
            assert(target2.nickname == "N/A") {
                "Expected nickname to be 'N/A' (default) but was '${'$'}{target2.nickname}'"
            }
        }
    """)

    @Test
    fun shouldMapMultipleDefaultValues() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping
        import org.mapstruct.Mappings

        data class Source(val title: String?, val category: String?, val status: String?)
        data class Target(val title: String, val category: String, val status: String)

        @Mapper
        interface MultiDefaultMapper {
            @Mappings(
                Mapping(target = "title", source = "title", defaultValue = "Untitled"),
                Mapping(target = "category", source = "category", defaultValue = "General"),
                Mapping(target = "status", source = "status", defaultValue = "Draft")
            )
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = MultiDefaultMapperImpl()

            val allNulls = Source(null, null, null)
            val result1 = mapper.map(allNulls)
            assert(result1.title == "Untitled") { "Expected title 'Untitled'" }
            assert(result1.category == "General") { "Expected category 'General'" }
            assert(result1.status == "Draft") { "Expected status 'Draft'" }

            val partial = Source("My Article", null, "Published")
            val result2 = mapper.map(partial)
            assert(result2.title == "My Article") { "Expected title 'My Article'" }
            assert(result2.category == "General") { "Expected category 'General'" }
            assert(result2.status == "Published") { "Expected status 'Published'" }
        }
    """)

    @Test
    fun shouldMapDefaultValueWithTypeConversion() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val count: String?)
        data class Target(val count: Int)

        @Mapper
        interface DefaultWithConversionMapper {
            @Mapping(target = "count", source = "count", defaultValue = "0")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = DefaultWithConversionMapperImpl()

            val withValue = Source("42")
            val result1 = mapper.map(withValue)
            assert(result1.count == 42) { "Expected count 42 but was ${'$'}{result1.count}" }

            val withNull = Source(null)
            val result2 = mapper.map(withNull)
            assert(result2.count == 0) { "Expected count 0 (default) but was ${'$'}{result2.count}" }
        }
    """)

    // TODO: Fix default value to Long conversion in KSP adapter
    @org.junit.jupiter.api.Disabled("KSP adapter has issues with String to Long default value conversion")
    @Test
    fun shouldMapDefaultValueWithLongConversion() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val value: String?)
        data class Target(val value: Long)

        @Mapper
        interface DefaultLongMapper {
            @Mapping(target = "value", source = "value", defaultValue = "100")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = DefaultLongMapperImpl()

            val withValue = Source("999")
            val result1 = mapper.map(withValue)
            assert(result1.value == 999L) { "Expected value 999L" }

            val withNull = Source(null)
            val result2 = mapper.map(withNull)
            assert(result2.value == 100L) { "Expected value 100L (default)" }
        }
    """)

    @Test
    fun shouldMapDefaultValueWithBooleanConversion() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val flag: String?)
        data class Target(val flag: Boolean)

        @Mapper
        interface DefaultBoolMapper {
            @Mapping(target = "flag", source = "flag", defaultValue = "true")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = DefaultBoolMapperImpl()

            val withFalse = Source("false")
            val result1 = mapper.map(withFalse)
            assert(result1.flag == false) { "Expected flag false" }

            val withNull = Source(null)
            val result2 = mapper.map(withNull)
            assert(result2.flag == true) { "Expected flag true (default)" }
        }
    """)
}
