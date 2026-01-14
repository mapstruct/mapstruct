/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.nestedproperties

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for nested property mapping (source = "nested.property") with KSP processor.
 *
 * NOTE: Nested property mapping has issues with duplicate target detection.
 */
class NestedPropertyMappingTest {

    // TODO: Fix duplicate target property detection for nested property sources
    @org.junit.jupiter.api.Disabled("KSP adapter reports false 'duplicate target mapping' for nested properties")
    @Test
    fun shouldMapNestedPropertyToFlat() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Address(
            val street: String,
            val city: String
        )

        data class Source(
            val name: String,
            val address: Address
        )

        data class Target(
            val name: String,
            val street: String,
            val city: String
        )

        @Mapper
        interface NestedPropertyMapper {
            @Mapping(source = "address.street", target = "street")
            @Mapping(source = "address.city", target = "city")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = NestedPropertyMapperImpl()
            val address = Address("123 Main St", "Boston")
            val source = Source("John", address)
            val target = mapper.map(source)

            assert(target.name == "John") {
                "Expected name to be 'John' but was '${'$'}{target.name}'"
            }
            assert(target.street == "123 Main St") {
                "Expected street to be '123 Main St' but was '${'$'}{target.street}'"
            }
            assert(target.city == "Boston") {
                "Expected city to be 'Boston' but was '${'$'}{target.city}'"
            }
        }
    """)
}
