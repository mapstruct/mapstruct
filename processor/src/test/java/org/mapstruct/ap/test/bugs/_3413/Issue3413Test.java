/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3413;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Muhammad Usama
 */
@IssueKey("3413")
public class Issue3413Test {
    @ProcessorTest
    @WithClasses(Erroneous3413Mapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "Expression and condition qualified by name are both defined in @Mapping, " +
                    "either define an expression or a condition qualified by name."
            )
        }
    )
    void errorExpectedBecauseExpressionAndConditionQualifiedByNameCannotCoExists() {
    }
}
