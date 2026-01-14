/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.abstractclass

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for abstract class mapper implementations with KSP processor.
 */
class AbstractClassMapperTest {

    @Test
    fun shouldGenerateAbstractClassMapperImpl() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val name: String, val age: Int)
        data class Target(val name: String, val age: Int)

        @Mapper
        abstract class AbstractMapper {
            abstract fun map(source: Source): Target
        }

        fun test() {
            val mapper = AbstractMapperImpl()
            val source = Source("John", 30)
            val target = mapper.map(source)

            assert(target.name == "John") {
                "Expected name 'John' but was '${'$'}{target.name}'"
            }
            assert(target.age == 30) {
                "Expected age 30 but was ${'$'}{target.age}"
            }
        }
    """)

    @Test
    fun shouldUseConcreteMethodInAbstractMapper() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val firstName: String, val lastName: String)
        data class Target(val fullName: String)

        @Mapper
        abstract class CustomLogicMapper {
            fun map(source: Source): Target {
                return Target(source.firstName + " " + source.lastName)
            }
        }

        fun test() {
            val mapper = CustomLogicMapperImpl()
            val source = Source("John", "Doe")
            val target = mapper.map(source)

            assert(target.fullName == "John Doe") {
                "Expected fullName 'John Doe' but was '${'$'}{target.fullName}'"
            }
        }
    """)

    @Test
    fun shouldMixAbstractAndConcreteMethodsInMapper() = pluginTest("""
        import org.mapstruct.Mapper

        data class PersonSource(val name: String, val age: Int)
        data class PersonTarget(val name: String, val age: Int)
        data class AddressSource(val street: String)
        data class AddressTarget(val street: String)

        @Mapper
        abstract class MixedMapper {
            abstract fun mapPerson(source: PersonSource): PersonTarget

            fun mapAddress(source: AddressSource): AddressTarget {
                return AddressTarget("Custom: " + source.street)
            }
        }

        fun test() {
            val mapper = MixedMapperImpl()

            // Test abstract method (generated implementation)
            val personSource = PersonSource("Alice", 25)
            val personTarget = mapper.mapPerson(personSource)
            assert(personTarget.name == "Alice") {
                "Expected name 'Alice' but was '${'$'}{personTarget.name}'"
            }

            // Test concrete method (custom implementation)
            val addressSource = AddressSource("Main St")
            val addressTarget = mapper.mapAddress(addressSource)
            assert(addressTarget.street == "Custom: Main St") {
                "Expected street 'Custom: Main St' but was '${'$'}{addressTarget.street}'"
            }
        }
    """)

    @Test
    fun shouldSupportAbstractMapperWithConstantMapping() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val value: String)
        data class Target(val value: String, val processed: Boolean)

        @Mapper
        abstract class ConstantMappingMapper {
            @Mapping(target = "processed", constant = "true")
            abstract fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConstantMappingMapperImpl()
            val source = Source("hello")
            val target = mapper.map(source)

            assert(target.value == "hello") {
                "Expected value 'hello' but was '${'$'}{target.value}'"
            }
            assert(target.processed == true) {
                "Expected processed true but was ${'$'}{target.processed}"
            }
        }
    """)

    @Test
    fun shouldSupportAbstractMapperWithTypeConversion() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val count: Int, val active: Boolean)
        data class Target(val count: String, val active: String)

        @Mapper
        abstract class TypeConversionMapper {
            abstract fun map(source: Source): Target
        }

        fun test() {
            val mapper = TypeConversionMapperImpl()
            val source = Source(42, true)
            val target = mapper.map(source)

            assert(target.count == "42") {
                "Expected count '42' but was '${'$'}{target.count}'"
            }
            assert(target.active == "true") {
                "Expected active 'true' but was '${'$'}{target.active}'"
            }
        }
    """)

    @Test
    fun shouldSupportAbstractMapperWithHelperMethod() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Named
        import org.mapstruct.Mapping

        data class Source(val value: String)
        data class Target(val value: String, val formatted: String)

        @Mapper
        abstract class HelperMethodMapper {
            @Mapping(target = "formatted", source = "value", qualifiedByName = ["format"])
            abstract fun map(source: Source): Target

            @Named("format")
            fun formatValue(input: String): String {
                return "FORMATTED_" + input
            }
        }

        fun test() {
            val mapper = HelperMethodMapperImpl()
            val source = Source("test")
            val target = mapper.map(source)

            assert(target.value == "test") {
                "Expected value 'test' but was '${'$'}{target.value}'"
            }
            assert(target.formatted == "FORMATTED_test") {
                "Expected formatted 'FORMATTED_test' but was '${'$'}{target.formatted}'"
            }
        }
    """)

    @Test
    fun shouldSupportAbstractMapperWithMultipleMappingMethods() = pluginTest("""
        import org.mapstruct.Mapper

        data class PersonSource(val name: String)
        data class PersonTarget(val name: String)
        data class ProductSource(val title: String)
        data class ProductTarget(val title: String)

        @Mapper
        abstract class MultiMethodMapper {
            abstract fun mapPerson(source: PersonSource): PersonTarget
            abstract fun mapProduct(source: ProductSource): ProductTarget
        }

        fun test() {
            val mapper = MultiMethodMapperImpl()

            val person = mapper.mapPerson(PersonSource("Alice"))
            assert(person.name == "Alice") { "Expected name 'Alice'" }

            val product = mapper.mapProduct(ProductSource("Widget"))
            assert(product.title == "Widget") { "Expected title 'Widget'" }
        }
    """)
}
