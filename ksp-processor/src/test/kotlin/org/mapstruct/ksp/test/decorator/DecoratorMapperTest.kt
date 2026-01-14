/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.decorator

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for decorator pattern with KSP processor.
 */
class DecoratorMapperTest {

    @Test
    fun shouldDecorateSimpleMapperMethod() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.DecoratedWith

        data class Source(val name: String)
        data class Target(val name: String)

        @Mapper
        @DecoratedWith(SimpleMapperDecorator::class)
        interface SimpleMapper {
            fun map(source: Source): Target
        }

        abstract class SimpleMapperDecorator : SimpleMapper {
            private val delegate: SimpleMapper = SimpleMapperImpl_()

            override fun map(source: Source): Target {
                val result = delegate.map(source)
                return Target("Decorated: " + result.name)
            }
        }

        fun test() {
            val mapper = SimpleMapperImpl()
            val source = Source("Test")
            val target = mapper.map(source)

            assert(target.name == "Decorated: Test") {
                "Expected name 'Decorated: Test' but was '${'$'}{target.name}'"
            }
        }
    """)

    @Test
    fun shouldDelegateNonDecoratedMethods() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.DecoratedWith

        data class PersonSource(val name: String)
        data class PersonTarget(val name: String)
        data class AddressSource(val street: String)
        data class AddressTarget(val street: String)

        @Mapper
        @DecoratedWith(MixedMapperDecorator::class)
        interface MixedMapper {
            fun mapPerson(source: PersonSource): PersonTarget
            fun mapAddress(source: AddressSource): AddressTarget
        }

        abstract class MixedMapperDecorator : MixedMapper {
            private val delegate: MixedMapper = MixedMapperImpl_()

            // Only decorate person mapping
            override fun mapPerson(source: PersonSource): PersonTarget {
                val result = delegate.mapPerson(source)
                return PersonTarget("Decorated: " + result.name)
            }

            // Address mapping delegates to default
            override fun mapAddress(source: AddressSource): AddressTarget {
                return delegate.mapAddress(source)
            }
        }

        fun test() {
            val mapper = MixedMapperImpl()

            // Decorated method
            val personSource = PersonSource("Alice")
            val personTarget = mapper.mapPerson(personSource)
            assert(personTarget.name == "Decorated: Alice") {
                "Expected name 'Decorated: Alice' but was '${'$'}{personTarget.name}'"
            }

            // Non-decorated method
            val addressSource = AddressSource("Main St")
            val addressTarget = mapper.mapAddress(addressSource)
            assert(addressTarget.street == "Main St") {
                "Expected street 'Main St' but was '${'$'}{addressTarget.street}'"
            }
        }
    """)
}
