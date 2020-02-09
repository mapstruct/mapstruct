/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1153;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

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
                messageRegExp = "Property \"readOnly\" has no write accessor in " +
                    "org.mapstruct.ap.test.bugs._1153.ErroneousIssue1153Mapper.Target\\."),
            @Diagnostic(type = ErroneousIssue1153Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                messageRegExp = "Property \"nestedTarget.readOnly\" has no write accessor in " +
                    "org.mapstruct.ap.test.bugs._1153.ErroneousIssue1153Mapper.Target\\."),
            @Diagnostic(type = ErroneousIssue1153Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 23,
                messageRegExp = "Unknown property \"nestedTarget2.writable2\" in result type " +
                    "org.mapstruct.ap.test.bugs._1153.ErroneousIssue1153Mapper.Target\\. " +
                    "Did you mean \"nestedTarget2\\.writable\"")
        })
    @ProcessorTest
    public void shouldReportErrorsCorrectly() {
    }
}
