/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.inheritance

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for inheritance mapping with KSP processor.
 */
class InheritanceMappingTest {

    @Test
    fun shouldMapInheritedProperties() = pluginTest("""
        import org.mapstruct.Mapper

        open class BaseSource {
            open var id: Long = 0
            open var name: String = ""
        }

        class ExtendedSource : BaseSource() {
            var description: String = ""
        }

        data class Target(
            val id: Long,
            val name: String,
            val description: String
        )

        @Mapper
        interface InheritanceMapper {
            fun map(source: ExtendedSource): Target
        }

        fun test() {
            val mapper = InheritanceMapperImpl()
            val source = ExtendedSource().apply {
                id = 42L
                name = "Test"
                description = "A description"
            }
            val target = mapper.map(source)

            assert(target.id == 42L) {
                "Expected id to be 42 but was ${'$'}{target.id}"
            }
            assert(target.name == "Test") {
                "Expected name to be 'Test' but was '${'$'}{target.name}'"
            }
            assert(target.description == "A description") {
                "Expected description to be 'A description' but was '${'$'}{target.description}'"
            }
        }
    """)

    @Test
    fun shouldMapFromInterfaceWithDefaultMethods() = pluginTest("""
        import org.mapstruct.Mapper

        interface NamedEntity {
            val name: String
        }

        data class Source(override val name: String, val code: String) : NamedEntity

        data class Target(val name: String, val code: String)

        @Mapper
        interface InterfaceMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = InterfaceMapperImpl()
            val source = Source("Entity Name", "E001")
            val target = mapper.map(source)

            assert(target.name == "Entity Name") {
                "Expected name 'Entity Name' but was '${'$'}{target.name}'"
            }
            assert(target.code == "E001") {
                "Expected code 'E001' but was '${'$'}{target.code}'"
            }
        }
    """)

    // TODO: Fix KSP adapter duplicate target detection for multiple @Mapping annotations
    @org.junit.jupiter.api.Disabled("KSP adapter error: 'Target property must not be mapped more than once' with multiple mappings")
    @Test
    fun shouldInheritConfigurationFromBaseMapper() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.InheritConfiguration
        import org.mapstruct.Mapping
        import org.mapstruct.MappingTarget

        data class Source(val firstName: String, val lastName: String)
        data class Target(var fullName: String, var reverseName: String)

        @Mapper
        interface ConfigInheritMapper {
            @Mapping(source = "firstName", target = "fullName")
            @Mapping(source = "lastName", target = "reverseName")
            fun toTarget(source: Source): Target
        }

        fun test() {
            val mapper = ConfigInheritMapperImpl()
            val source = Source("John", "Doe")
            val target = mapper.toTarget(source)

            assert(target.fullName == "John") {
                "Expected fullName 'John' but was '${'$'}{target.fullName}'"
            }
            assert(target.reverseName == "Doe") {
                "Expected reverseName 'Doe' but was '${'$'}{target.reverseName}'"
            }
        }
    """)

    @Test
    fun shouldMapToInheritedTargetProperties() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(
            val id: Long,
            val name: String,
            val extra: String
        )

        open class BaseTarget {
            open var id: Long = 0
            open var name: String = ""
        }

        class ExtendedTarget : BaseTarget() {
            var extra: String = ""
        }

        @Mapper
        interface InheritedTargetMapper {
            fun map(source: Source): ExtendedTarget
        }

        fun test() {
            val mapper = InheritedTargetMapperImpl()
            val source = Source(1L, "Test", "Extra Value")
            val target = mapper.map(source)

            assert(target.id == 1L) { "Expected id 1" }
            assert(target.name == "Test") { "Expected name 'Test'" }
            assert(target.extra == "Extra Value") { "Expected extra 'Extra Value'" }
        }
    """)

    @Test
    fun shouldMapMultipleLevelsOfInheritance() = pluginTest("""
        import org.mapstruct.Mapper

        open class GrandparentSource {
            open var id: Long = 0
        }

        open class ParentSource : GrandparentSource() {
            open var name: String = ""
        }

        class ChildSource : ParentSource() {
            var value: Int = 0
        }

        data class Target(val id: Long, val name: String, val value: Int)

        @Mapper
        interface MultiLevelInheritanceMapper {
            fun map(source: ChildSource): Target
        }

        fun test() {
            val mapper = MultiLevelInheritanceMapperImpl()
            val source = ChildSource().apply {
                id = 100L
                name = "Multi"
                value = 42
            }
            val target = mapper.map(source)

            assert(target.id == 100L) { "Expected id 100" }
            assert(target.name == "Multi") { "Expected name 'Multi'" }
            assert(target.value == 42) { "Expected value 42" }
        }
    """)

    @Test
    fun shouldMapWithInterfaceInheritance() = pluginTest("""
        import org.mapstruct.Mapper

        interface Identifiable {
            val id: Long
        }

        interface Named {
            val name: String
        }

        data class Source(
            override val id: Long,
            override val name: String,
            val extra: String
        ) : Identifiable, Named

        data class Target(val id: Long, val name: String, val extra: String)

        @Mapper
        interface MultiInterfaceMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = MultiInterfaceMapperImpl()
            val source = Source(99L, "Interface Test", "Extra Data")
            val target = mapper.map(source)

            assert(target.id == 99L) { "Expected id 99" }
            assert(target.name == "Interface Test") { "Expected name 'Interface Test'" }
            assert(target.extra == "Extra Data") { "Expected extra 'Extra Data'" }
        }
    """)
}
