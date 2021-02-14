/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1180;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Sjaak Derksen
 */
@WithClasses( {
    Source.class,
    Target.class,
    SharedConfig.class,
    ErroneousIssue1180Mapper.class
} )
@IssueKey( "1180" )
public class Issue1180Test {

    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIssue1180Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 18,
                messageRegExp = "No property named \"sourceProperty\\.nonExistant\" exists.*")
        })
    public void shouldCompileButNotGiveNullPointer() {
    }
}
