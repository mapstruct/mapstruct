/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.builder

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for builder pattern mapping with KSP processor.
 *
 * NOTE: Builder pattern support has issues with KSP adapter constructor detection.
 */
class BuilderMappingTest {

    // TODO: Fix KSP adapter to recognize builder pattern for target construction
    @org.junit.jupiter.api.Disabled("KSP adapter error: 'Target does not have an accessible constructor'")
    @Test
    fun shouldMapUsingSimpleBuilder() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val name: String, val age: Int)

        class Target private constructor(val name: String, val age: Int) {
            class Builder {
                private var name: String = ""
                private var age: Int = 0

                fun name(name: String): Builder {
                    this.name = name
                    return this
                }

                fun age(age: Int): Builder {
                    this.age = age
                    return this
                }

                fun build(): Target {
                    return Target(name, age)
                }
            }

            companion object {
                @JvmStatic
                fun builder(): Builder = Builder()
            }
        }

        @Mapper
        interface BuilderMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = BuilderMapperImpl()
            val source = Source("Alice", 30)
            val target = mapper.map(source)

            assert(target.name == "Alice") {
                "Expected name to be 'Alice' but was '${'$'}{target.name}'"
            }
            assert(target.age == 30) {
                "Expected age to be 30 but was ${'$'}{target.age}"
            }
        }
    """)
}
