/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1698;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

@IssueKey("1698")
@WithClasses(Erroneous1698Mapper.class)
public class Issue1698Test {

    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Erroneous1698Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                message = "Can't map property \"String rabbit\" to \"Erroneous1698Mapper.Rabbit rabbit\". " +
                    "Consider to declare/implement a mapping method: \"Erroneous1698Mapper.Rabbit map(String value)\".")
        })

    public void testErrorMessage() {
    }
}
