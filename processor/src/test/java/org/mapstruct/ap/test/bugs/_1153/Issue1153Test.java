/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1153;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Filip Hrisafov
 */
@WithClasses(ErroneousIssue1153Mapper.class)
@IssueKey("1153")
public class Issue1153Test {

    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIssue1153Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "Property \"readOnly\" has no write accessor in ErroneousIssue1153Mapper.Target."),
            @Diagnostic(type = ErroneousIssue1153Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                message =
                    "Property \"readOnly\" has no write accessor in ErroneousIssue1153Mapper.Target.NestedTarget " +
                    "for target name \"nestedTarget.readOnly\"."),
            @Diagnostic(type = ErroneousIssue1153Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 23,
                message = "Unknown property \"writable2\" in type ErroneousIssue1153Mapper.Target.NestedTarget " +
                    "for target name \"nestedTarget2.writable2\". Did you mean \"nestedTarget2.writable\"?")
        })
    @ProcessorTest
    public void shouldReportErrorsCorrectly() {
    }
}
