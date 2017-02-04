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
package org.mapstruct.ap.test.erroneous.misbalancedbraces;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.DisableCheckstyle;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for making sure that expressions with too many closing braces are passed through, letting the compiler raise an
 * error.
 *
 * @author Gunnar Morling
 */
@WithClasses({ MapperWithMalformedExpression.class, Source.class, Target.class })
@DisableCheckstyle
@RunWith(AnnotationProcessorTestRunner.class)
public class MisbalancedBracesTest {

    // the compiler messages due to the additional closing brace differ between JDK and Eclipse, hence we can only
    // assert on the line number but not the message
    @Test
    @IssueKey("1056")
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = { @Diagnostic(kind = Kind.ERROR, line = 20) }
    )
    public void expressionWithMisbalancedBracesIsPassedThrough() {
    }
}
