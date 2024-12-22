/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3786;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Ben Zegveld
 */
@IssueKey( "3786" )
public class Issue3786Test {

    @WithClasses( ErroneousByteArrayMapper.class )
    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = ErroneousByteArrayMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 12,
                message = "Can't generate mapping method from non-iterable type to array."
            )
        }
    )
    void byteArrayReturnTypeShouldGiveInaccessibleContructorError() {
    }
}
