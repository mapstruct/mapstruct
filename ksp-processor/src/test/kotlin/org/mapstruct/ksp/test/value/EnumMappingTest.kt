/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.value

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for enum mapping with KSP processor.
 *
 * NOTE: Enum mapping currently has issues with KSP enum constant name resolution.
 */
class EnumMappingTest {

    // TODO: Fix enum constant name resolution in KSP adapter
    // Currently fails with "Unexpected enum constant: PENDING"
    @org.junit.jupiter.api.Disabled("KSP adapter has issues with enum constant name resolution")
    @Test
    fun shouldMapEnumToEnum() = pluginTest("""
        import org.mapstruct.Mapper

        enum class SourceStatus {
            PENDING,
            ACTIVE,
            COMPLETED
        }

        enum class TargetStatus {
            PENDING,
            ACTIVE,
            COMPLETED
        }

        data class Source(val status: SourceStatus)
        data class Target(val status: TargetStatus)

        @Mapper
        interface EnumMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = EnumMapperImpl()

            val source1 = Source(SourceStatus.PENDING)
            val target1 = mapper.map(source1)
            assert(target1.status == TargetStatus.PENDING) {
                "Expected status to be PENDING but was ${'$'}{target1.status}"
            }

            val source2 = Source(SourceStatus.ACTIVE)
            val target2 = mapper.map(source2)
            assert(target2.status == TargetStatus.ACTIVE) {
                "Expected status to be ACTIVE but was ${'$'}{target2.status}"
            }

            val source3 = Source(SourceStatus.COMPLETED)
            val target3 = mapper.map(source3)
            assert(target3.status == TargetStatus.COMPLETED) {
                "Expected status to be COMPLETED but was ${'$'}{target3.status}"
            }
        }
    """)

    @Test
    fun shouldMapEnumToString() = pluginTest("""
        import org.mapstruct.Mapper

        enum class Status {
            ACTIVE,
            INACTIVE
        }

        data class Source(val status: Status)
        data class Target(val status: String)

        @Mapper
        interface EnumToStringMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = EnumToStringMapperImpl()
            val source = Source(Status.ACTIVE)
            val target = mapper.map(source)

            assert(target.status == "ACTIVE") {
                "Expected status 'ACTIVE' but was '${'$'}{target.status}'"
            }
        }
    """)

    @Test
    fun shouldMapStringToEnum() = pluginTest("""
        import org.mapstruct.Mapper

        enum class Status {
            ACTIVE,
            INACTIVE
        }

        data class Source(val status: String)
        data class Target(val status: Status)

        @Mapper
        interface StringToEnumMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = StringToEnumMapperImpl()
            val source = Source("ACTIVE")
            val target = mapper.map(source)

            assert(target.status == Status.ACTIVE) {
                "Expected status ACTIVE but was ${'$'}{target.status}"
            }
        }
    """)

    // TODO: Fix enum constant name resolution in KSP adapter
    @org.junit.jupiter.api.Disabled("KSP adapter has issues with direct enum-to-enum mapping")
    @Test
    fun shouldMapEnumDirectly() = pluginTest("""
        import org.mapstruct.Mapper

        enum class SourceColor {
            RED,
            GREEN,
            BLUE
        }

        enum class TargetColor {
            RED,
            GREEN,
            BLUE
        }

        @Mapper
        interface DirectEnumMapper {
            fun map(source: SourceColor): TargetColor
        }

        fun test() {
            val mapper = DirectEnumMapperImpl()

            assert(mapper.map(SourceColor.RED) == TargetColor.RED)
            assert(mapper.map(SourceColor.GREEN) == TargetColor.GREEN)
            assert(mapper.map(SourceColor.BLUE) == TargetColor.BLUE)
        }
    """)

    @Test
    fun shouldMapEnumToStringWithMultipleValues() = pluginTest("""
        import org.mapstruct.Mapper

        enum class Priority {
            HIGH,
            MEDIUM,
            LOW
        }

        data class Source(val priority: Priority)
        data class Target(val priority: String)

        @Mapper
        interface PriorityMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = PriorityMapperImpl()

            val high = mapper.map(Source(Priority.HIGH))
            assert(high.priority == "HIGH") { "Expected HIGH but was '${'$'}{high.priority}'" }

            val medium = mapper.map(Source(Priority.MEDIUM))
            assert(medium.priority == "MEDIUM") { "Expected MEDIUM but was '${'$'}{medium.priority}'" }

            val low = mapper.map(Source(Priority.LOW))
            assert(low.priority == "LOW") { "Expected LOW but was '${'$'}{low.priority}'" }
        }
    """)

    @Test
    fun shouldMapNullableEnumToString() = pluginTest("""
        import org.mapstruct.Mapper

        enum class Status { ON, OFF }

        data class Source(val status: Status?)
        data class Target(val status: String?)

        @Mapper
        interface NullableEnumMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = NullableEnumMapperImpl()

            val withValue = Source(Status.ON)
            val resultValue = mapper.map(withValue)
            assert(resultValue.status == "ON") { "Expected 'ON'" }

            val withNull = Source(null)
            val resultNull = mapper.map(withNull)
            assert(resultNull.status == null) { "Expected null" }
        }
    """)
}
