/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.value.string2enum;

import javax.tools.Diagnostic.Kind;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@WithJSpecify
public class NullCheckValueMappingString2EnumTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses( { OrderType.class, NullGuardSkippedNonNullMapper.class } )
    void nullGuardSkippedForNonNullParameter() {
        generatedSource.addComparisonToFixtureFor( NullGuardSkippedNonNullMapper.class );
    }

    @ProcessorTest
    @WithClasses( { OrderType.class, NullGuardSkippedNullMarkedMapper.class } )
    void nullGuardSkippedForNullMarkedMethod() {
        generatedSource.addComparisonToFixtureFor( NullGuardSkippedNullMarkedMapper.class );
    }

    @ProcessorTest
    @WithClasses( { OrderType.class, NullMappingDeadMapper.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(
                type = NullMappingDeadMapper.class,
                kind = Kind.WARNING,
                line = 17,
                alternativeLine = 20,
                message = "Source parameter can't be null so source = \"NULL\" is ignored."
            )
        }
    )
    void warningForDeadNullMappingWhenParameterIsNonNull() {
    }

    @ProcessorTest
    @WithClasses( { OrderType.class, ErroneousNonNullReturnNullTargetMapper.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                    type = ErroneousNonNullReturnNullTargetMapper.class,
                    kind = Kind.ERROR,
                    line = 17,
                    alternativeLine = 21,
                    message = "JSpecify value mapping method has a @NonNull return type but source =\"DEFAULT\" " +
                            "mapped to target = \"NULL\"."
            ),
            @Diagnostic(
                type = ErroneousNonNullReturnNullTargetMapper.class,
                kind = Kind.ERROR,
                line = 26,
                message = "JSpecify value mapping method has a @NonNull return type but the implementation can " +
                        "return null. Either define a default value for null with source = \"NULL\" or implement a " +
                        "custom EnumMappingStrategy."
            )
        }
    )
    void errorWhenNonNullReturnYieldsNullFromExplicitNullTarget() {
    }
}
