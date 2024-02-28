/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3485;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author hduelme
 */
@IssueKey("3463")
@WithClasses({
        Issue3485Mapper.class
})
public class Issue3485Test {
    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic(type = Issue3485Mapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 33,
                            message = "Using @Mapping( target = \".\") requires a source property. Expression or " +
                                    "constant could not be used as a source.")
            })
    void thisMappingWithoutSource() {
    }
}
