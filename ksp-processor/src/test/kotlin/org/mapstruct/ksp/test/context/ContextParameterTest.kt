/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.context

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for @Context parameter passing with KSP processor.
 */
class ContextParameterTest {

    @Test
    fun shouldPassContextToMappingMethod() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Context

        data class Source(val name: String)
        data class Target(val name: String, val locale: String)

        class MappingContext(val locale: String)

        @Mapper
        abstract class ContextMapper {
            fun map(source: Source, @Context ctx: MappingContext): Target {
                return Target(source.name, ctx.locale)
            }
        }

        fun test() {
            val mapper = object : ContextMapper() {}
            val ctx = MappingContext("en_US")
            val source = Source("Hello")
            val target = mapper.map(source, ctx)

            assert(target.name == "Hello") {
                "Expected name 'Hello' but was '${'$'}{target.name}'"
            }
            assert(target.locale == "en_US") {
                "Expected locale 'en_US' but was '${'$'}{target.locale}'"
            }
        }
    """)

    @Test
    fun shouldPassContextInInterfaceMapper() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Context
        import org.mapstruct.Mapping

        data class Source(val value: Int)
        data class Target(val value: Int, val multiplied: Int)

        class MultiplierContext(val factor: Int)

        @Mapper
        interface ContextInterfaceMapper {
            @Mapping(target = "multiplied", expression = "java(source.getValue() * ctx.getFactor())")
            fun map(source: Source, @Context ctx: MultiplierContext): Target
        }

        fun test() {
            val mapper = ContextInterfaceMapperImpl()
            val source = Source(10)
            val ctx = MultiplierContext(5)
            val target = mapper.map(source, ctx)

            assert(target.value == 10) { "Expected value 10" }
            assert(target.multiplied == 50) { "Expected multiplied 50" }
        }
    """)

    @Test
    fun shouldPassMultipleContextParameters() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Context
        import org.mapstruct.Mapping

        data class Source(val text: String)
        data class Target(val text: String, val formatted: String)

        class PrefixContext(val prefix: String)
        class SuffixContext(val suffix: String)

        @Mapper
        interface MultiContextMapper {
            @Mapping(target = "formatted", expression = "java(prefixCtx.getPrefix() + source.getText() + suffixCtx.getSuffix())")
            fun map(source: Source, @Context prefixCtx: PrefixContext, @Context suffixCtx: SuffixContext): Target
        }

        fun test() {
            val mapper = MultiContextMapperImpl()
            val source = Source("Hello")
            val target = mapper.map(source, PrefixContext("["), SuffixContext("]"))

            assert(target.text == "Hello") { "Expected text 'Hello'" }
            assert(target.formatted == "[Hello]") {
                "Expected formatted '[Hello]' but was '${'$'}{target.formatted}'"
            }
        }
    """)

    @Test
    fun shouldUseContextInNestedMapping() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Context
        import org.mapstruct.Mapping

        data class InnerSource(val value: Int)
        data class InnerTarget(val value: Int, val label: String)

        data class OuterSource(val inner: InnerSource)
        data class OuterTarget(val inner: InnerTarget)

        class LabelContext(val label: String)

        @Mapper
        interface NestedContextMapper {
            @Mapping(target = "inner.label", expression = "java(ctx.getLabel())")
            fun map(source: OuterSource, @Context ctx: LabelContext): OuterTarget
        }

        fun test() {
            val mapper = NestedContextMapperImpl()
            val source = OuterSource(InnerSource(42))
            val ctx = LabelContext("TEST")
            val target = mapper.map(source, ctx)

            assert(target.inner.value == 42) { "Expected inner.value 42" }
            assert(target.inner.label == "TEST") { "Expected inner.label 'TEST'" }
        }
    """)
}
