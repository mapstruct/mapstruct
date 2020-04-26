/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1283;

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
@IssueKey("1283")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Source.class,
    Target.class
})
public class Issue1283Test {

    @Test
    @WithClasses(ErroneousInverseTargetHasNoSuitableConstructorMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousInverseTargetHasNoSuitableConstructorMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22L,
                message = "org.mapstruct.ap.test.bugs._1283.Source does not have an accessible constructor."
            )
        }
    )
    public void inheritInverseConfigurationReturnTypeHasNoSuitableConstructor() {
    }

    @Test
    @WithClasses(ErroneousTargetHasNoSuitableConstructorMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousTargetHasNoSuitableConstructorMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 18L,
                message = "org.mapstruct.ap.test.bugs._1283.Source does not have an accessible constructor."
            )
        }
    )
    public void returnTypeHasNoSuitableConstructor() {
    }
}
