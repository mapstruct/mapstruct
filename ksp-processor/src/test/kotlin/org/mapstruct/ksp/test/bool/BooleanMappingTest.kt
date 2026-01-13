/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.bool

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

class BooleanMappingTest {

    @Test
    fun shouldMapBooleanPropertyWithIsPrefixedGetter() = pluginTest("""
        import org.mapstruct.Mapper

        data class Person(
            var married: Boolean? = null,
            var engaged: Boolean? = null,
            var divorced: YesNo? = null,
            var widowed: YesNo? = null
        ) {
            fun isMarried(): Boolean? = married

            // START: please note: deliberately ordered, first getEngaged, then isEngaged.
            fun getEngaged(): Boolean? = engaged

            fun isEngaged(): Boolean = engaged?.let { !it } ?: false
            // END
        }

        data class PersonDto(
            var married: String? = null,
            var engaged: String? = null,
            var divorced: String? = null,
            var widowed: Boolean? = null
        )

        data class YesNo(val yes: Boolean)

        object YesNoMapper {
            fun toString(yesNo: YesNo?): String =
                if (yesNo?.yes == true) "yes" else "no"

            fun toBool(yesNo: YesNo?): Boolean =
                yesNo?.yes == true
        }

        @Mapper(uses = [YesNoMapper::class])
        interface PersonMapper {
            fun personToDto(person: Person): PersonDto
        }

        fun test() {
            val person = Person(married = true)
            val personDto = PersonMapperImpl().personToDto(person)

            assert(personDto.married == "true") {
                "Expected married to be 'true' but was '${'$'}{personDto.married}'"
            }
        }
    """)

    @Test
    fun shouldMapBooleanPropertyPreferringGetPrefixedGetterOverIsPrefixedGetter() = pluginTest("""
        import org.mapstruct.Mapper

        data class Person(
            var married: Boolean? = null,
            var engaged: Boolean? = null,
            var divorced: YesNo? = null,
            var widowed: YesNo? = null
        ) {
            fun isMarried(): Boolean? = married

            fun getEngaged(): Boolean? = engaged

            fun isEngaged(): Boolean = engaged?.let { !it } ?: false
        }

        data class PersonDto(
            var married: String? = null,
            var engaged: String? = null,
            var divorced: String? = null,
            var widowed: Boolean? = null
        )

        data class YesNo(val yes: Boolean)

        object YesNoMapper {
            fun toString(yesNo: YesNo?): String =
                if (yesNo?.yes == true) "yes" else "no"

            fun toBool(yesNo: YesNo?): Boolean =
                yesNo?.yes == true
        }

        @Mapper(uses = [YesNoMapper::class])
        interface PersonMapper {
            fun personToDto(person: Person): PersonDto
        }

        fun test() {
            val person = Person(engaged = true)
            val personDto = PersonMapperImpl().personToDto(person)

            assert(personDto.engaged == "true") {
                "Expected engaged to be 'true' but was '${'$'}{personDto.engaged}'"
            }
        }
    """)

    @Test
    fun shouldMapBooleanPropertyWithPropertyMappingMethod() = pluginTest("""
        import org.mapstruct.Mapper

        data class Person(
            var married: Boolean? = null,
            var engaged: Boolean? = null,
            var divorced: YesNo? = null,
            var widowed: YesNo? = null
        ) {
            fun isMarried(): Boolean? = married

            fun getEngaged(): Boolean? = engaged

            fun isEngaged(): Boolean = engaged?.let { !it } ?: false
        }

        data class PersonDto(
            var married: String? = null,
            var engaged: String? = null,
            var divorced: String? = null,
            var widowed: Boolean? = null
        )

        data class YesNo(val yes: Boolean)

        object YesNoMapper {
            fun toString(yesNo: YesNo?): String =
                if (yesNo?.yes == true) "yes" else "no"

            fun toBool(yesNo: YesNo?): Boolean =
                yesNo?.yes == true
        }

        @Mapper(uses = [YesNoMapper::class])
        interface PersonMapper {
            fun personToDto(person: Person): PersonDto
        }

        fun test() {
            val person = Person(
                divorced = YesNo(true),
                widowed = YesNo(true)
            )
            val personDto = PersonMapperImpl().personToDto(person)

            assert(personDto.divorced == "yes") {
                "Expected divorced to be 'yes' but was '${'$'}{personDto.divorced}'"
            }
            assert(personDto.widowed == true) {
                "Expected widowed to be true but was ${'$'}{personDto.widowed}"
            }
        }
    """)
}
