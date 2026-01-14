/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.defaultmethod

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for default method support in Kotlin interface mappers with KSP processor.
 */
class DefaultMethodTest {

    @Test
    fun shouldUseDefaultMethodImplementation() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val firstName: String, val lastName: String)
        data class Target(val fullName: String)

        @Mapper
        interface DefaultMethodMapper {
            fun map(source: Source): Target {
                return Target(source.firstName + " " + source.lastName)
            }
        }

        fun test() {
            val mapper = DefaultMethodMapperImpl()
            val source = Source("John", "Doe")
            val target = mapper.map(source)

            assert(target.fullName == "John Doe") {
                "Expected fullName 'John Doe' but was '${'$'}{target.fullName}'"
            }
        }
    """)

    @Test
    fun shouldMixDefaultAndAbstractMethods() = pluginTest("""
        import org.mapstruct.Mapper

        data class UserSource(val name: String, val email: String)
        data class UserTarget(val name: String, val email: String)
        data class ProfileSource(val displayName: String)
        data class ProfileTarget(val displayName: String)

        @Mapper
        interface MixedMethodMapper {
            // Abstract method - will be generated
            fun mapUser(source: UserSource): UserTarget

            // Default method - custom implementation
            fun mapProfile(source: ProfileSource): ProfileTarget {
                return ProfileTarget("Profile: " + source.displayName)
            }
        }

        fun test() {
            val mapper = MixedMethodMapperImpl()

            // Test abstract method (generated)
            val userSource = UserSource("Alice", "alice@test.com")
            val userTarget = mapper.mapUser(userSource)
            assert(userTarget.name == "Alice") {
                "Expected name 'Alice' but was '${'$'}{userTarget.name}'"
            }
            assert(userTarget.email == "alice@test.com") {
                "Expected email 'alice@test.com' but was '${'$'}{userTarget.email}'"
            }

            // Test default method (custom)
            val profileSource = ProfileSource("Bob")
            val profileTarget = mapper.mapProfile(profileSource)
            assert(profileTarget.displayName == "Profile: Bob") {
                "Expected displayName 'Profile: Bob' but was '${'$'}{profileTarget.displayName}'"
            }
        }
    """)

    @Test
    fun shouldCallDefaultHelperMethodFromAbstract() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping
        import org.mapstruct.Named

        data class Source(val value: String)
        data class Target(val value: String)

        @Mapper
        interface HelperMethodMapper {
            @Mapping(target = "value", source = "value", qualifiedByName = ["toUpperCase"])
            fun map(source: Source): Target

            @Named("toUpperCase")
            fun convertToUpperCase(value: String): String {
                return value.uppercase()
            }
        }

        fun test() {
            val mapper = HelperMethodMapperImpl()
            val source = Source("hello")
            val target = mapper.map(source)

            assert(target.value == "HELLO") {
                "Expected value 'HELLO' but was '${'$'}{target.value}'"
            }
        }
    """)

    @Test
    fun shouldSupportDefaultMethodWithMultipleParameters() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source1(val a: String)
        data class Source2(val b: String)
        data class Target(val combined: String)

        @Mapper
        interface MultiParamDefaultMapper {
            fun combine(s1: Source1, s2: Source2): Target {
                return Target(s1.a + "-" + s2.b)
            }
        }

        fun test() {
            val mapper = MultiParamDefaultMapperImpl()
            val target = mapper.combine(Source1("foo"), Source2("bar"))

            assert(target.combined == "foo-bar") {
                "Expected combined 'foo-bar' but was '${'$'}{target.combined}'"
            }
        }
    """)

    @Test
    fun shouldUseDefaultMethodForTypeConversion() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: String)
        data class Target(val value: Int)

        @Mapper
        interface ConversionDefaultMapper {
            fun map(source: Source): Target {
                return Target(source.value.toIntOrNull() ?: 0)
            }
        }

        fun test() {
            val mapper = ConversionDefaultMapperImpl()

            val validSource = Source("42")
            val validTarget = mapper.map(validSource)
            assert(validTarget.value == 42) { "Expected value 42" }

            val invalidSource = Source("not-a-number")
            val invalidTarget = mapper.map(invalidSource)
            assert(invalidTarget.value == 0) { "Expected value 0 for invalid input" }
        }
    """)

    @Test
    fun shouldUseDefaultMethodReturningNull() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: String)
        data class Target(val value: String?)

        @Mapper
        interface NullableDefaultMapper {
            fun map(source: Source): Target {
                return if (source.value.isEmpty()) Target(null) else Target(source.value)
            }
        }

        fun test() {
            val mapper = NullableDefaultMapperImpl()

            val nonEmpty = Source("test")
            val nonEmptyTarget = mapper.map(nonEmpty)
            assert(nonEmptyTarget.value == "test") { "Expected value 'test'" }

            val empty = Source("")
            val emptyTarget = mapper.map(empty)
            assert(emptyTarget.value == null) { "Expected value null for empty input" }
        }
    """)

    @Test
    fun shouldUseDefaultMethodWithListProcessing() = pluginTest("""
        import org.mapstruct.Mapper

        data class Item(val name: String)
        data class ItemDto(val name: String)

        @Mapper
        interface ListDefaultMapper {
            fun mapItem(item: Item): ItemDto {
                return ItemDto("Mapped: " + item.name)
            }

            fun mapItems(items: List<Item>): List<ItemDto> {
                return items.map { mapItem(it) }
            }
        }

        fun test() {
            val mapper = ListDefaultMapperImpl()
            val items = listOf(Item("A"), Item("B"), Item("C"))
            val dtos = mapper.mapItems(items)

            assert(dtos.size == 3) { "Expected 3 items" }
            assert(dtos[0].name == "Mapped: A") { "Expected 'Mapped: A'" }
            assert(dtos[1].name == "Mapped: B") { "Expected 'Mapped: B'" }
            assert(dtos[2].name == "Mapped: C") { "Expected 'Mapped: C'" }
        }
    """)
}
