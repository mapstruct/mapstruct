/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignorebydefaultsource;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

public class IgnoreByDefaultSourcesTest {

    @ProcessorTest
    @WithClasses({ SourceTargetMapper.class, Source.class, Target.class })
    public void shouldSucceed() {
    }

    @ProcessorTest
    @WithClasses({ ErroneousSourceTargetMapper.class, Source.class, Target.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 20,
                message = "Unmapped source property: \"other\".")
        }
    )
    public void shouldRaiseErrorDueToNonIgnoredSourceProperty() {
    }
}
