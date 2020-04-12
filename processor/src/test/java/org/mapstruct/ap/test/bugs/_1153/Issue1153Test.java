/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1153;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses(ErroneousIssue1153Mapper.class)
@IssueKey("1153")
public class Issue1153Test {

    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIssue1153Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "Property \"readOnly\" has no write accessor in " +
                    "org.mapstruct.ap.test.bugs._1153.ErroneousIssue1153Mapper.Target."),
            @Diagnostic(type = ErroneousIssue1153Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                message = "Property \"readOnly\" has no write accessor in org.mapstruct.ap.test.bugs._1153" +
                    ".ErroneousIssue1153Mapper.Target.NestedTarget for target name \"nestedTarget.readOnly\"."),
            @Diagnostic(type = ErroneousIssue1153Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 23,
                message = "Unknown property \"writable2\" in type org.mapstruct.ap.test.bugs._1153" +
                    ".ErroneousIssue1153Mapper.Target.NestedTarget for target name \"nestedTarget2.writable2\". Did " +
                    "you mean \"nestedTarget2.writable\"?")
        })
    @Test
    public void shouldReportErrorsCorrectly() {
    }
}
