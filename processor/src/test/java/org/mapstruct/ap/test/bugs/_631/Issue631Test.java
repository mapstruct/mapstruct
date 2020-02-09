/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._631;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Sjaak Derksen
 */
public class Issue631Test {

    @ProcessorTest
    @IssueKey("631")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 22,
                messageRegExp = "Can't generate mapping method for a generic type variable target."),
            @Diagnostic(type = ErroneousSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 24,
                messageRegExp = "Can't generate mapping method for a generic type variable source.")
        }
    )
    @WithClasses({ErroneousSourceTargetMapper.class, Base1.class, Base2.class})
    public void showsCantMapPropertyError() {

    }
}
