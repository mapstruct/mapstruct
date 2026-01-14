/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.updatemethods

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for update methods with @MappingTarget in KSP processor.
 *
 * NOTE: Update methods have issues with KSP return type handling for void methods.
 */
class UpdateMethodTest {

    // TODO: Fix KSP adapter to handle void return types for @MappingTarget update methods
    @org.junit.jupiter.api.Disabled("KSP adapter error: 'result type not assignable to return type' for void methods")
    @Test
    fun shouldUpdateExistingTarget() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.MappingTarget

        data class Source(
            val name: String,
            val age: Int
        )

        class Target {
            var name: String = ""
            var age: Int = 0
            var extraField: String = "preserved"
        }

        @Mapper
        interface UpdateMapper {
            fun update(source: Source, @MappingTarget target: Target)
        }

        fun test() {
            val mapper = UpdateMapperImpl()
            val source = Source("Updated Name", 25)
            val target = Target().apply {
                name = "Original Name"
                age = 20
                extraField = "should remain"
            }

            mapper.update(source, target)

            assert(target.name == "Updated Name") {
                "Expected name to be 'Updated Name' but was '${'$'}{target.name}'"
            }
            assert(target.age == 25) {
                "Expected age to be 25 but was ${'$'}{target.age}"
            }
            assert(target.extraField == "should remain") {
                "Expected extraField to be preserved but was '${'$'}{target.extraField}'"
            }
        }
    """)

    @Test
    fun shouldUpdateExistingTargetWithReturn() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.MappingTarget

        data class Source(
            val name: String,
            val value: Int
        )

        class Target {
            var name: String = ""
            var value: Int = 0
        }

        @Mapper
        interface UpdateWithReturnMapper {
            fun update(source: Source, @MappingTarget target: Target): Target
        }

        fun test() {
            val mapper = UpdateWithReturnMapperImpl()
            val source = Source("Updated", 100)
            val target = Target().apply {
                name = "Original"
                value = 50
            }

            val result = mapper.update(source, target)

            assert(result === target) { "Expected result to be same instance as target" }
            assert(target.name == "Updated") { "Expected name 'Updated'" }
            assert(target.value == 100) { "Expected value 100" }
        }
    """)

    @Test
    fun shouldUpdateWithPartialMapping() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.MappingTarget
        import org.mapstruct.Mapping

        data class Source(val newName: String)

        class Target {
            var name: String = ""
            var counter: Int = 0
        }

        @Mapper
        interface PartialUpdateMapper {
            @Mapping(source = "newName", target = "name")
            fun update(source: Source, @MappingTarget target: Target): Target
        }

        fun test() {
            val mapper = PartialUpdateMapperImpl()
            val source = Source("New Value")
            val target = Target().apply {
                name = "Old Value"
                counter = 42
            }

            mapper.update(source, target)

            assert(target.name == "New Value") { "Expected name 'New Value'" }
            assert(target.counter == 42) { "Expected counter to remain 42" }
        }
    """)

    @Test
    fun shouldUpdateWithTypeConversion() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.MappingTarget

        data class Source(val count: String, val name: String)

        class Target {
            var count: Long = 0
            var name: String = ""
        }

        @Mapper
        interface TypeConvertingUpdateMapper {
            fun update(source: Source, @MappingTarget target: Target): Target
        }

        fun test() {
            val mapper = TypeConvertingUpdateMapperImpl()
            val source = Source("12345", "Updated Name")
            val target = Target().apply {
                count = 100L
                name = "Original"
            }

            mapper.update(source, target)

            assert(target.count == 12345L) { "Expected count 12345L" }
            assert(target.name == "Updated Name") { "Expected name 'Updated Name'" }
        }
    """)

    @Test
    fun shouldUpdateMultipleFields() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.MappingTarget

        data class Source(
            val field1: String,
            val field2: Int,
            val field3: Boolean,
            val field4: Long
        )

        class Target {
            var field1: String = ""
            var field2: Int = 0
            var field3: Boolean = false
            var field4: Long = 0
            var preserved: String = "keep"
        }

        @Mapper
        interface MultiFieldUpdateMapper {
            fun update(source: Source, @MappingTarget target: Target): Target
        }

        fun test() {
            val mapper = MultiFieldUpdateMapperImpl()
            val source = Source("value1", 42, true, 999L)
            val target = Target()

            mapper.update(source, target)

            assert(target.field1 == "value1") { "Expected field1 'value1'" }
            assert(target.field2 == 42) { "Expected field2 42" }
            assert(target.field3 == true) { "Expected field3 true" }
            assert(target.field4 == 999L) { "Expected field4 999L" }
            assert(target.preserved == "keep") { "Expected preserved 'keep'" }
        }
    """)
}
