/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.generics

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for generic type mapping with KSP processor.
 *
 * NOTE: Generic collection mapping has issues with KSP adapter type handling.
 */
class GenericsMappingTest {

    // TODO: Fix KSP adapter to handle generic collection types (List<T>, Set<T>, Map<K,V>)
    @org.junit.jupiter.api.Disabled("KSP adapter error: 'Can't generate mapping method for a generic type variable source'")
    @Test
    fun shouldMapWithGenericListProperty() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val items: List<String>)
        data class Target(val items: List<String>)

        @Mapper
        interface GenericListMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = GenericListMapperImpl()
            val source = Source(listOf("a", "b", "c"))
            val target = mapper.map(source)

            assert(target.items == listOf("a", "b", "c")) {
                "Expected items [a, b, c] but was ${'$'}{target.items}"
            }
        }
    """)

    // TODO: Fix KSP adapter to handle nested generic types
    @org.junit.jupiter.api.Disabled("KSP adapter error: 'Can't generate mapping method for a generic type variable source'")
    @Test
    fun shouldMapNestedGenericTypes() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val data: Map<String, List<Int>>)
        data class Target(val data: Map<String, List<Int>>)

        @Mapper
        interface NestedGenericMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = NestedGenericMapperImpl()
            val sourceData = mapOf(
                "numbers" to listOf(1, 2, 3),
                "more" to listOf(4, 5)
            )
            val source = Source(sourceData)
            val target = mapper.map(source)

            assert(target.data["numbers"] == listOf(1, 2, 3)) {
                "Expected numbers [1, 2, 3] but was ${'$'}{target.data["numbers"]}"
            }
            assert(target.data["more"] == listOf(4, 5)) {
                "Expected more [4, 5] but was ${'$'}{target.data["more"]}"
            }
        }
    """)

    // TODO: Fix KSP adapter to handle generic Set types
    @org.junit.jupiter.api.Disabled("KSP adapter error: 'Can't generate mapping method for a generic type variable source'")
    @Test
    fun shouldMapGenericSetProperty() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val tags: Set<String>)
        data class Target(val tags: Set<String>)

        @Mapper
        interface GenericSetMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = GenericSetMapperImpl()
            val source = Source(setOf("tag1", "tag2", "tag3"))
            val target = mapper.map(source)

            assert(target.tags.containsAll(setOf("tag1", "tag2", "tag3"))) {
                "Expected tags to contain [tag1, tag2, tag3] but was ${'$'}{target.tags}"
            }
            assert(target.tags.size == 3) {
                "Expected 3 tags but was ${'$'}{target.tags.size}"
            }
        }
    """)

    // TODO: Fix KSP adapter to handle Map with complex value types
    @org.junit.jupiter.api.Disabled("KSP adapter error: 'Can't generate mapping method for a generic type variable source'")
    @Test
    fun shouldMapMapWithComplexValueType() = pluginTest("""
        import org.mapstruct.Mapper

        data class ItemSource(val value: String)
        data class ItemTarget(val value: String)
        data class Source(val items: Map<String, ItemSource>)
        data class Target(val items: Map<String, ItemTarget>)

        @Mapper
        interface ComplexMapMapper {
            fun map(source: Source): Target
            fun mapItem(source: ItemSource): ItemTarget
        }

        fun test() {
            val mapper = ComplexMapMapperImpl()
            val source = Source(mapOf(
                "key1" to ItemSource("value1"),
                "key2" to ItemSource("value2")
            ))
            val target = mapper.map(source)

            assert(target.items["key1"]?.value == "value1") {
                "Expected key1 value 'value1' but was ${'$'}{target.items["key1"]?.value}"
            }
            assert(target.items["key2"]?.value == "value2") {
                "Expected key2 value 'value2' but was ${'$'}{target.items["key2"]?.value}"
            }
        }
    """)

    // TODO: Fix KSP adapter to handle nullable generic types
    @org.junit.jupiter.api.Disabled("KSP adapter error: 'Can't generate mapping method for a generic type variable source'")
    @Test
    fun shouldMapNullableGenericType() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val items: List<String>?)
        data class Target(val items: List<String>?)

        @Mapper
        interface NullableGenericMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = NullableGenericMapperImpl()

            // Test with non-null list
            val source1 = Source(listOf("a", "b"))
            val target1 = mapper.map(source1)
            assert(target1.items == listOf("a", "b")) {
                "Expected items [a, b] but was ${'$'}{target1.items}"
            }

            // Test with null list
            val source2 = Source(null)
            val target2 = mapper.map(source2)
            assert(target2.items == null) {
                "Expected items to be null but was ${'$'}{target2.items}"
            }
        }
    """)
}
