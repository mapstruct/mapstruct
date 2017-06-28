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
package org.mapstruct.ap.test.bugs._1029;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Verifies that read-only properties can be explicitly mentioned as {@code ignored=true} without raising an error.
 *
 * @author Andreas Gudian
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses(ErroneousIssue1029Mapper.class)
@IssueKey("1029")
public class Issue1029Test {

    @Test
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic(kind = Kind.WARNING, line = 39, type = ErroneousIssue1029Mapper.class,
            messageRegExp = "Unmapped target properties: \"knownProp, lastUpdated, computedMapping\"\\."),
        @Diagnostic(kind = Kind.WARNING, line = 50, type = ErroneousIssue1029Mapper.class,
            messageRegExp = "Unmapped target property: \"lastUpdated\"\\."),
        @Diagnostic(kind = Kind.ERROR, line = 55, type = ErroneousIssue1029Mapper.class,
            messageRegExp = "Unknown property \"unknownProp\" in result type " +
                "org.mapstruct.ap.test.bugs._1029.ErroneousIssue1029Mapper.Deck\\. Did you mean \"knownProp\"?")
    })
    public void reportsProperWarningsAndError() {
    }
}
