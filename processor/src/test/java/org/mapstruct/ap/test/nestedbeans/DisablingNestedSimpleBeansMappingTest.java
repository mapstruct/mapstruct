/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    House.class, HouseDto.class,
    Wheel.class, WheelDto.class,
    Roof.class, RoofDto.class,
    RoofType.class, ExternalRoofType.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class DisablingNestedSimpleBeansMappingTest {

    @WithClasses({
        ErroneousDisabledHouseMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousDisabledHouseMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"org.mapstruct.ap.test.nestedbeans.Roof roof\" to \"org.mapstruct.ap" +
                    ".test.nestedbeans.RoofDto roof\". Consider to declare/implement a mapping method: \"org" +
                    ".mapstruct.ap.test.nestedbeans.RoofDto map(org.mapstruct.ap.test.nestedbeans.Roof value)\"."
            )
        })
    @Test
    public void shouldUseDisabledMethodGenerationOnMapper() {
    }

    @WithClasses({
        ErroneousDisabledViaConfigHouseMapper.class,
        DisableConfig.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousDisabledViaConfigHouseMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"org.mapstruct.ap.test.nestedbeans.Roof roof\" to \"org.mapstruct.ap" +
                    ".test.nestedbeans.RoofDto roof\". Consider to declare/implement a mapping method: \"org" +
                    ".mapstruct.ap.test.nestedbeans.RoofDto map(org.mapstruct.ap.test.nestedbeans.Roof value)\"."
            )
        })
    @Test
    public void shouldUseDisabledMethodGenerationOnMapperConfig() {
    }
}
