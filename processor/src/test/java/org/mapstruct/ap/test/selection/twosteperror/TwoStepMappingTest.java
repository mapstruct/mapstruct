/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.twosteperror;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

public class TwoStepMappingTest {

    @ProcessorTest
    @WithClasses(ErroneousMapperMM.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            kind = javax.tools.Diagnostic.Kind.ERROR,
            type = ErroneousMapperMM.class,
            line = 16,
            message = "Ambiguous 2step methods found, mapping SourceType s to TargetType. " +
                "Found methodY( methodX ( parameter ) ): " +
                "method(s)Y: ErroneousMapperMM.TargetType methodY1(ErroneousMapperMM.TypeInTheMiddleA s), " +
                "methodX: ErroneousMapperMM.TypeInTheMiddleA methodX1(ErroneousMapperMM.SourceType s); " +
                "method(s)Y: ErroneousMapperMM.TargetType methodY2(ErroneousMapperMM.TypeInTheMiddleB s), " +
                "methodX: ErroneousMapperMM.TypeInTheMiddleB methodX2(ErroneousMapperMM.SourceType s); ."
        ))
    public void methodAndMethodTest() {
    }

    @ProcessorTest
    @WithClasses( ErroneousMapperCM.class )
    @ExpectedCompilationOutcome( value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperCM.class,
                line = 18,
                message = "Ambiguous 2step methods found, mapping BigDecimal s to TargetType. " +
                    "Found methodY( conversionX ( parameter ) ): " +
                    "method(s)Y: ErroneousMapperCM.TargetType methodY1(String s), conversionX: BigDecimal-->String; " +
                    "method(s)Y: ErroneousMapperCM.TargetType methodY2(Double d), conversionX: BigDecimal-->Double; ."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperCM.class,
                line = 18,
                messageRegExp = "Can't map property.*"
            )
        } )
    public void conversionAndMethodTest() {
    }

    @ProcessorTest
    @WithClasses( ErroneousMapperMC.class )
    @ExpectedCompilationOutcome( value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperMC.class,
                line = 18,
                message = "Ambiguous 2step methods found, mapping SourceType s to String. " +
                "Found conversionY( methodX ( parameter ) ): " +
                "conversionY: BigDecimal-->String, method(s)X: BigDecimal methodX1(ErroneousMapperMC.SourceType s); " +
                "conversionY: Double-->String, method(s)X: Double methodX2(ErroneousMapperMC.SourceType s); ."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperMC.class,
                line = 18,
                messageRegExp = "Can't map property.*"
            )
        } )
    public void methodAndConversionTest() {
    }
}
