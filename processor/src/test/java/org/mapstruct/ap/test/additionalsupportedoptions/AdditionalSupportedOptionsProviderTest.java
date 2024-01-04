/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.additionalsupportedoptions;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.ap.spi.EnumMappingStrategy;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.runner.Compiler;

import static org.assertj.core.api.Assertions.assertThat;

@Execution( ExecutionMode.CONCURRENT )
public class AdditionalSupportedOptionsProviderTest {

    @ProcessorTest
    @WithClasses({
        Pet.class,
        PetWithMissing.class,
        UnknownEnumMappingStrategyMapper.class
    })
    @WithServiceImplementation(CustomAdditionalSupportedOptionsProvider.class)
    @WithServiceImplementation(value = UnknownEnumMappingStrategy.class, provides = EnumMappingStrategy.class)
    @ProcessorOption(name = "myorg.custom.defaultNullEnumConstant", value = "MISSING")
    public void shouldUseConfiguredPrefix() {
        assertThat( UnknownEnumMappingStrategyMapper.INSTANCE.map( null ) )
            .isEqualTo( PetWithMissing.MISSING );
    }

    @ProcessorTest(Compiler.JDK) // The eclipse compiler does not parse the error message properly
    @WithClasses({
        EmptyMapper.class
    })
    @WithServiceImplementation(InvalidAdditionalSupportedOptionsProvider.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            kind = javax.tools.Diagnostic.Kind.ERROR,
            messageRegExp = "Additional SPI options cannot start with \"mapstruct\". Provider " +
                "org.mapstruct.ap.test.additionalsupportedoptions.InvalidAdditionalSupportedOptionsProvider@.*" +
                " provided option mapstruct.custom.test"
        )
    )
    public void shouldFailWhenOptionsProviderUsesMapstructPrefix() {
    }

}
