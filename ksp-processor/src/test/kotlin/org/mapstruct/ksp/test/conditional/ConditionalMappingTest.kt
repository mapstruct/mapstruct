/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.conditional

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for conditional mapping with KSP processor.
 */
class ConditionalMappingTest {

    @Test
    fun shouldMapOnlyNonEmptyStrings() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping
        import org.mapstruct.Condition

        data class Source(val name: String?, val description: String?)

        class Target {
            var name: String = "default"
            var description: String = "default"
        }

        @Mapper
        abstract class ConditionalMapper {
            @Condition
            fun isNotEmpty(value: String?): Boolean {
                return !value.isNullOrEmpty()
            }

            abstract fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConditionalMapperImpl()

            // Test with non-empty values
            val source1 = Source("Hello", "World")
            val target1 = mapper.map(source1)
            assert(target1.name == "Hello") {
                "Expected name 'Hello' but was '${'$'}{target1.name}'"
            }
            assert(target1.description == "World") {
                "Expected description 'World' but was '${'$'}{target1.description}'"
            }
        }
    """)

    @Test
    fun shouldApplyConditionInInterfaceMapper() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Condition

        data class Source(val value: String?)

        class Target {
            var value: String = "unchanged"
        }

        @Mapper
        interface ConditionalInterfaceMapper {
            fun map(source: Source): Target

            @Condition
            fun hasValue(value: String?): Boolean {
                return value != null && value.length > 0
            }
        }

        fun test() {
            val mapper = ConditionalInterfaceMapperImpl()

            // Test with non-empty value
            val source1 = Source("test")
            val target1 = mapper.map(source1)
            assert(target1.value == "test") {
                "Expected value 'test' but was '${'$'}{target1.value}'"
            }
        }
    """)

    @Test
    fun shouldMapWithConditionExpression() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val status: String)

        data class Target(val active: Boolean)

        @Mapper
        interface ConditionExpressionMapper {
            @Mapping(target = "active", expression = "java(source.getStatus().equals(\"ACTIVE\"))")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConditionExpressionMapperImpl()

            val activeSource = Source("ACTIVE")
            val activeTarget = mapper.map(activeSource)
            assert(activeTarget.active == true) {
                "Expected active=true for ACTIVE status"
            }

            val inactiveSource = Source("INACTIVE")
            val inactiveTarget = mapper.map(inactiveSource)
            assert(inactiveTarget.active == false) {
                "Expected active=false for INACTIVE status"
            }
        }
    """)

    @Test
    fun shouldApplyConditionForTypeConversion() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Condition

        data class Source(val number: String?)

        class Target {
            var number: String = "0"
        }

        @Mapper
        abstract class NumericConditionMapper {
            @Condition
            fun isNumeric(value: String?): Boolean {
                return value != null && value.all { it.isDigit() }
            }

            abstract fun map(source: Source): Target
        }

        fun test() {
            val mapper = NumericConditionMapperImpl()

            val numericSource = Source("123")
            val numericTarget = mapper.map(numericSource)
            assert(numericTarget.number == "123") { "Expected number '123'" }
        }
    """)
}
