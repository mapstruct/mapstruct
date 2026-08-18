/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3385;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

@WithClasses(Issue3385Mapper.class)
@IssueKey("3385")
class Issue3385Test {

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(
                type = Issue3385Mapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 24,
                message = "Lifecycle method unused is not used."
            ),
            @Diagnostic(
                type = Issue3385Mapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 29,
                message = "Lifecycle method unusedBefore is not used."
            )
        }
    )
    void shouldWarnForUnusedLifecycleMethod() {
    }

}
