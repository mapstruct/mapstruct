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
package org.mapstruct.ap.test.nestedbeans.exclusions;

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
    Target.class,
    ErroneousJavaInternalMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1154")
public class ErroneousJavaInternalTest {

    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousJavaInternalMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = "Can't map property \".*MyType date\" to \"java\\.util\\.Date date\"\\. Consider to " +
                    "declare/implement a mapping method: \"java\\.util\\.Date map\\(.*MyType value\\)\"\\."),
            @Diagnostic(type = ErroneousJavaInternalMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = "Can't map property \".*MyType calendar\" to \"java\\.util\\.GregorianCalendar " +
                    "calendar\"\\. Consider to declare/implement a mapping method: \"java\\.util\\.GregorianCalendar " +
                    "map\\(.*MyType value\\)\"\\."),
            @Diagnostic(type = ErroneousJavaInternalMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = "Can't map property \".*List<.*MyType> types\" to \".*List<.*String> types\"\\" +
                    ". Consider to declare/implement a mapping method: \".*List<.*String> map\\(.*List<.*MyType> " +
                    "value\\)\"\\."),
            @Diagnostic(type = ErroneousJavaInternalMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = "Can't map property \".*List<.*MyType> nestedMyType\\.deepNestedType\\.types\" to \"" +
                    ".*List<.*String> nestedMyType\\.deepNestedType\\.types\"\\. Consider to declare/implement a " +
                    "mapping method: \".*List<.*String> map\\(.*List<.*MyType> value\\)\"\\.")
        })
    @Test
    public void shouldNotNestIntoJavaPackageObjects() throws Exception {
    }
}
