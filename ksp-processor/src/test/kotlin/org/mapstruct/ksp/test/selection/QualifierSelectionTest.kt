/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.selection

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for method selection with @Named qualifier in KSP processor.
 */
class QualifierSelectionTest {

    @Test
    fun shouldSelectMethodByNamedQualifier() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping
        import org.mapstruct.Named

        data class Source(val value: String)
        data class Target(val formatted: String)

        class StringFormatter {
            @Named("uppercase")
            fun toUpperCase(value: String): String = value.uppercase()

            @Named("lowercase")
            fun toLowerCase(value: String): String = value.lowercase()
        }

        @Mapper(uses = [StringFormatter::class])
        interface QualifierMapper {
            @Mapping(source = "value", target = "formatted", qualifiedByName = ["uppercase"])
            fun mapToUpper(source: Source): Target

            @Mapping(source = "value", target = "formatted", qualifiedByName = ["lowercase"])
            fun mapToLower(source: Source): Target
        }

        fun test() {
            val mapper = QualifierMapperImpl()

            val source = Source("Hello World")

            val upper = mapper.mapToUpper(source)
            assert(upper.formatted == "HELLO WORLD") {
                "Expected uppercase 'HELLO WORLD' but was '${'$'}{upper.formatted}'"
            }

            val lower = mapper.mapToLower(source)
            assert(lower.formatted == "hello world") {
                "Expected lowercase 'hello world' but was '${'$'}{lower.formatted}'"
            }
        }
    """)

    @Test
    fun shouldSelectMethodInMapperByNamedQualifier() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping
        import org.mapstruct.Named

        data class Source(val value: String)
        data class Target(val processed: String)

        @Mapper
        interface InlineQualifierMapper {
            @Mapping(source = "value", target = "processed", qualifiedByName = ["transform"])
            fun map(source: Source): Target

            @Named("transform")
            fun transformValue(input: String): String {
                return "Transformed: " + input
            }

            @Named("other")
            fun otherTransform(input: String): String {
                return "Other: " + input
            }
        }

        fun test() {
            val mapper = InlineQualifierMapperImpl()
            val source = Source("test")
            val target = mapper.map(source)

            assert(target.processed == "Transformed: test") {
                "Expected 'Transformed: test' but was '${'$'}{target.processed}'"
            }
        }
    """)

    @Test
    fun shouldSelectTypeConversionMethod() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: Int)
        data class Target(val value: String)

        class IntFormatter {
            fun format(value: Int): String {
                return "Number: " + value
            }
        }

        @Mapper(uses = [IntFormatter::class])
        interface TypeConversionSelectionMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = TypeConversionSelectionMapperImpl()
            val source = Source(42)
            val target = mapper.map(source)

            assert(target.value == "Number: 42") {
                "Expected 'Number: 42' but was '${'$'}{target.value}'"
            }
        }
    """)

    @Test
    fun shouldSelectMostSpecificMethod() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: String, val number: Int)
        data class Target(val value: String, val number: String)

        class Converters {
            // More specific method for String
            fun toString(value: String): String = "String: " + value

            // Generic method for Int
            fun intToString(value: Int): String = "Int: " + value
        }

        @Mapper(uses = [Converters::class])
        interface SpecificMethodMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = SpecificMethodMapperImpl()
            val source = Source("test", 42)
            val target = mapper.map(source)

            assert(target.value == "String: test") { "Expected 'String: test' but was '${'$'}{target.value}'" }
            assert(target.number == "Int: 42") { "Expected 'Int: 42' but was '${'$'}{target.number}'" }
        }
    """)

    @Test
    fun shouldUseQualifierForMultipleProperties() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping
        import org.mapstruct.Named
        import org.mapstruct.Mappings

        data class Source(val first: String, val second: String)
        data class Target(val first: String, val second: String)

        @Mapper
        interface MultiPropertyQualifierMapper {
            @Mappings(
                Mapping(source = "first", target = "first", qualifiedByName = ["addPrefix"]),
                Mapping(source = "second", target = "second", qualifiedByName = ["addSuffix"])
            )
            fun map(source: Source): Target

            @Named("addPrefix")
            fun addPrefixMethod(s: String): String = "PRE_" + s

            @Named("addSuffix")
            fun addSuffixMethod(s: String): String = s + "_SUF"
        }

        fun test() {
            val mapper = MultiPropertyQualifierMapperImpl()
            val source = Source("A", "B")
            val target = mapper.map(source)

            assert(target.first == "PRE_A") { "Expected 'PRE_A'" }
            assert(target.second == "B_SUF") { "Expected 'B_SUF'" }
        }
    """)
}
