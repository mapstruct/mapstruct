/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2077;

import static javax.tools.Diagnostic.Kind.ERROR;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

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
