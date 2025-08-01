/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignoreunmapped;

import org.mapstruct.ap.test.ignoreunmapped.mapper.UserMapper;
import org.mapstruct.ap.test.ignoreunmapped.mapper.UserMapperWithMultiMapping;
import org.mapstruct.ap.test.ignoreunmapped.mapper.UserMapperWithWarnPolicyInMapperConfig;
import org.mapstruct.ap.test.ignoreunmapped.mapper.UserMapperWithErrorPolicyInMapperConfig;
import org.mapstruct.ap.test.ignoreunmapped.mapper.UserMapperWithoutBeanMapping;
import org.mapstruct.ap.test.ignoreunmapped.mapper.UserMapperWithIgnorePolicyInMapperConfig;
import org.mapstruct.ap.test.ignoreunmapped.mapper.UserMapperWithWarnSourcePolicy;
import org.mapstruct.ap.test.ignoreunmapped.mapper.UserMapperWithIgnoreSourcePolicy;
import org.mapstruct.ap.test.ignoreunmapped.mapper.UserMapperWithErrorSourcePolicy;
import org.mapstruct.ap.test.ignoreunmapped.mapper.UserMapperWithErrorSourcePolicyInMapper;
import org.mapstruct.ap.test.ignoreunmapped.mapper.UserMapperWithWarnSourcePolicyInMapper;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import javax.tools.Diagnostic.Kind;

/**
 * Verifies that mapped properties listed in ignoreUnmappedSourceProperties trigger a warning.
 *
 * @author Ritesh Chopade(codeswithritesh)
 */
@IssueKey("3837")
@WithClasses({UserEntity.class, UserDto.class})
public class IgnoredMappedPropertyTest {

    @ProcessorTest
    @WithClasses({
            UserMapper.class,
            UserMapperWithIgnoreSourcePolicy.class,
            UserMapperWithoutBeanMapping.class,
            UserMapperWithIgnorePolicyInMapperConfig.class
    })
    @ExpectedCompilationOutcome(
            value = CompilationResult.SUCCEEDED
    )
    public void shouldNotWarnAboutRedundantIgnore() {
    }

    @ProcessorTest
    @WithClasses({
            UserMapperWithErrorSourcePolicy.class,
            UserMapperWithErrorSourcePolicyInMapper.class,
            UserMapperWithErrorPolicyInMapperConfig.class
    })
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic(
                            type = UserMapperWithErrorSourcePolicy.class,
                            kind = Kind.ERROR,
                            line = 20,
                            message = "Source property \"email\" is mapped despite being listed in ignoreUnmappedSourceProperties."
                    ),
                    @Diagnostic(
                            type = UserMapperWithErrorSourcePolicyInMapper.class,
                            kind = Kind.ERROR,
                            line = 19,
                            message = "Source property \"email\" is mapped despite being listed in ignoreUnmappedSourceProperties."
                    ),
                    @Diagnostic(
                            type = UserMapperWithErrorPolicyInMapperConfig.class,
                            kind = Kind.ERROR,
                            line = 23,
                            message = "Source property \"email\" is mapped despite being listed in ignoreUnmappedSourceProperties."
                    )
            }
    )
    public void shouldWarnAboutRedundantIgnoreWithErrorPolicy() {
    }

    @ProcessorTest
    @WithClasses({
            UserMapperWithWarnSourcePolicy.class,
            UserMapperWithWarnSourcePolicyInMapper.class,
            UserMapperWithWarnPolicyInMapperConfig.class
    })
    @ExpectedCompilationOutcome(
            value = CompilationResult.SUCCEEDED,
            diagnostics = {
                    @Diagnostic(
                            type = UserMapperWithWarnSourcePolicy.class,
                            kind = Kind.WARNING,
                            line = 20,
                            message = "Source property \"email\" is mapped despite being listed in ignoreUnmappedSourceProperties."
                    ),
                    @Diagnostic(
                            type = UserMapperWithWarnSourcePolicyInMapper.class,
                            kind = Kind.WARNING,
                            line = 19,
                            message = "Source property \"email\" is mapped despite being listed in ignoreUnmappedSourceProperties."
                    ),
                    @Diagnostic(
                            type = UserMapperWithWarnPolicyInMapperConfig.class,
                            kind = Kind.WARNING,
                            line = 23,
                            message = "Source property \"email\" is mapped despite being listed in ignoreUnmappedSourceProperties."
                    )
            }
    )
    public void shouldWarnAboutRedundantIgnoreWithWarnPolicy() {
    }

    @ProcessorTest
    @WithClasses({UserMapperWithMultiMapping.class})
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic(
                            type = UserMapperWithMultiMapping.class,
                            kind = Kind.ERROR,
                            line = 21,
                            message = "Source properties \"email, username\" are mapped despite being listed in ignoreUnmappedSourceProperties."
                    )
            }
    )
    public void shouldWarnAboutRedundantIgnoreWithMultiMapping() {
    }
}
