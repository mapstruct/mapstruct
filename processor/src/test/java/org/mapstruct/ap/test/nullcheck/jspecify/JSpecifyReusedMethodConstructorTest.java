/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.Compiler;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("4081")
@WithJSpecify
@WithClasses({
    ReusedConstructorSource.class,
    ReusedConstructorTarget.class,
    ReusedSourceValue.class,
    ReusedTargetValue.class
})
class JSpecifyReusedMethodConstructorTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest(Compiler.JDK)
    @WithClasses(JSpecifyNullableInputNonNullReturnConstructorMapper.class)
    void nullableInputNonNullReturnHelperSatisfiesConstructor() {
        generatedSource.addComparisonToFixtureFor( JSpecifyNullableInputNonNullReturnConstructorMapper.class );

        ReusedConstructorSource source = new ReusedConstructorSource();
        ReusedConstructorTarget target =
            JSpecifyNullableInputNonNullReturnConstructorMapper.INSTANCE.map( source );

        assertThat( target.getValue().value() ).isEqualTo( "fallback" );
    }

    @ProcessorTest(Compiler.JDK)
    @WithClasses(ErroneousJSpecifyNullableReturnConstructorMapper.class)
    @IssueKey("4081")
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousJSpecifyNullableReturnConstructorMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                message = "Can't map potentially nullable source property \"value\" to @NonNull "
                    + "constructor parameter \"value\". Consider adding a defaultValue or defaultExpression.")
        })
    void nullableHelperResultCannotSatisfyConstructor() {
    }
}
