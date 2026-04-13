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
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for JSpecify nullness annotations with constructor mappings.
 *
 * @author Filip Hrisafov
 */
@IssueKey("1243")
@WithClasses({
    SourceBean.class,
    ConstructorTargetBean.class
})
@WithJSpecify
class JSpecifyConstructorTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses(JSpecifyConstructorMapper.class)
    public void verifyGeneratedCode() {
        generatedSource.addComparisonToFixtureFor( JSpecifyConstructorMapper.class );
    }

    @ProcessorTest
    @WithClasses(JSpecifyConstructorMapper.class)
    public void constructorMappingWithNonNullSource() {
        SourceBean source = new SourceBean();
        source.setNonNullValue( "value" );
        source.setNullableValue( "nullable" );
        source.setUnannotatedValue( "unannotated" );

        ConstructorTargetBean target = JSpecifyConstructorMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getNonNullParam() ).isEqualTo( "value" );
        assertThat( target.getNullableParam() ).isEqualTo( "nullable" );
        assertThat( target.getUnannotatedParam() ).isEqualTo( "unannotated" );
    }

    @ProcessorTest
    @WithClasses(ErroneousJSpecifyConstructorMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousJSpecifyConstructorMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                message = "Can't map potentially nullable source property \"nullableValue\" to @NonNull " +
                    "constructor parameter \"nonNullParam\". Consider adding a defaultValue or " +
                    "defaultExpression.")
        })
    public void nullableSourceToNonNullConstructorParamShouldFail() {
    }

    @ProcessorTest
    @WithClasses(JSpecifyDirectParamConstructorMapper.class)
    public void nonNullDirectParameterToNonNullConstructorParamCompiles() {
        // A @NonNull method parameter (source has no property entries — it IS the parameter)
        // must be accepted by a @NonNull constructor parameter without triggering the
        // "potentially nullable source" error.
        ConstructorTargetBean target = JSpecifyDirectParamConstructorMapper.INSTANCE.map(
            "nn", "nullable", "plain" );

        assertThat( target.getNonNullParam() ).isEqualTo( "nn" );
        assertThat( target.getNullableParam() ).isEqualTo( "nullable" );
        assertThat( target.getUnannotatedParam() ).isEqualTo( "plain" );
    }

    @ProcessorTest
    @WithClasses(ErroneousJSpecifyDirectParamConstructorMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousJSpecifyDirectParamConstructorMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                message = "Can't map potentially nullable source property \"nullableValue\" to @NonNull " +
                    "constructor parameter \"nonNullParam\". Consider adding a defaultValue or " +
                    "defaultExpression.")
        })
    public void nullableDirectParameterToNonNullConstructorParamShouldFail() {
    }

    @ProcessorTest
    @WithClasses(JSpecifyConstructorDefaultValueMapper.class)
    public void defaultValueSuppressesNullableToNonNullConstructorParamError() {
        SourceBean source = new SourceBean();
        source.setNonNullValue( "present" );
        // nullableValue left null on purpose — defaultValue "fallback" should kick in.

        ConstructorTargetBean target = JSpecifyConstructorDefaultValueMapper.INSTANCE.map( source );

        assertThat( target.getNonNullParam() ).isEqualTo( "fallback" );
        assertThat( target.getUnannotatedParam() ).isEqualTo( "present" );
    }
}
