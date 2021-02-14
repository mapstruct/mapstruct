/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2077;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static javax.tools.Diagnostic.Kind.ERROR;

/**
 * @author Sjaak Derksen
 */
@IssueKey("2077")
public class Issue2077Test {

    @ProcessorTest
    @WithClasses(Issue2077ErroneousMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Issue2077ErroneousMapper.class,
                kind = ERROR,
                line = 18,
                message = "The type of parameter \"source\" has no property named \"s1\". Please define the source " +
                    "property explicitly.")
        }
    )
    public void shouldNotCompile() {
    }
}
