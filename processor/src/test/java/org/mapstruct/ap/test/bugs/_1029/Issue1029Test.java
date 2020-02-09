/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1029;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

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
            messageRegExp = "Unmapped target properties: \"knownProp, lastUpdated, computedMapping\"\\."),
        @Diagnostic(kind = Kind.WARNING, line = 37, type = ErroneousIssue1029Mapper.class,
            messageRegExp = "Unmapped target property: \"lastUpdated\"\\."),
        @Diagnostic(kind = Kind.ERROR, line = 42, type = ErroneousIssue1029Mapper.class,
            messageRegExp = "Unknown property \"unknownProp\" in result type " +
                "org.mapstruct.ap.test.bugs._1029.ErroneousIssue1029Mapper.Deck\\. Did you mean \"knownProp\"?")
    })
    public void reportsProperWarningsAndError() {
    }
}
