/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.nestedbeans

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for nested bean mapping with KSP processor.
 */
class NestedBeanMappingTest {

    @Test
    fun shouldMapNestedBeans() = pluginTest("""
        import org.mapstruct.Mapper

        data class Address(
            val street: String,
            val city: String
        )

        data class AddressDto(
            val street: String,
            val city: String
        )

        data class Person(
            val name: String,
            val address: Address
        )

        data class PersonDto(
            val name: String,
            val address: AddressDto
        )

        @Mapper
        interface PersonMapper {
            fun map(person: Person): PersonDto
        }

        fun test() {
            val mapper = PersonMapperImpl()
            val address = Address("123 Main St", "Boston")
            val person = Person("John", address)
            val personDto = mapper.map(person)

            assert(personDto.name == "John") {
                "Expected name to be 'John' but was '${'$'}{personDto.name}'"
            }
            assert(personDto.address.street == "123 Main St") {
                "Expected street to be '123 Main St' but was '${'$'}{personDto.address.street}'"
            }
            assert(personDto.address.city == "Boston") {
                "Expected city to be 'Boston' but was '${'$'}{personDto.address.city}'"
            }
        }
    """)

    @Test
    fun shouldMapDeeplyNestedBeans() = pluginTest("""
        import org.mapstruct.Mapper

        data class Country(val name: String, val code: String)
        data class CountryDto(val name: String, val code: String)

        data class City(val name: String, val country: Country)
        data class CityDto(val name: String, val country: CountryDto)

        data class Address(val street: String, val city: City)
        data class AddressDto(val street: String, val city: CityDto)

        data class Company(val name: String, val address: Address)
        data class CompanyDto(val name: String, val address: AddressDto)

        @Mapper
        interface DeepNestedMapper {
            fun map(company: Company): CompanyDto
        }

        fun test() {
            val mapper = DeepNestedMapperImpl()
            val country = Country("United States", "US")
            val city = City("Boston", country)
            val address = Address("123 Main St", city)
            val company = Company("Acme Corp", address)

            val dto = mapper.map(company)

            assert(dto.name == "Acme Corp") {
                "Expected company name 'Acme Corp' but was '${'$'}{dto.name}'"
            }
            assert(dto.address.street == "123 Main St") {
                "Expected street '123 Main St' but was '${'$'}{dto.address.street}'"
            }
            assert(dto.address.city.name == "Boston") {
                "Expected city 'Boston' but was '${'$'}{dto.address.city.name}'"
            }
            assert(dto.address.city.country.name == "United States") {
                "Expected country 'United States' but was '${'$'}{dto.address.city.country.name}'"
            }
            assert(dto.address.city.country.code == "US") {
                "Expected country code 'US' but was '${'$'}{dto.address.city.country.code}'"
            }
        }
    """)

    @Test
    fun shouldMapNestedNullableProperty() = pluginTest("""
        import org.mapstruct.Mapper

        data class Inner(val value: String)
        data class InnerDto(val value: String)

        data class Outer(val inner: Inner?)
        data class OuterDto(val inner: InnerDto?)

        @Mapper
        interface NullableNestedMapper {
            fun map(outer: Outer): OuterDto
        }

        fun test() {
            val mapper = NullableNestedMapperImpl()

            // Test with null nested
            val outerNull = Outer(null)
            val dtoNull = mapper.map(outerNull)
            assert(dtoNull.inner == null) {
                "Expected null inner but was ${'$'}{dtoNull.inner}"
            }

            // Test with non-null nested
            val outerWithValue = Outer(Inner("test"))
            val dtoWithValue = mapper.map(outerWithValue)
            assert(dtoWithValue.inner != null && dtoWithValue.inner!!.value == "test") {
                "Expected inner.value 'test' but was ${'$'}{dtoWithValue.inner?.value}"
            }
        }
    """)

