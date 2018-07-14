/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._590;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Andreas Gudian
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses(ErroneousSourceTargetMapper.class)
public class Issue590Test {

    @Test
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = { @Diagnostic(type = ErroneousSourceTargetMapper.class,
            kind = Kind.ERROR,
            messageRegExp = "Can't map property \"java\\.lang\\.String prop\" to \"[^ ]+ prop\"") })
    public void showsCantMapPropertyError() {

    }
}
