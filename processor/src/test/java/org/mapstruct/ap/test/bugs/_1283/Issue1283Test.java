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
package org.mapstruct.ap.test.bugs._1283;

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
@IssueKey("1283")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Source.class,
    Target.class
})
public class Issue1283Test {

    @Test
    @WithClasses(ErroneousInverseTargetHasNoSuitableConstructorMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousInverseTargetHasNoSuitableConstructorMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 35L,
                messageRegExp = ".*\\._1283\\.Source does not have an accessible empty constructor"
            )
        }
    )
    public void inheritInverseConfigurationReturnTypeHasNoSuitableConstructor() {
    }

    @Test
    @WithClasses(ErroneousTargetHasNoSuitableConstructorMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousTargetHasNoSuitableConstructorMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 31L,
                messageRegExp = ".*\\._1283\\.Source does not have an accessible empty constructor"
            )
        }
    )
    public void returnTypeHasNoSuitableConstructor() {
    }
}
