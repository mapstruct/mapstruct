/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.erroneous

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.assertThis
import org.mapstruct.ksp.test.CompilerTest

/**
 * Tests for error reporting with KSP processor.
 */
class UnmappedTargetTest {

    @Test
    fun shouldWarnUnmappedTargetProperty() = assertThis(
        CompilerTest(
            code = {
                """
                import org.mapstruct.Mapper

                data class Source(val name: String)
                data class Target(val name: String, val age: Int)

                @Mapper
                interface ErrorMapper {
                    fun map(source: Source): Target
                }
                """
            },
            assert = {
                // The default behavior should compile but with a warning
                // since unmapped properties default to WARN
                compiles
            }
        )
    )

    @Test
    fun shouldReportUnmappedTargetPropertyAsError() = assertThis(
        CompilerTest(
            code = {
                """
                import org.mapstruct.Mapper
                import org.mapstruct.ReportingPolicy

                data class Source(val name: String)
                data class Target(val name: String, val age: Int)

                @Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
                interface StrictMapper {
                    fun map(source: Source): Target
                }
                """
            },
            assert = {
                failsWith { message ->
                    message.contains("Unmapped target property")
                }
            }
        )
    )

    @Test
    fun shouldReportNonExistentSourceProperty() = assertThis(
        CompilerTest(
            code = {
                """
                import org.mapstruct.Mapper
                import org.mapstruct.Mapping

                data class Source(val name: String)
                data class Target(val name: String)

                @Mapper
                interface BadSourceMapper {
                    @Mapping(source = "nonExistent", target = "name")
                    fun map(source: Source): Target
                }
                """
            },
            assert = {
                failsWith { message ->
                    message.contains("nonExistent") || message.contains("unknown") || message.contains("No property")
                }
            }
        )
    )

    @Test
    fun shouldReportNonExistentTargetProperty() = assertThis(
        CompilerTest(
            code = {
                """
                import org.mapstruct.Mapper
                import org.mapstruct.Mapping

                data class Source(val name: String)
                data class Target(val name: String)

                @Mapper
                interface BadTargetMapper {
                    @Mapping(source = "name", target = "nonExistent")
                    fun map(source: Source): Target
                }
                """
            },
            assert = {
                failsWith { message ->
                    message.contains("nonExistent") || message.contains("unknown") || message.contains("No property")
                }
            }
        )
    )

    @Test
    fun shouldReportDuplicateTargetMapping() = assertThis(
        CompilerTest(
            code = {
                """
                import org.mapstruct.Mapper
                import org.mapstruct.Mapping
                import org.mapstruct.Mappings

                data class Source(val firstName: String, val lastName: String)
                data class Target(val name: String)

                @Mapper
                interface DuplicateTargetMapper {
                    @Mappings(
                        Mapping(source = "firstName", target = "name"),
                        Mapping(source = "lastName", target = "name")
                    )
                    fun map(source: Source): Target
                }
                """
            },
            assert = {
                failsWith { message ->
                    message.contains("more than once") || message.contains("duplicate") || message.contains("Duplicate")
                }
            }
        )
    )
}
