/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1283;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1283")
@WithClasses({
    Source.class,
    Target.class
})
public class Issue1283Test {

    @ProcessorTest
    @WithClasses(ErroneousInverseTargetHasNoSuitableConstructorMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousInverseTargetHasNoSuitableConstructorMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22L,
                message = "Source does not have an accessible constructor."
            )
        }
    )
    public void inheritInverseConfigurationReturnTypeHasNoSuitableConstructor() {
    }

    @ProcessorTest
    @WithClasses(ErroneousTargetHasNoSuitableConstructorMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousTargetHasNoSuitableConstructorMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 18L,
                message = "Source does not have an accessible constructor."
            )
        }
    )
    public void returnTypeHasNoSuitableConstructor() {
    }
}
