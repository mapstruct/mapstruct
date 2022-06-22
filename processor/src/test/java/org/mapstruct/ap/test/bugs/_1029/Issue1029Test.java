/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1029;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * Verifies that read-only properties can be explicitly mentioned as {@code ignored=true} without raising an error.
 *
 * @author Andreas Gudian
 */
@WithClasses(ErroneousIssue1029Mapper.class)
@IssueKey("1029")
public class Issue1029Test {

    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic(kind = Kind.WARNING, line = 26, type = ErroneousIssue1029Mapper.class,
            message = "Unmapped target properties: \"knownProp, lastUpdated, computedMapping\"."),
        @Diagnostic(kind = Kind.WARNING, line = 37, type = ErroneousIssue1029Mapper.class,
            message = "Unmapped target property: \"lastUpdated\"."),
        @Diagnostic(kind = Kind.ERROR, line = 42, type = ErroneousIssue1029Mapper.class,
            message = "Unknown property \"unknownProp\" in result type ErroneousIssue1029Mapper.Deck. " +
                "Did you mean \"knownProp\"?")
    })
    public void reportsProperWarningsAndError() {
    }
}
