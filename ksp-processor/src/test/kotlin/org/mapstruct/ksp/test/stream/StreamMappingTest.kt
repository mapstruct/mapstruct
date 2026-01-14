/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.stream

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for Stream mapping with KSP processor.
 *
 * NOTE: Stream mapping has issues with KSP generic type variable handling.
 */
class StreamMappingTest {

    // TODO: Fix KSP adapter to handle generic type variables for Stream mapping
    @org.junit.jupiter.api.Disabled("KSP adapter error: 'Can't generate mapping method for a generic type variable source'")
    @Test
    fun shouldMapStreamToList() = pluginTest("""
        import org.mapstruct.Mapper
        import java.util.stream.Stream

        data class Source(val name: String)
        data class Target(val name: String)

        @Mapper
        interface StreamToListMapper {
            fun map(source: Source): Target
            fun mapStream(stream: Stream<Source>): List<Target>
        }

        fun test() {
            val mapper = StreamToListMapperImpl()
            val stream = Stream.of(Source("A"), Source("B"), Source("C"))
            val list = mapper.mapStream(stream)

            assert(list.size == 3) {
                "Expected 3 items but got ${'$'}{list.size}"
            }
            assert(list[0].name == "A") {
                "Expected first item name 'A' but was '${'$'}{list[0].name}'"
            }
            assert(list[1].name == "B") {
                "Expected second item name 'B' but was '${'$'}{list[1].name}'"
            }
            assert(list[2].name == "C") {
                "Expected third item name 'C' but was '${'$'}{list[2].name}'"
            }
        }
    """)
}
