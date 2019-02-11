/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1698;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1698")
@WithClasses(Erroneous1698Mapper.class)
public class Issue1698Test {

    @Test
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Erroneous1698Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                messageRegExp = "Can't map property.*")
        })

    public void testErrorMessage() {
    }
}