    @Test
    fun shouldMapRecursiveStructure() = pluginTest("""
        import org.mapstruct.Mapper

        data class TreeNode(
            val value: String,
            val left: TreeNode?,
            val right: TreeNode?
        )

        data class TreeNodeDto(
            val value: String,
            val left: TreeNodeDto?,
            val right: TreeNodeDto?
        )

        @Mapper
        interface TreeMapper {
            fun map(node: TreeNode): TreeNodeDto
        }

        fun test() {
            val mapper = TreeMapperImpl()

            // Build a simple tree:
            //       root
            //      /    \
            //    left   right
            val tree = TreeNode(
                "root",
                TreeNode("left", null, null),
                TreeNode("right", null, null)
            )

            val dto = mapper.map(tree)

            assert(dto.value == "root") {
                "Expected root value 'root' but was '${'$'}{dto.value}'"
            }
            assert(dto.left != null && dto.left!!.value == "left") {
                "Expected left value 'left' but was '${'$'}{dto.left?.value}'"
            }
            assert(dto.right != null && dto.right!!.value == "right") {
                "Expected right value 'right' but was '${'$'}{dto.right?.value}'"
            }
            assert(dto.left!!.left == null) {
                "Expected left.left to be null but was not"
            }
        }
    """)

    @Test
    fun shouldMapNestedWithTypeConversion() = pluginTest("""
        import org.mapstruct.Mapper

        data class InnerSource(val value: Int)
        data class InnerTarget(val value: String)

        data class OuterSource(val inner: InnerSource)
        data class OuterTarget(val inner: InnerTarget)

        @Mapper
        interface NestedConversionMapper {
            fun map(source: OuterSource): OuterTarget
        }

        fun test() {
            val mapper = NestedConversionMapperImpl()
            val source = OuterSource(InnerSource(42))
            val target = mapper.map(source)

            assert(target.inner.value == "42") {
                "Expected inner.value '42' but was '${'$'}{target.inner.value}'"
            }
        }
    """)

    @Test
    fun shouldMapMultipleNestedObjects() = pluginTest("""
        import org.mapstruct.Mapper

        data class PartA(val name: String)
        data class PartADto(val name: String)

        data class PartB(val code: Int)
        data class PartBDto(val code: Int)

        data class Container(val partA: PartA, val partB: PartB)
        data class ContainerDto(val partA: PartADto, val partB: PartBDto)

        @Mapper
        interface MultiNestedMapper {
            fun map(source: Container): ContainerDto
        }

        fun test() {
            val mapper = MultiNestedMapperImpl()
            val source = Container(PartA("Test"), PartB(123))
            val target = mapper.map(source)

            assert(target.partA.name == "Test") { "Expected partA.name 'Test'" }
            assert(target.partB.code == 123) { "Expected partB.code 123" }
        }
    """)

    @Test
    fun shouldMapNestedWithSameName() = pluginTest("""
        import org.mapstruct.Mapper

        data class Config(val value: String)
        data class ConfigDto(val value: String)

        data class Settings(val config: Config)
        data class SettingsDto(val config: ConfigDto)

        @Mapper
        interface NestedSameNameMapper {
            fun map(source: Settings): SettingsDto
        }

        fun test() {
            val mapper = NestedSameNameMapperImpl()
            val source = Settings(Config("enabled"))
            val target = mapper.map(source)

            assert(target.config.value == "enabled") { "Expected config.value 'enabled'" }
        }
    """)

    @Test
    fun shouldMapNestedListNullable() = pluginTest("""
        import org.mapstruct.Mapper

        data class Item(val id: Int)
        data class ItemDto(val id: Int)

        data class Wrapper(val item: Item?)
        data class WrapperDto(val item: ItemDto?)

        @Mapper
        interface NullableNestedItemMapper {
            fun map(source: Wrapper): WrapperDto
        }

        fun test() {
            val mapper = NullableNestedItemMapperImpl()

            val withItem = Wrapper(Item(1))
            val resultItem = mapper.map(withItem)
            assert(resultItem.item?.id == 1) { "Expected item.id 1" }

            val withNull = Wrapper(null)
            val resultNull = mapper.map(withNull)
            assert(resultNull.item == null) { "Expected null item" }
        }
    """)
}
