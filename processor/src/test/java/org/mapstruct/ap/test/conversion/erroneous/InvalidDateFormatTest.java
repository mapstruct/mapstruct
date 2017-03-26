/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
