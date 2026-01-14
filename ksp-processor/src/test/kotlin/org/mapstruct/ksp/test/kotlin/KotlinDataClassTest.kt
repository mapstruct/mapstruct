/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.kotlin

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for Kotlin data class specific features with KSP processor.
 */
class KotlinDataClassTest {

    @Test
    fun shouldMapKotlinDataClass() = pluginTest("""
        import org.mapstruct.Mapper

        data class SourcePerson(
            val firstName: String,
            val lastName: String,
            val age: Int
        )

        data class TargetPerson(
            val firstName: String,
            val lastName: String,
            val age: Int
        )

        @Mapper
        interface PersonMapper {
            fun map(source: SourcePerson): TargetPerson
        }

        fun test() {
            val mapper = PersonMapperImpl()
            val source = SourcePerson("John", "Doe", 30)
            val target = mapper.map(source)

            assert(target.firstName == "John") {
                "Expected firstName to be 'John' but was '${'$'}{target.firstName}'"
            }
            assert(target.lastName == "Doe") {
                "Expected lastName to be 'Doe' but was '${'$'}{target.lastName}'"
            }
            assert(target.age == 30) {
                "Expected age to be 30 but was ${'$'}{target.age}"
            }
        }
    """)

    @Test
    fun shouldMapNullableDataClassProperties() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val name: String,
            val nickname: String?
        )

        data class Target(
            val name: String,
            val nickname: String?
        )

        @Mapper
        interface NullableMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = NullableMapperImpl()

            // Test with non-null nickname
            val source1 = Source("Alice", "Ali")
            val target1 = mapper.map(source1)
            assert(target1.name == "Alice") { "Name mismatch" }
            assert(target1.nickname == "Ali") { "Nickname mismatch" }

            // Test with null nickname
            val source2 = Source("Bob", null)
            val target2 = mapper.map(source2)
            assert(target2.name == "Bob") { "Name mismatch" }
            assert(target2.nickname == null) { "Expected null nickname" }
        }
    """)

    @Test
    fun shouldHandleNonNullableToNullable() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val name: String  // Non-nullable
        )

        data class Target(
            val name: String?  // Nullable
        )

        @Mapper
        interface NullableTargetMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = NullableTargetMapperImpl()
            val source = Source("Test")
            val target = mapper.map(source)

            assert(target.name == "Test") {
                "Expected name to be 'Test' but was '${'$'}{target.name}'"
            }
        }
    """)

    @Test
    fun shouldMapDataClassWithDefaultValues() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(
            val name: String
        )

        data class Target(
            val name: String,
            val status: String = "active"
        )

        @Mapper
        interface DefaultValueMapper {
            @Mapping(target = "status", constant = "pending")
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = DefaultValueMapperImpl()
            val source = Source("Item")
            val target = mapper.map(source)

            assert(target.name == "Item") {
                "Expected name 'Item' but was '${'$'}{target.name}'"
            }
            assert(target.status == "pending") {
                "Expected status 'pending' but was '${'$'}{target.status}'"
            }
        }
    """)

    @Test
    fun shouldMapDataClassWithValAndVar() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val immutableField: String,
            var mutableField: String
        )

        data class Target(
            val immutableField: String,
            var mutableField: String
        )

        @Mapper
        interface MixedAccessMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = MixedAccessMapperImpl()
            val source = Source("immutable", "mutable")
            val target = mapper.map(source)

            assert(target.immutableField == "immutable") {
                "Expected immutableField 'immutable' but was '${'$'}{target.immutableField}'"
            }
            assert(target.mutableField == "mutable") {
                "Expected mutableField 'mutable' but was '${'$'}{target.mutableField}'"
            }
        }
    """)

    @Test
    fun shouldMapDataClassWithPrivateSetter() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val value: String
        )

        class Target(
            val value: String
        )

        @Mapper
        interface PrivateSetterMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = PrivateSetterMapperImpl()
            val source = Source("test")
            val target = mapper.map(source)

            assert(target.value == "test") {
                "Expected value 'test' but was '${'$'}{target.value}'"
            }
        }
    """)

    @Test
    fun shouldMapDataClassWithCopy() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val id: Long, val name: String)
        data class Target(val id: Long, val name: String)

        @Mapper
        interface CopyMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = CopyMapperImpl()
            val source = Source(1L, "Original")
            val target = mapper.map(source)

            assert(target.id == 1L) { "Expected id 1L" }
            assert(target.name == "Original") { "Expected name 'Original'" }

            // Verify data class copy works independently
            val modified = source.copy(name = "Modified")
            assert(source.name == "Original") { "Original should be unchanged" }
            assert(modified.name == "Modified") { "Copy should have modified name" }
        }
    """)

    @Test
    fun shouldMapDataClassWithComponentFunctions() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val first: String, val second: Int, val third: Boolean)
        data class Target(val first: String, val second: Int, val third: Boolean)

        @Mapper
        interface ComponentMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ComponentMapperImpl()
            val source = Source("A", 42, true)
            val target = mapper.map(source)

            // Verify via destructuring (uses componentN functions)
            val (f, s, t) = target
            assert(f == "A") { "Expected first 'A'" }
            assert(s == 42) { "Expected second 42" }
            assert(t == true) { "Expected third true" }
        }
    """)

    @Test
    fun shouldMapDataClassWithManyFields() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val field1: String,
            val field2: Int,
            val field3: Boolean,
            val field4: Long,
            val field5: Double
        )

        data class Target(
            val field1: String,
            val field2: Int,
            val field3: Boolean,
            val field4: Long,
            val field5: Double
        )

        @Mapper
        interface ManyFieldsMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ManyFieldsMapperImpl()
            val source = Source("test", 1, true, 100L, 3.14)
            val target = mapper.map(source)

            assert(target.field1 == "test") { "Expected field1 'test'" }
            assert(target.field2 == 1) { "Expected field2 1" }
            assert(target.field3 == true) { "Expected field3 true" }
            assert(target.field4 == 100L) { "Expected field4 100L" }
            assert(target.field5 == 3.14) { "Expected field5 3.14" }
        }
    """)

    @Test
    fun shouldMapDataClassToRegularClass() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val name: String, val value: Int)

        class Target {
            var name: String = ""
            var value: Int = 0
        }

        @Mapper
        interface DataToRegularMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = DataToRegularMapperImpl()
            val source = Source("Hello", 42)
            val target = mapper.map(source)

            assert(target.name == "Hello") { "Expected name 'Hello'" }
            assert(target.value == 42) { "Expected value 42" }
        }
    """)

    @Test
    fun shouldMapRegularClassToDataClass() = pluginTest("""
        import org.mapstruct.Mapper

        class Source {
            var name: String = ""
            var value: Int = 0
        }

        data class Target(val name: String, val value: Int)

        @Mapper
        interface RegularToDataMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = RegularToDataMapperImpl()
            val source = Source()
            source.name = "World"
            source.value = 99

            val target = mapper.map(source)

            assert(target.name == "World") { "Expected name 'World'" }
            assert(target.value == 99) { "Expected value 99" }
        }
    """)
}
