/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.erroneous;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Jose Carlos Campanero Ortiz
 */
public class ErroneousJavadocTest {

    @ProcessorTest
    @IssueKey("2987")
    @WithClasses({ EmptyJavadocMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = EmptyJavadocMapper.class,
                kind = Kind.ERROR,
                line = 10,
                message = "'value', 'authors', 'deprecated' and 'since' are undefined in @Javadoc, "
                    + "define at least one of them.")
        }
    )
    public void shouldFailOnEmptyJavadocAnnotation() {
    }
}
