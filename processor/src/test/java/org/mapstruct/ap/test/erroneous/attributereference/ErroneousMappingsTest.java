/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.erroneous.attributereference;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.testng.annotations.Test;

/**
 * Test for using unknown attributes in {@code @Mapping}.
 *
 * @author Gunnar Morling
 */
@WithClasses({ ErroneousMapper.class, Source.class, Target.class, AnotherTarget.class })
public class ErroneousMappingsTest extends MapperTestBase {

    @Test
    @IssueKey("11")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 27,
                messageRegExp = ".*Unknown property \"bar\" in return type.*"),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.WARNING,
                line = 28,
                messageRegExp = "Unmapped target property: \"foo\""),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 30,
                messageRegExp = ".*Unknown property \"bar\" in parameter type.*")
        }
    )
    public void shouldFailToGenerateMappings() {
    }
}
