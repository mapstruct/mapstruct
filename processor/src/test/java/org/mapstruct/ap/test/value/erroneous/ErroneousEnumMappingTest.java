/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.erroneous;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.value.CustomIllegalArgumentException;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2169")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    CustomIllegalArgumentException.class,
    ExternalOrderType.class,
    OrderType.class,
})
public class ErroneousEnumMappingTest {

    @Test
    @WithClasses({
        EmptyEnumMappingMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = EmptyEnumMappingMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                message = "'nameTransformationStrategy', 'configuration' and 'defaultException' are undefined in " +
                    "@EnumMapping, define at least one of them."
            )
        }
    )
    public void shouldCompileErrorWhenEnumMappingIsEmpty() {
    }

    @Test
    @WithClasses({
        NoConfigurationEnumMappingMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = NoConfigurationEnumMappingMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 21,
                message = "Configuration has to be defined when strategy is defined.")
        }
    )
    public void shouldCompileErrorWhenEnumMappingHasStrategyButNoConfiguration() {
    }
}
