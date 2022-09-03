/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2674;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static javax.tools.Diagnostic.Kind;

/**
 * @author Justyna Kubica-Ledzion
 */
@IssueKey("2674")
@WithClasses({ Source.class, Target.class, ErroneousSourceTargetMapping.class })
public class Issue2674Test {

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapping.class,
                kind = Kind.ERROR,
                line = 20,
                message = "@BeforeMapping can only be applied to an implemented method."),
            @Diagnostic(type = ErroneousSourceTargetMapping.class,
                kind = Kind.ERROR,
                line = 23,
                message = "@AfterMapping can only be applied to an implemented method.")
        }
    )
    public void shouldRaiseErrorIfThereIsNoAfterOrBeforeMethodImplementation() {
    }
}
