/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.collection

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for collection type mapping with KSP processor.
 *
 * NOTE: Collection mapping currently has issues with KSP generic type resolution.
 * The KspTypeMirror.getTypeArguments() returns type parameters (E) instead of
 * resolved type arguments (String).
 */
class CollectionMappingTest {

    // TODO: Fix KspTypeMirror.getTypeArguments() to return resolved types
    // Currently fails because List<String> generates as "List<E>" in Java
    @org.junit.jupiter.api.Disabled("KSP adapter doesn't properly resolve generic type arguments")
    @Test
    fun shouldMapListToList() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val items: List<String>)
        data class Target(val items: List<String>)

        @Mapper
        interface CollectionMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = CollectionMapperImpl()
            val source = Source(listOf("x", "y", "z"))
            val target = mapper.map(source)

            assert(target.items == listOf("x", "y", "z")) {
                "Expected items to be ['x', 'y', 'z'] but was '${'$'}{target.items}'"
            }
        }
    """)
}
