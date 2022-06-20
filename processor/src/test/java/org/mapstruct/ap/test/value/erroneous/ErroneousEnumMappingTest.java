/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.erroneous;

import org.mapstruct.ap.test.value.CustomIllegalArgumentException;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2169")
@WithClasses({
    CustomIllegalArgumentException.class,
    ExternalOrderType.class,
    OrderType.class,
})
public class ErroneousEnumMappingTest {

    @ProcessorTest
    @WithClasses({
        EmptyEnumMappingMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = EmptyEnumMappingMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                message = "'nameTransformationStrategy', 'configuration' and 'unexpectedValueMappingException' are " +
                    "undefined in @EnumMapping, define at least one of them."
            )
        }
    )
    public void shouldCompileErrorWhenEnumMappingIsEmpty() {
    }

    @ProcessorTest
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
