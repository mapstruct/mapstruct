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
package org.mapstruct.ap.test.namesuggestion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.namesuggestion.erroneous.PersonAgeMapper;
import org.mapstruct.ap.test.namesuggestion.erroneous.PersonGarageWrongSourceMapper;
import org.mapstruct.ap.test.namesuggestion.erroneous.PersonGarageWrongTargetMapper;
import org.mapstruct.ap.test.namesuggestion.erroneous.PersonNameMapper;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Person.class, PersonDto.class, Garage.class, GarageDto.class, ColorRgb.class, ColorRgbDto.class
})
public class SuggestMostSimilarNameTest {

    @Test
    @WithClasses({
        PersonAgeMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = PersonAgeMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 32,
                messageRegExp = ".*Did you mean \"age\"\\?")
        }
    )
    public void testAgeSuggestion() {
    }

    @Test
    @WithClasses({
        PersonNameMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = PersonNameMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 32,
                messageRegExp = ".*Did you mean \"fullName\"\\?")
        }
    )
    public void testNameSuggestion() {
    }

    @Test
    @WithClasses({
        PersonGarageWrongTargetMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = PersonGarageWrongTargetMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 32,
                messageRegExp = "Unknown property \"garage\\.colour\\.rgb\".*Did you mean \"garage\\.color\"\\?"),
            @Diagnostic(type = PersonGarageWrongTargetMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 35,
                messageRegExp = "Unknown property \"garage\\.colour\".*Did you mean \"garage\\.color\"\\?")
        }
    )
    public void testGarageTargetSuggestion() {
    }

    @Test
    @WithClasses({
        PersonGarageWrongSourceMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = PersonGarageWrongSourceMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 32,
                messageRegExp = "No property named \"garage\\.colour\\.rgb\".*Did you mean \"garage\\.color\"\\?"),
            @Diagnostic(type = PersonGarageWrongSourceMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 35,
                messageRegExp = "No property named \"garage\\.colour\".*Did you mean \"garage\\.color\"\\?")
        }
    )
    public void testGarageSourceSuggestion() {
    }
}
