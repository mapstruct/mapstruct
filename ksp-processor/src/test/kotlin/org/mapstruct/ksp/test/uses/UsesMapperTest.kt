/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.uses

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for mapper 'uses' attribute with KSP processor.
 */
class UsesMapperTest {

    @Test
    fun shouldUseExternalMapperForNestedProperty() = pluginTest("""
        import org.mapstruct.Mapper

        data class InnerSource(val value: String)
        data class InnerTarget(val value: String)

        data class OuterSource(val inner: InnerSource)
        data class OuterTarget(val inner: InnerTarget)

        class InnerMapper {
            fun map(source: InnerSource): InnerTarget {
                return InnerTarget("mapped:" + source.value)
            }
        }

        @Mapper(uses = [InnerMapper::class])
        interface OuterMapper {
            fun map(source: OuterSource): OuterTarget
        }

        fun test() {
            val mapper = OuterMapperImpl()
            val source = OuterSource(InnerSource("test"))
            val target = mapper.map(source)

            assert(target.inner.value == "mapped:test") {
                "Expected inner value 'mapped:test' but was '${'$'}{target.inner.value}'"
            }
        }
    """)

    @Test
    fun shouldUseExternalTypeConverterClass() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val value: Int)
        data class Target(val value: String)

        class IntToStringConverter {
            fun convert(value: Int): String {
                return "Value: " + value.toString()
            }
        }

        @Mapper(uses = [IntToStringConverter::class])
        interface ConverterMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConverterMapperImpl()
            val source = Source(42)
            val target = mapper.map(source)

            assert(target.value == "Value: 42") {
                "Expected value 'Value: 42' but was '${'$'}{target.value}'"
            }
        }
    """)

    @Test
    fun shouldUseMultipleExternalMappers() = pluginTest("""
        import org.mapstruct.Mapper

        data class PersonSource(val name: String)
        data class PersonTarget(val name: String)

        data class AddressSource(val street: String)
        data class AddressTarget(val street: String)

        data class ContactSource(val person: PersonSource, val address: AddressSource)
        data class ContactTarget(val person: PersonTarget, val address: AddressTarget)

        class PersonMapper {
            fun mapPerson(source: PersonSource): PersonTarget {
                return PersonTarget("Person:" + source.name)
            }
        }

        class AddressMapper {
            fun mapAddress(source: AddressSource): AddressTarget {
                return AddressTarget("Address:" + source.street)
            }
        }

        @Mapper(uses = [PersonMapper::class, AddressMapper::class])
        interface ContactMapper {
            fun map(source: ContactSource): ContactTarget
        }

        fun test() {
            val mapper = ContactMapperImpl()
            val source = ContactSource(
                PersonSource("Alice"),
                AddressSource("Main St")
            )
            val target = mapper.map(source)

            assert(target.person.name == "Person:Alice") {
                "Expected person name 'Person:Alice' but was '${'$'}{target.person.name}'"
            }
            assert(target.address.street == "Address:Main St") {
                "Expected address street 'Address:Main St' but was '${'$'}{target.address.street}'"
            }
        }
    """)

    @Test
    fun shouldUseMapperInterfaceInUses() = pluginTest("""
        import org.mapstruct.Mapper

        data class ItemSource(val name: String)
        data class ItemTarget(val name: String)

        data class OrderSource(val item: ItemSource)
        data class OrderTarget(val item: ItemTarget)

        @Mapper
        interface ItemMapper {
            fun map(source: ItemSource): ItemTarget
        }

        @Mapper(uses = [ItemMapper::class])
        interface OrderMapper {
            fun map(source: OrderSource): OrderTarget
        }

        fun test() {
            val mapper = OrderMapperImpl()
            val source = OrderSource(ItemSource("Widget"))
            val target = mapper.map(source)

            assert(target.item.name == "Widget") {
                "Expected item name 'Widget' but was '${'$'}{target.item.name}'"
            }
        }
    """)

    @Test
    fun shouldUseDateFormatterClass() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val timestamp: Long)
        data class Target(val formatted: String)

        class TimestampFormatter {
            fun format(timestamp: Long): String {
                return "Time: " + timestamp.toString()
            }
        }

        @Mapper(uses = [TimestampFormatter::class])
        interface DateMapper {
            fun map(source: Source): Target {
                val formatter = TimestampFormatter()
                return Target(formatter.format(source.timestamp))
            }
        }

        fun test() {
            val mapper = DateMapperImpl()
            val source = Source(1234567890L)
            val target = mapper.map(source)

            assert(target.formatted == "Time: 1234567890") {
                "Expected 'Time: 1234567890' but was '${'$'}{target.formatted}'"
            }
        }
    """)
}
