/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.sealed

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for sealed class mapping with KSP processor.
 */
class SealedClassMappingTest {

    @Test
    fun shouldMapSealedClassSubtype() = pluginTest("""
        import org.mapstruct.Mapper

        sealed class SourceResult {
            data class Success(val data: String) : SourceResult()
            data class Error(val message: String) : SourceResult()
        }

        data class TargetSuccess(val data: String)

        @Mapper
        interface SealedMapper {
            fun mapSuccess(source: SourceResult.Success): TargetSuccess
        }

        fun test() {
            val mapper = SealedMapperImpl()
            val source = SourceResult.Success("Hello")
            val target = mapper.mapSuccess(source)

            assert(target.data == "Hello") {
                "Expected data to be 'Hello' but was '${'$'}{target.data}'"
            }
        }
    """)

    @Test
    fun shouldMapToSealedClassSubtype() = pluginTest("""
        import org.mapstruct.Mapper

        data class SourceData(val value: String)

        sealed class TargetResult {
            data class Success(val value: String) : TargetResult()
            data class Failure(val error: String) : TargetResult()
        }

        @Mapper
        interface ToSealedMapper {
            fun mapToSuccess(source: SourceData): TargetResult.Success
        }

        fun test() {
            val mapper = ToSealedMapperImpl()
            val source = SourceData("Test")
            val target = mapper.mapToSuccess(source)

            assert(target.value == "Test") {
                "Expected value to be 'Test' but was '${'$'}{target.value}'"
            }
        }
    """)

    @Test
    fun shouldMapBetweenSealedClassSubtypes() = pluginTest("""
        import org.mapstruct.Mapper

        sealed class SourceEvent {
            data class UserCreated(val username: String, val email: String) : SourceEvent()
            data class UserDeleted(val userId: Long) : SourceEvent()
        }

        sealed class TargetEvent {
            data class Created(val username: String, val email: String) : TargetEvent()
            data class Deleted(val userId: Long) : TargetEvent()
        }

        @Mapper
        interface SealedToSealedMapper {
            fun mapCreated(source: SourceEvent.UserCreated): TargetEvent.Created
            fun mapDeleted(source: SourceEvent.UserDeleted): TargetEvent.Deleted
        }

        fun test() {
            val mapper = SealedToSealedMapperImpl()

            val created = SourceEvent.UserCreated("alice", "alice@test.com")
            val targetCreated = mapper.mapCreated(created)
            assert(targetCreated.username == "alice") { "Expected username 'alice'" }
            assert(targetCreated.email == "alice@test.com") { "Expected email 'alice@test.com'" }

            val deleted = SourceEvent.UserDeleted(123L)
            val targetDeleted = mapper.mapDeleted(deleted)
            assert(targetDeleted.userId == 123L) { "Expected userId 123L" }
        }
    """)
}
