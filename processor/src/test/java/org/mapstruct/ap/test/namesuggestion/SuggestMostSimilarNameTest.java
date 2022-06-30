/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.namesuggestion;

import org.mapstruct.ap.test.namesuggestion.erroneous.PersonAgeMapper;
import org.mapstruct.ap.test.namesuggestion.erroneous.PersonGarageWrongSourceMapper;
import org.mapstruct.ap.test.namesuggestion.erroneous.PersonGarageWrongTargetMapper;
import org.mapstruct.ap.test.namesuggestion.erroneous.PersonNameMapper;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

@WithClasses({
    Person.class, PersonDto.class, Garage.class, GarageDto.class, ColorRgb.class, ColorRgbDto.class
})
public class SuggestMostSimilarNameTest {

    @ProcessorTest
    @WithClasses({
        PersonAgeMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = PersonAgeMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "No property named \"agee\" exists in source parameter(s). Did you mean \"age\"?")
        }
    )
    public void testAgeSuggestion() {
    }

    @ProcessorTest
    @WithClasses({
        PersonNameMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = PersonNameMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "Unknown property \"fulName\" in result type Person. Did you mean \"fullName\"?")
        }
    )
    public void testNameSuggestion() {
    }

    @ProcessorTest
    @WithClasses({
        PersonGarageWrongTargetMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = PersonGarageWrongTargetMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "Unknown property \"colour\" in type Garage for target name \"garage.colour.rgb\". " +
                    "Did you mean \"garage.color\"?"),
            @Diagnostic(type = PersonGarageWrongTargetMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 20,
                messageRegExp = "Unmapped target properties: \"fullName, fullAge\"\\."),
            @Diagnostic(type = PersonGarageWrongTargetMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                message = "Unknown property \"colour\" in type Garage for target name \"garage.colour\". " +
                    "Did you mean \"garage.color\"?"),
            @Diagnostic(type = PersonGarageWrongTargetMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 23,
                messageRegExp = "Unmapped target properties: \"fullName, fullAge\"\\."),
        }
    )
    public void testGarageTargetSuggestion() {
    }

    @ProcessorTest
    @WithClasses({
        PersonGarageWrongSourceMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = PersonGarageWrongSourceMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 21,
                message = "No property named \"garage.colour.rgb\" exists in source parameter(s). Did you mean " +
                    "\"garage.color\"?"),
            @Diagnostic(type = PersonGarageWrongSourceMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 28,
                message = "No property named \"garage.colour\" exists in source parameter(s). Did you mean \"garage" +
                    ".color\"?")
        }
    )
    public void testGarageSourceSuggestion() {
    }
}
