/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.ignore

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for ignoring properties with @Mapping(ignore = true) in KSP processor.
 */
class IgnorePropertyTest {

    @Test
    fun shouldIgnoreProperty() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(
            val name: String,
            val secret: String
        )

        data class Target(
            val name: String,
            val secret: String?  // Nullable since it will be ignored (null)
        )

        @Mapper
        interface IgnoreMapper {
            @Mapping(target = "secret", ignore = true)
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = IgnoreMapperImpl()
            val source = Source("Alice", "sensitive-data")
            val target = mapper.map(source)

            assert(target.name == "Alice") {
                "Expected name to be 'Alice' but was '${'$'}{target.name}'"
            }
            // The secret should be null since it's ignored
            assert(target.secret == null) {
                "Expected secret to be null (ignored) but was '${'$'}{target.secret}'"
            }
        }
    """)

    @Test
    fun shouldIgnoreMultipleProperties() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping
        import org.mapstruct.Mappings

        data class Source(val name: String, val password: String, val token: String, val age: Int)
        data class Target(val name: String, val password: String?, val token: String?, val age: Int)

        @Mapper
        interface MultiIgnoreMapper {
            @Mappings(
                Mapping(target = "password", ignore = true),
                Mapping(target = "token", ignore = true)
            )
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = MultiIgnoreMapperImpl()
            val source = Source("Alice", "secret123", "token-abc", 25)
            val target = mapper.map(source)

            assert(target.name == "Alice") { "Expected name 'Alice'" }
            assert(target.password == null) { "Expected password null (ignored)" }
            assert(target.token == null) { "Expected token null (ignored)" }
            assert(target.age == 25) { "Expected age 25" }
        }
    """)

    @Test
    fun shouldIgnorePropertyInAbstractClassMapper() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val name: String, val privateData: String)
        data class Target(val name: String, val privateData: String?)

        @Mapper
        abstract class AbstractIgnoreMapper {
            @Mapping(target = "privateData", ignore = true)
            abstract fun map(source: Source): Target
        }

        fun test() {
            val mapper = AbstractIgnoreMapperImpl()
            val source = Source("Alice", "sensitive")
            val target = mapper.map(source)

            assert(target.name == "Alice") { "Expected name 'Alice'" }
            assert(target.privateData == null) { "Expected privateData null (ignored)" }
        }
    """)

    @Test
    fun shouldIgnorePropertyWithDefaultTarget() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class Source(val name: String, val value: Int)

        class Target {
            var name: String = ""
            var value: Int = 999
        }

        @Mapper
        interface IgnoreWithDefaultMapper {
            @Mapping(target = "value", ignore = true)
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = IgnoreWithDefaultMapperImpl()
            val source = Source("Test", 42)
            val target = mapper.map(source)

            assert(target.name == "Test") { "Expected name 'Test'" }
            // value should remain at default since ignored
        }
    """)

    @Test
    fun shouldIgnoreAllButOne() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping
        import org.mapstruct.Mappings

        data class Source(val a: String, val b: String, val c: String, val d: String)
        data class Target(val a: String, val b: String?, val c: String?, val d: String?)

        @Mapper
        interface IgnoreAllButOneMapper {
            @Mappings(
                Mapping(target = "b", ignore = true),
                Mapping(target = "c", ignore = true),
                Mapping(target = "d", ignore = true)
            )
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = IgnoreAllButOneMapperImpl()
            val source = Source("keep", "ignore1", "ignore2", "ignore3")
            val target = mapper.map(source)

            assert(target.a == "keep") { "Expected a 'keep'" }
            assert(target.b == null) { "Expected b null" }
            assert(target.c == null) { "Expected c null" }
            assert(target.d == null) { "Expected d null" }
        }
    """)
}
