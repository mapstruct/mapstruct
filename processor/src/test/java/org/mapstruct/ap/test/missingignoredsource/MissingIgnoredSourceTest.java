/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.missingignoredsource;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

public class MissingIgnoredSourceTest {

    @ProcessorTest
    @WithClasses({ ErroneousSourceTargetMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 18,
                message = "Ignored unknown source property: \"bar\".")
        }
    )
    public void shouldRaiseErrorDueToMissingIgnoredSourceProperty() {
    }
}
