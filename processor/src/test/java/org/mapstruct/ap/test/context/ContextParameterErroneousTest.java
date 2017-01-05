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
package org.mapstruct.ap.test.context;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.Context;
import org.mapstruct.ap.test.context.erroneous.ErroneousNodeMapperWithNonUniqueContextTypes;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Tests the erroneous usage of the {@link Context} annotation in the following situations:
 * <ul>
 * <li>using the the same context parameter type twice in the same method
 * </ul>
 *
 * @author Andreas Gudian
 */
@IssueKey("975")
@WithClasses({
    Node.class,
    NodeDto.class,
    CycleContext.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class ContextParameterErroneousTest {

    @Test
    @WithClasses(ErroneousNodeMapperWithNonUniqueContextTypes.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            kind = Kind.ERROR,
            line = 33,
            type = ErroneousNodeMapperWithNonUniqueContextTypes.class,
            messageRegExp = "The types of @Context parameters must be unique"))
    public void reportsNonUniqueContextParamType() {
    }
}
