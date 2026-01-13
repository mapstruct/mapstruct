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

        class Person {
            private var _married: Boolean? = null
            private var _engaged: Boolean? = null
            private var _divorced: YesNo? = null
            private var _widowed: YesNo? = null

            fun isMarried(): Boolean? = _married
            fun setMarried(value: Boolean?) { _married = value }

            // START: please note: deliberately ordered, first getEngaged, then isEngaged.
            fun getEngaged(): Boolean? = _engaged

            fun isEngaged(): Boolean = _engaged?.let { !it } ?: false
            // END
            fun setEngaged(value: Boolean?) { _engaged = value }

            fun getDivorced(): YesNo? = _divorced
            fun setDivorced(value: YesNo?) { _divorced = value }

            fun getWidowed(): YesNo? = _widowed
            fun setWidowed(value: YesNo?) { _widowed = value }
        }

        data class PersonDto(
            var married: String? = null,
            var engaged: String? = null,
            var divorced: String? = null,
            var widowed: Boolean? = null
        )

        data class YesNo(val yes: Boolean)

        class YesNoMapper {
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
            val person = Person()
            person.setMarried(true)
            val personDto = PersonMapperImpl().personToDto(person)

            assert(personDto.married == "true") {
                "Expected married to be 'true' but was '${'$'}{personDto.married}'"
            }
        }
    """)

    @Test
    fun shouldMapBooleanPropertyPreferringGetPrefixedGetterOverIsPrefixedGetter() = pluginTest("""
        import org.mapstruct.Mapper

        class Person {
            private var _married: Boolean? = null
            private var _engaged: Boolean? = null
            private var _divorced: YesNo? = null
            private var _widowed: YesNo? = null

            fun isMarried(): Boolean? = _married
            fun setMarried(value: Boolean?) { _married = value }

            fun getEngaged(): Boolean? = _engaged

            fun isEngaged(): Boolean = _engaged?.let { !it } ?: false
            fun setEngaged(value: Boolean?) { _engaged = value }

            fun getDivorced(): YesNo? = _divorced
            fun setDivorced(value: YesNo?) { _divorced = value }

            fun getWidowed(): YesNo? = _widowed
            fun setWidowed(value: YesNo?) { _widowed = value }
        }

        data class PersonDto(
            var married: String? = null,
            var engaged: String? = null,
            var divorced: String? = null,
            var widowed: Boolean? = null
        )

        data class YesNo(val yes: Boolean)

        class YesNoMapper {
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
            val person = Person()
            person.setEngaged(true)
            val personDto = PersonMapperImpl().personToDto(person)

            assert(personDto.engaged == "true") {
                "Expected engaged to be 'true' but was '${'$'}{personDto.engaged}'"
            }
        }
    """)

    @Test
    fun shouldMapBooleanPropertyWithPropertyMappingMethod() = pluginTest("""
        import org.mapstruct.Mapper

        class Person {
            private var _married: Boolean? = null
            private var _engaged: Boolean? = null
            private var _divorced: YesNo? = null
            private var _widowed: YesNo? = null

            fun isMarried(): Boolean? = _married
            fun setMarried(value: Boolean?) { _married = value }

            fun getEngaged(): Boolean? = _engaged

            fun isEngaged(): Boolean = _engaged?.let { !it } ?: false
            fun setEngaged(value: Boolean?) { _engaged = value }

            fun getDivorced(): YesNo? = _divorced
            fun setDivorced(value: YesNo?) { _divorced = value }

            fun getWidowed(): YesNo? = _widowed
            fun setWidowed(value: YesNo?) { _widowed = value }
        }

        data class PersonDto(
            var married: String? = null,
            var engaged: String? = null,
            var divorced: String? = null,
            var widowed: Boolean? = null
        )

        data class YesNo(val yes: Boolean)

        class YesNoMapper {
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
            val person = Person()
            person.setDivorced(YesNo(true))
            person.setWidowed(YesNo(true))
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
