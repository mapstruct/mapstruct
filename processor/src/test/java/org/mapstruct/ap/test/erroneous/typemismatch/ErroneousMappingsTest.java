/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.erroneous.typemismatch;

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
 * Tests failures expected for unmappable attributes.
 *
 * @author Gunnar Morling
 */
@WithClasses({ ErroneousMapper.class, Source.class, Target.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class ErroneousMappingsTest {

    @Test
    @IssueKey("6")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 27,
                messageRegExp = "Can't map property \"boolean foo\" to \"int foo\". Consider to declare/implement a "
                        + "mapping method: \"int map\\(boolean value\\)\"."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 29,
                messageRegExp = "Can't map property \"int foo\" to \"boolean foo\". Consider to declare/implement a "
                        + "mapping method: \"boolean map\\(int value\\)\"."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 31,
                messageRegExp = "Can't generate mapping method with primitive return type\\."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 33,
                messageRegExp = "Can't generate mapping method with primitive parameter type\\."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 35,
                messageRegExp =
                    "Can't generate mapping method that has a parameter annotated with @TargetType\\.")
        }
    )
    public void shouldFailToGenerateMappings() {
    }
}
