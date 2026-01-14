/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.source

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for expression mapping with @Mapping(expression = ...) in KSP processor.
 */
class ExpressionMappingTest {

    @Test
    fun shouldMapExpressionValue() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(
            val firstName: String,
            val lastName: String
        )

        data class Target(
            val fullName: String
        )

        @Mapper
        interface ExpressionMapper {
            @Mapping(target = "fullName", expression = "java(source.getFirstName() + \" \" + source.getLastName())")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ExpressionMapperImpl()
            val source = Source("John", "Doe")
            val target = mapper.map(source)

            assert(target.fullName == "John Doe") {
                "Expected fullName to be 'John Doe' but was '${'$'}{target.fullName}'"
            }
        }
    """)

    @Test
    fun shouldMapWithDefaultExpression() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(
            val name: String?
        )

        data class Target(
            val name: String
        )

        @Mapper
        interface DefaultExpressionMapper {
            @Mapping(target = "name", source = "name", defaultExpression = "java(\"Unknown\")")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = DefaultExpressionMapperImpl()

            // Test with null source property
            val sourceWithNull = Source(null)
            val targetWithDefault = mapper.map(sourceWithNull)
            assert(targetWithDefault.name == "Unknown") {
                "Expected name 'Unknown' but was '${'$'}{targetWithDefault.name}'"
            }

            // Test with non-null source property
            val sourceWithValue = Source("Alice")
            val targetWithValue = mapper.map(sourceWithValue)
            assert(targetWithValue.name == "Alice") {
                "Expected name 'Alice' but was '${'$'}{targetWithValue.name}'"
            }
        }
    """)

    @Test
    fun shouldMapWithMathExpression() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(
            val price: Int,
            val quantity: Int
        )

        data class Target(
            val total: Int
        )

        @Mapper
        interface MathExpressionMapper {
            @Mapping(target = "total", expression = "java(source.getPrice() * source.getQuantity())")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = MathExpressionMapperImpl()
            val source = Source(10, 5)
            val target = mapper.map(source)

            assert(target.total == 50) {
                "Expected total 50 but was ${'$'}{target.total}"
            }
        }
    """)

    @Test
    fun shouldMapWithStringConcatExpression() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val prefix: String, val suffix: String)
        data class Target(val combined: String)

        @Mapper
        interface ConcatExpressionMapper {
            @Mapping(target = "combined", expression = "java(source.getPrefix() + \"_\" + source.getSuffix())")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConcatExpressionMapperImpl()
            val source = Source("hello", "world")
            val target = mapper.map(source)

            assert(target.combined == "hello_world") {
                "Expected 'hello_world' but was '${'$'}{target.combined}'"
            }
        }
    """)

    @Test
    fun shouldMapWithConditionalExpression() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val score: Int)
        data class Target(val grade: String)

        @Mapper
        interface ConditionalExpressionMapper {
            @Mapping(target = "grade", expression = "java(source.getScore() >= 50 ? \"PASS\" : \"FAIL\")")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConditionalExpressionMapperImpl()

            val passing = Source(75)
            val passingTarget = mapper.map(passing)
            assert(passingTarget.grade == "PASS") { "Expected 'PASS' for score 75" }

            val failing = Source(30)
            val failingTarget = mapper.map(failing)
            assert(failingTarget.grade == "FAIL") { "Expected 'FAIL' for score 30" }
        }
    """)

    @Test
    fun shouldMapWithToUpperCaseExpression() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val name: String)
        data class Target(val name: String)

        @Mapper
        interface UpperCaseExpressionMapper {
            @Mapping(target = "name", expression = "java(source.getName().toUpperCase())")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = UpperCaseExpressionMapperImpl()
            val source = Source("hello")
            val target = mapper.map(source)

            assert(target.name == "HELLO") { "Expected 'HELLO' but was '${'$'}{target.name}'" }
        }
    """)

    @Test
    fun shouldMapWithLengthExpression() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val text: String)
        data class Target(val length: Int)

        @Mapper
        interface LengthExpressionMapper {
            @Mapping(target = "length", expression = "java(source.getText().length())")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = LengthExpressionMapperImpl()
            val source = Source("Hello World")
            val target = mapper.map(source)

            assert(target.length == 11) { "Expected 11 but was ${'$'}{target.length}" }
        }
    """)
}
