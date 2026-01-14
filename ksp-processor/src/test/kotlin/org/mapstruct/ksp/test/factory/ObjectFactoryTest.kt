/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.factory

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for object factory with KSP processor.
 */
class ObjectFactoryTest {

    @Test
    fun shouldUseObjectFactory() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.ObjectFactory

        data class Source(val name: String, val value: Int)

        class Target(val name: String, val value: Int, val createdBy: String)

        class TargetFactory {
            @ObjectFactory
            fun createTarget(source: Source): Target {
                return Target(source.name, source.value, "factory")
            }
        }

        @Mapper(uses = [TargetFactory::class])
        interface FactoryMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = FactoryMapperImpl()
            val source = Source("Test", 42)
            val target = mapper.map(source)

            assert(target.name == "Test") {
                "Expected name 'Test' but was '${'$'}{target.name}'"
            }
            assert(target.value == 42) {
                "Expected value 42 but was ${'$'}{target.value}"
            }
            assert(target.createdBy == "factory") {
                "Expected createdBy 'factory' but was '${'$'}{target.createdBy}'"
            }
        }
    """)

    @Test
    fun shouldUseObjectFactoryInAbstractMapper() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.ObjectFactory

        data class Source(val value: String)

        class Target(val value: String, val prefix: String)

        @Mapper
        abstract class AbstractFactoryMapper {
            abstract fun map(source: Source): Target

            @ObjectFactory
            fun createTarget(source: Source): Target {
                return Target(source.value, "PREFIX_")
            }
        }

        fun test() {
            val mapper = AbstractFactoryMapperImpl()
            val source = Source("test")
            val target = mapper.map(source)

            assert(target.value == "test") { "Expected value 'test'" }
            assert(target.prefix == "PREFIX_") { "Expected prefix 'PREFIX_'" }
        }
    """)

    @Test
    fun shouldUseObjectFactoryWithMultipleTargets() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.ObjectFactory

        data class Source(val name: String)

        class TargetA(val name: String, val type: String)
        class TargetB(val name: String, val type: String)

        @Mapper
        abstract class MultiTargetFactoryMapper {
            abstract fun toA(source: Source): TargetA
            abstract fun toB(source: Source): TargetB

            @ObjectFactory
            fun createTargetA(source: Source): TargetA {
                return TargetA(source.name, "TYPE_A")
            }

            @ObjectFactory
            fun createTargetB(source: Source): TargetB {
                return TargetB(source.name, "TYPE_B")
            }
        }

        fun test() {
            val mapper = MultiTargetFactoryMapperImpl()
            val source = Source("Test")

            val targetA = mapper.toA(source)
            assert(targetA.name == "Test") { "Expected name 'Test'" }
            assert(targetA.type == "TYPE_A") { "Expected type 'TYPE_A'" }

            val targetB = mapper.toB(source)
            assert(targetB.name == "Test") { "Expected name 'Test'" }
            assert(targetB.type == "TYPE_B") { "Expected type 'TYPE_B'" }
        }
    """)

    @Test
    fun shouldUseExternalObjectFactory() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.ObjectFactory

        data class Source(val value: Int)

        class Target(val value: Int, val origin: String)

        class ExternalFactory {
            @ObjectFactory
            fun create(source: Source): Target {
                return Target(source.value, "external")
            }
        }

        @Mapper(uses = [ExternalFactory::class])
        interface ExternalFactoryMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ExternalFactoryMapperImpl()
            val source = Source(100)
            val target = mapper.map(source)

            assert(target.value == 100) { "Expected value 100" }
            assert(target.origin == "external") { "Expected origin 'external'" }
        }
    """)
}
