/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.callbacks

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for lifecycle callback methods (@BeforeMapping, @AfterMapping) with KSP processor.
 *
 * NOTE: Callback tests may have issues with KSP adapter's handling of abstract classes.
 */
class LifecycleCallbackTest {

    // TODO: Investigate if @AfterMapping works with KSP - may need abstract class support
    @org.junit.jupiter.api.Disabled("KSP adapter may not support @AfterMapping callbacks correctly")
    @Test
    fun shouldInvokeAfterMapping() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.AfterMapping
        import org.mapstruct.MappingTarget

        data class Source(val name: String)

        class Target {
            var name: String = ""
            var processed: Boolean = false
        }

        @Mapper
        abstract class CallbackMapper {
            abstract fun map(source: Source): Target

            @AfterMapping
            fun afterMapping(@MappingTarget target: Target) {
                target.processed = true
            }
        }

        fun test() {
            val mapper = CallbackMapperImpl()
            val source = Source("Alice")
            val target = mapper.map(source)

            assert(target.name == "Alice") {
                "Expected name to be 'Alice' but was '${'$'}{target.name}'"
            }
            assert(target.processed) {
                "Expected processed to be true (set by @AfterMapping)"
            }
        }
    """)

    // TODO: Fix callback invocation in KSP - callbacks in interface default methods are not invoked
    @org.junit.jupiter.api.Disabled("KSP adapter does not invoke @AfterMapping in interface default methods")
    @Test
    fun shouldInvokeAfterMappingWithInterfaceDefaultMethod() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.AfterMapping
        import org.mapstruct.MappingTarget

        data class Source(val name: String)

        class Target {
            var name: String = ""
            var suffix: String = ""
        }

        @Mapper
        interface CallbackInterfaceMapper {
            fun map(source: Source): Target

            @AfterMapping
            fun afterMapping(@MappingTarget target: Target) {
                target.suffix = "_processed"
            }
        }

        fun test() {
            val mapper = CallbackInterfaceMapperImpl()
            val source = Source("test")
            val target = mapper.map(source)

            assert(target.name == "test") {
                "Expected name 'test' but was '${'$'}{target.name}'"
            }
            assert(target.suffix == "_processed") {
                "Expected suffix '_processed' but was '${'$'}{target.suffix}'"
            }
        }
    """)

    // TODO: Fix callback invocation in KSP - callbacks in interface default methods are not invoked
    @org.junit.jupiter.api.Disabled("KSP adapter does not invoke @BeforeMapping in interface default methods")
    @Test
    fun shouldInvokeBeforeMappingWithInterfaceDefaultMethod() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.BeforeMapping
        import org.mapstruct.MappingTarget

        data class Source(val value: Int)

        class Target {
            var value: Int = 0
            var initialValue: Int = -1
        }

        @Mapper
        interface BeforeCallbackMapper {
            fun map(source: Source): Target

            @BeforeMapping
            fun beforeMapping(@MappingTarget target: Target) {
                target.initialValue = 100
            }
        }

        fun test() {
            val mapper = BeforeCallbackMapperImpl()
            val source = Source(42)
            val target = mapper.map(source)

            assert(target.value == 42) {
                "Expected value 42 but was ${'$'}{target.value}"
            }
            assert(target.initialValue == 100) {
                "Expected initialValue 100 but was ${'$'}{target.initialValue}"
            }
        }
    """)

    // TODO: Fix callback invocation in KSP - callbacks in interface default methods are not invoked
    @org.junit.jupiter.api.Disabled("KSP adapter does not invoke lifecycle callbacks in interface default methods")
    @Test
    fun shouldInvokeBothBeforeAndAfterMapping() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.BeforeMapping
        import org.mapstruct.AfterMapping
        import org.mapstruct.MappingTarget

        data class Source(val name: String)

        class Target {
            var name: String = ""
            var log: String = ""
        }

        @Mapper
        interface FullLifecycleMapper {
            fun map(source: Source): Target

            @BeforeMapping
            fun before(@MappingTarget target: Target) {
                target.log = "before"
            }

            @AfterMapping
            fun after(@MappingTarget target: Target) {
                target.log = target.log + ":after"
            }
        }

        fun test() {
            val mapper = FullLifecycleMapperImpl()
            val source = Source("test")
            val target = mapper.map(source)

            assert(target.name == "test") {
                "Expected name 'test' but was '${'$'}{target.name}'"
            }
            assert(target.log == "before:after") {
                "Expected log 'before:after' but was '${'$'}{target.log}'"
            }
        }
    """)
}
