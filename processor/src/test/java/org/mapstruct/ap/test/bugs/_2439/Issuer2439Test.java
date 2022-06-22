/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2439;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2439")
@WithClasses({
    ErroneousIssue2439Mapper.class
})
class Issuer2439Test {

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIssue2439Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                message = "No property named \"mode.desc\" exists in source parameter(s)." +
                    " Type \"ErroneousIssue2439Mapper.LiveMode[]\" has no properties.")
        }
    )
    void shouldProvideGoodErrorMessage() {

    }
}
