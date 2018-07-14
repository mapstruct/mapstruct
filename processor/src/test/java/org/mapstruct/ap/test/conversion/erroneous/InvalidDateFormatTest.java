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
                line = 25,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern character 'q'\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 26,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Unknown pattern letter: r\"\\."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 27,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Unknown pattern letter: r\"\\."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 28,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Unknown pattern letter: r\"\\."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Unknown pattern letter: r\"\\."),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 30,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern component: q\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 31,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern component: q\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 32,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern component: q\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 33,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern component: q\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 37,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern character 'q'\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 40,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern character 'q'\""),
            @Diagnostic(type = ErroneousFormatMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 43,
                messageRegExp = "Given date format \"qwertz\" is invalid. Message: \"Illegal pattern character 'q'\"")
        })
    @Test
    public void shouldFailWithInvalidDateFormats() {
    }
}
