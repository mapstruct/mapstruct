/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._590;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Andreas Gudian
 */
@WithClasses(ErroneousSourceTargetMapper.class)
public class Issue590Test {

    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper.class,
                kind = Kind.ERROR,
                message = "Can't map property \"String prop\" to \"XMLFormatter prop\". " +
                    "Consider to declare/implement a mapping method: \"XMLFormatter map(String value)\".")
        })
    public void showsCantMapPropertyError() {

    }
}
