/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.array

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for array type mapping with KSP processor.
 *
 * NOTE: Array mapping currently has issues with KSP type conversion.
 * Kotlin IntArray/Array<T> aren't properly converted to Java int[]/T[].
 */
class ArrayMappingTest {

    // TODO: Fix KSP adapter to properly convert Kotlin array types to Java array types
    // Currently fails because IntArray generates as "IntArray" in Java instead of "int[]"
    @org.junit.jupiter.api.Disabled("KSP adapter doesn't properly convert Kotlin array types to Java array types")
    @Test
    fun shouldMapPrimitiveArrayToArray() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val values: IntArray)
        data class Target(val values: IntArray)

        @Mapper
        interface ArrayMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ArrayMapperImpl()
            val source = Source(intArrayOf(1, 2, 3))
            val target = mapper.map(source)

            assert(target.values.contentEquals(intArrayOf(1, 2, 3))) {
                "Expected values to be [1, 2, 3] but was '${'$'}{target.values.toList()}'"
            }
        }
    """)
}
