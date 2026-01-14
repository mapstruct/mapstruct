/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.reverse

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for reverse/bidirectional mapping with KSP processor.
 */
class ReverseMappingTest {

    @Test
    fun shouldMapBidirectionally() = pluginTest("""
        import org.mapstruct.Mapper

        data class Dto(val name: String, val age: Int)
        data class Entity(val name: String, val age: Int)

        @Mapper
        interface BidirectionalMapper {
            fun toDto(entity: Entity): Dto
            fun toEntity(dto: Dto): Entity
        }

        fun test() {
            val mapper = BidirectionalMapperImpl()

            // Entity -> DTO
            val entity = Entity("Alice", 30)
            val dto = mapper.toDto(entity)
            assert(dto.name == "Alice") {
                "Expected DTO name 'Alice' but was '${'$'}{dto.name}'"
            }
            assert(dto.age == 30) {
                "Expected DTO age 30 but was ${'$'}{dto.age}"
            }

            // DTO -> Entity
            val dto2 = Dto("Bob", 25)
            val entity2 = mapper.toEntity(dto2)
            assert(entity2.name == "Bob") {
                "Expected Entity name 'Bob' but was '${'$'}{entity2.name}'"
            }
            assert(entity2.age == 25) {
                "Expected Entity age 25 but was ${'$'}{entity2.age}"
            }
        }
    """)

    @Test
    fun shouldMapBidirectionallyWithDifferentPropertyNames() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.Mapping
        import org.mapstruct.InheritInverseConfiguration

        data class PersonDto(val fullName: String)
        data class PersonEntity(val name: String)

        @Mapper
        interface RenamedBidirectionalMapper {
            @Mapping(source = "name", target = "fullName")
            fun toDto(entity: PersonEntity): PersonDto

            @InheritInverseConfiguration
            fun toEntity(dto: PersonDto): PersonEntity
        }

        fun test() {
            val mapper = RenamedBidirectionalMapperImpl()

            // Entity -> DTO
            val entity = PersonEntity("Alice")
            val dto = mapper.toDto(entity)
            assert(dto.fullName == "Alice") {
                "Expected fullName 'Alice' but was '${'$'}{dto.fullName}'"
            }

            // DTO -> Entity (using inverse config)
            val dto2 = PersonDto("Bob")
            val entity2 = mapper.toEntity(dto2)
            assert(entity2.name == "Bob") {
                "Expected name 'Bob' but was '${'$'}{entity2.name}'"
            }
        }
    """)

    @Test
    fun shouldMapToAndFromSelf() = pluginTest("""
        import org.mapstruct.Mapper

        data class Data(val value: String)

        @Mapper
        interface SelfMapper {
            fun copy(source: Data): Data
        }

        fun test() {
            val mapper = SelfMapperImpl()
            val source = Data("test")
            val copy = mapper.copy(source)

            assert(copy.value == "test") {
                "Expected value 'test' but was '${'$'}{copy.value}'"
            }
            // Verify it's a copy, not the same instance
            assert(copy !== source) {
                "Expected a copy, not the same instance"
            }
        }
    """)

    @Test
    fun shouldMapBidirectionallyWithNestedObjects() = pluginTest("""
        import org.mapstruct.Mapper

        data class InnerDto(val value: String)
        data class OuterDto(val inner: InnerDto)
        data class InnerEntity(val value: String)
        data class OuterEntity(val inner: InnerEntity)

        @Mapper
        interface NestedBidirectionalMapper {
            fun toDto(entity: OuterEntity): OuterDto
            fun toEntity(dto: OuterDto): OuterEntity
        }

        fun test() {
            val mapper = NestedBidirectionalMapperImpl()

            // Entity -> DTO
            val entity = OuterEntity(InnerEntity("test"))
            val dto = mapper.toDto(entity)
            assert(dto.inner.value == "test") { "Expected inner value 'test'" }

            // DTO -> Entity
            val dto2 = OuterDto(InnerDto("reverse"))
            val entity2 = mapper.toEntity(dto2)
            assert(entity2.inner.value == "reverse") { "Expected inner value 'reverse'" }
        }
    """)

    @Test
    fun shouldMapBidirectionallyWithTypeConversion() = pluginTest("""
        import org.mapstruct.Mapper

        data class DtoWithString(val value: String)
        data class EntityWithLong(val value: Long)

        @Mapper
        interface TypeConvertingBidirectionalMapper {
            fun toDto(entity: EntityWithLong): DtoWithString
            fun toEntity(dto: DtoWithString): EntityWithLong
        }

        fun test() {
            val mapper = TypeConvertingBidirectionalMapperImpl()

            // Entity -> DTO
            val entity = EntityWithLong(12345L)
            val dto = mapper.toDto(entity)
            assert(dto.value == "12345") { "Expected '12345' but was '${'$'}{dto.value}'" }

            // DTO -> Entity
            val dto2 = DtoWithString("67890")
            val entity2 = mapper.toEntity(dto2)
            assert(entity2.value == 67890L) { "Expected 67890L but was ${'$'}{entity2.value}" }
        }
    """)

    @Test
    fun shouldMapBidirectionallyWithMultipleProperties() = pluginTest("""
        import org.mapstruct.Mapper

        data class PersonDto(
            val firstName: String,
            val lastName: String,
            val age: Int,
            val active: Boolean
        )

        data class PersonEntity(
            val firstName: String,
            val lastName: String,
            val age: Int,
            val active: Boolean
        )

        @Mapper
        interface MultiPropertyBidirectionalMapper {
            fun toDto(entity: PersonEntity): PersonDto
            fun toEntity(dto: PersonDto): PersonEntity
        }

        fun test() {
            val mapper = MultiPropertyBidirectionalMapperImpl()

            // Entity -> DTO
            val entity = PersonEntity("John", "Doe", 30, true)
            val dto = mapper.toDto(entity)
            assert(dto.firstName == "John") { "Expected firstName 'John'" }
            assert(dto.lastName == "Doe") { "Expected lastName 'Doe'" }
            assert(dto.age == 30) { "Expected age 30" }
            assert(dto.active == true) { "Expected active true" }

            // DTO -> Entity
            val dto2 = PersonDto("Jane", "Smith", 25, false)
            val entity2 = mapper.toEntity(dto2)
            assert(entity2.firstName == "Jane") { "Expected firstName 'Jane'" }
            assert(entity2.lastName == "Smith") { "Expected lastName 'Smith'" }
            assert(entity2.age == 25) { "Expected age 25" }
            assert(entity2.active == false) { "Expected active false" }
        }
    """)
}
