/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2347;

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
@IssueKey("2347")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    ErroneousClassWithPrivateMapper.class
})
public class Issue2347Test {

    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = ErroneousClassWithPrivateMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 41,
                message = "Cannot create an implementation for mapper PrivateInterfaceMapper," +
                    " because it is a private interface."
            ),
            @Diagnostic(
                type = ErroneousClassWithPrivateMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 47,
                message = "Cannot create an implementation for mapper PrivateClassMapper," +
                    " because it is a private class."
            )
        }
    )
    @Test
    public void shouldGenerateCompileErrorWhenMapperIsPrivate() {

    }
}
