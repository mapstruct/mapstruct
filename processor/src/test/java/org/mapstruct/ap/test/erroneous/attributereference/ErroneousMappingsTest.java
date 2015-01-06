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
package org.mapstruct.ap.test.erroneous.attributereference;

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
 * Test for using unknown attributes in {@code @Mapping}.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Source.class, Target.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class ErroneousMappingsTest {

    @Test
    @IssueKey("11")
    @WithClasses( { ErroneousMapper.class, AnotherTarget.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 29,
                messageRegExp = "No property named \"bar\" exists in source parameter\\(s\\)"),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 30,
                messageRegExp = "No property named \"source1.foo\" exists in source parameter\\(s\\)"),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 31,
                messageRegExp = "Unknown property \"bar\" in return type"),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 33,
                messageRegExp = "Target property \"foo\" must not be mapped more than once"),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.WARNING,
                line = 35,
                messageRegExp = "Unmapped target property: \"bar\"")
        }
    )
    public void shouldFailToGenerateMappings() {
    }

    @Test
    @WithClasses( { ErroneousMapper1.class, DummySource.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper1.class,
                kind = Kind.ERROR,
                line = 29,
                messageRegExp = "The type of parameter \"source\" has no property named \"foobar\"")
        }
    )
    public void shouldFailToGenerateMappingsErrorOnMandatoryParameterName() {
    }

    @Test
    @WithClasses( { ErroneousMapper2.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper2.class,
                kind = Kind.ERROR,
                line = 32,
                messageRegExp = "Target property \"foo\" must not be mapped more than once" )
        }
    )
    public void shouldFailToGenerateMappingsErrorOnDuplicateTarget() {
    }
}
