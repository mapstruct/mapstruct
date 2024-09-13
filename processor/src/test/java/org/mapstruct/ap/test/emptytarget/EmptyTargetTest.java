/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.emptytarget;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

@IssueKey("1140")
@WithClasses({
    EmptyTarget.class,
    EmptyTargetMapper.class,
    Source.class,
    Target.class,
    TargetWithNoSetters.class,
})
class EmptyTargetTest {

    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = EmptyTargetMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 13,
                message = "No target property found for target \"TargetWithNoSetters\"."),
            @Diagnostic(type = EmptyTargetMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 15,
                message = "No target property found for target \"EmptyTarget\".")
        })
    void shouldProvideWarnings() {
    }
}
