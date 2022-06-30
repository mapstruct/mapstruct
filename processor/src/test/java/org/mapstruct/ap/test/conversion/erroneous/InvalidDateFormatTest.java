/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.erroneous;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJoda;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Source.class,
    Target.class
})
@WithJoda
@IssueKey("725")
public class InvalidDateFormatTest {

    @WithClasses({
        ErroneousFormatMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 25,
                message = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern character 'q'\"."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 26,
                message = "Given date format \"qwertz\" is invalid. Message: \"Unknown pattern letter: r\"."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 27,
                message = "Given date format \"qwertz\" is invalid. Message: \"Unknown pattern letter: r\"."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 28,
                message = "Given date format \"qwertz\" is invalid. Message: \"Unknown pattern letter: r\"."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                message = "Given date format \"qwertz\" is invalid. Message: \"Unknown pattern letter: r\"."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 30,
                message = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern component: q\"."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 31,
                message = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern component: q\"."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 32,
                message = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern component: q\"."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 33,
                message = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern component: q\"."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 37,
                message = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern character 'q'\"."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 40,
                message = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern character 'q'\"."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 43,
                message = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern character 'q'\".")
        })
    @ProcessorTest
    public void shouldFailWithInvalidDateFormats() {
    }
}
