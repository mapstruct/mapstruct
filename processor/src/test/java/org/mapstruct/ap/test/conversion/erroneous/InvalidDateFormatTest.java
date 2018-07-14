/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.erroneous;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Source.class,
    Target.class
})
@IssueKey("725")
@RunWith(AnnotationProcessorTestRunner.class)
public class InvalidDateFormatTest {

    @WithClasses({
        ErroneousFormatMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 38,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern character 'q'\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 39,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Unknown pattern letter: r\"\\."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 40,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Unknown pattern letter: r\"\\."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 41,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Unknown pattern letter: r\"\\."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 42,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Unknown pattern letter: r\"\\."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 43,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern component: q\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 44,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern component: q\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 45,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern component: q\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 46,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern component: q\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 50,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern character 'q'\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 53,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern character 'q'\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 56,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern character 'q'\"")
        })
    @Test
    public void shouldFailWithInvalidDateFormats() {
    }
}
