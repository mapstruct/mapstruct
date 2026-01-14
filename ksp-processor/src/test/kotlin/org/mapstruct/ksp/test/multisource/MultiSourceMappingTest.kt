/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.multisource

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for mapping from multiple source parameters with KSP processor.
 */
class MultiSourceMappingTest {

    @Test
    fun shouldMapMultipleSourcesToOneTarget() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class PersonInfo(
            val firstName: String,
            val lastName: String
        )

        data class ContactInfo(
            val email: String,
            val phone: String
        )

        data class PersonDto(
            val fullName: String,
            val email: String,
            val phone: String
        )

        @Mapper
        interface MultiSourceMapper {
            @Mapping(target = "fullName", expression = "java(personInfo.getFirstName() + \" \" + personInfo.getLastName())")
            fun map(personInfo: PersonInfo, contactInfo: ContactInfo): PersonDto
        }

        fun test() {
            val mapper = MultiSourceMapperImpl()
            val person = PersonInfo("John", "Doe")
            val contact = ContactInfo("john@example.com", "555-1234")
            val dto = mapper.map(person, contact)

            assert(dto.fullName == "John Doe") {
                "Expected fullName to be 'John Doe' but was '${'$'}{dto.fullName}'"
            }
            assert(dto.email == "john@example.com") {
                "Expected email to be 'john@example.com' but was '${'$'}{dto.email}'"
            }
            assert(dto.phone == "555-1234") {
                "Expected phone to be '555-1234' but was '${'$'}{dto.phone}'"
            }
        }
    """)

    @Test
    fun shouldMapMultipleSourcesWithSamePropertyNames() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping
        import org.mapstruct.Mappings

        data class User(val id: Long, val name: String)
        data class Address(val id: Long, val street: String)
        data class Combined(val userId: Long, val addressId: Long, val userName: String, val street: String)

        @Mapper
        interface AmbiguousSourceMapper {
            @Mappings(
                Mapping(source = "user.id", target = "userId"),
                Mapping(source = "address.id", target = "addressId"),
                Mapping(source = "user.name", target = "userName")
            )
            fun combine(user: User, address: Address): Combined
        }

        fun test() {
            val mapper = AmbiguousSourceMapperImpl()
            val user = User(1L, "Alice")
            val address = Address(100L, "Main St")
            val combined = mapper.combine(user, address)

            assert(combined.userId == 1L) { "Expected userId 1L but was ${'$'}{combined.userId}" }
            assert(combined.addressId == 100L) { "Expected addressId 100L but was ${'$'}{combined.addressId}" }
            assert(combined.userName == "Alice") { "Expected userName 'Alice' but was '${'$'}{combined.userName}'" }
            assert(combined.street == "Main St") { "Expected street 'Main St' but was '${'$'}{combined.street}'" }
        }
    """)

    @Test
    fun shouldMapThreeSourcesIntoOne() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping

        data class First(val a: String)
        data class Second(val b: String)
        data class Third(val c: String)
        data class Result(val a: String, val b: String, val c: String)

        @Mapper
        interface ThreeSourceMapper {
            fun merge(first: First, second: Second, third: Third): Result
        }

        fun test() {
            val mapper = ThreeSourceMapperImpl()
            val result = mapper.merge(First("A"), Second("B"), Third("C"))

            assert(result.a == "A") { "Expected a='A' but was '${'$'}{result.a}'" }
            assert(result.b == "B") { "Expected b='B' but was '${'$'}{result.b}'" }
            assert(result.c == "C") { "Expected c='C' but was '${'$'}{result.c}'" }
        }
    """)

    @Test
    fun shouldMapFromTwoSourcesWithTypeConversion() = pluginTest("""
        import org.mapstruct.Mapper

        data class SourceA(val count: Int)
        data class SourceB(val total: Long)
        data class Result(val count: String, val total: String)

        @Mapper
        interface TwoSourceConversionMapper {
            fun combine(a: SourceA, b: SourceB): Result
        }

        fun test() {
            val mapper = TwoSourceConversionMapperImpl()
            val result = mapper.combine(SourceA(42), SourceB(100L))

            assert(result.count == "42") { "Expected count '42'" }
            assert(result.total == "100") { "Expected total '100'" }
        }
    """)

    @Test
    fun shouldMapFromTwoSourcesWithDifferentTypes() = pluginTest("""
        import org.mapstruct.Mapper

        data class PersonSource(val name: String, val age: Int)
        data class WorkSource(val title: String, val salary: Long)
        data class EmployeeDto(
            val name: String,
            val age: Int,
            val title: String,
            val salary: Long
        )

        @Mapper
        interface EmployeeMapper {
            fun toEmployee(person: PersonSource, work: WorkSource): EmployeeDto
        }

        fun test() {
            val mapper = EmployeeMapperImpl()
            val person = PersonSource("John", 30)
            val work = WorkSource("Developer", 50000L)
            val employee = mapper.toEmployee(person, work)

            assert(employee.name == "John") { "Expected name 'John'" }
            assert(employee.age == 30) { "Expected age 30" }
            assert(employee.title == "Developer") { "Expected title 'Developer'" }
            assert(employee.salary == 50000L) { "Expected salary 50000L" }
        }
    """)
}
