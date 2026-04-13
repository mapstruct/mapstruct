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
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * Tests for JSpecify nullness annotation support in MapStruct.
 *
 * @author Filip Hrisafov
 */
@IssueKey("1243")
@WithClasses({
    SourceBean.class,
    TargetBean.class
})
@WithJSpecify
public class JSpecifyNullCheckTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    // -- Property-level tests (JSpecifyNullCheckMapper) --

    @ProcessorTest
    @WithClasses(JSpecifyNullCheckMapper.class)
    public void sourceNonNullGetterShouldSkipNullCheck() {
        generatedSource.addComparisonToFixtureFor( JSpecifyNullCheckMapper.class );

        // Source @NonNull getter -> no null check, setter always called
        SourceBean source = new SourceBean();
        source.setNonNullValue( "value" );

        TargetBean target = JSpecifyNullCheckMapper.INSTANCE.map( source );

        assertThat( target.isNonNullTargetSet() ).isTrue();
        assertThat( target.getNonNullTarget() ).isEqualTo( "value" );
    }

    @ProcessorTest
    @WithClasses(JSpecifyNullCheckMapper.class)
    public void sourceNullableTargetNonNullShouldAddNullCheck() {
        // Source @Nullable getter + Target @NonNull setter -> null check added
        // When source value is null, setter should NOT be called
        SourceBean source = new SourceBean();

        TargetBean target = JSpecifyNullCheckMapper.INSTANCE.map( source );

        assertThat( target.isNonNullTargetFromNullableSet() ).isFalse();
    }

    @ProcessorTest
    @WithClasses(JSpecifyNullCheckMapper.class)
    public void sourceNullableTargetNullableDefersToStrategy() {
        // Source @Nullable + Target @Nullable -> defers to NullValueCheckStrategy
        // Default NVCS is ON_IMPLICIT_CONVERSION, direct String->String has no null check
        SourceBean source = new SourceBean();

        TargetBean target = JSpecifyNullCheckMapper.INSTANCE.map( source );

        // With ON_IMPLICIT_CONVERSION, no null check for direct assignment -> setter called
        assertThat( target.isNullableTargetSet() ).isTrue();
    }

    // -- JSpecify overrides NullValueCheckStrategy --

    @ProcessorTest
    @WithClasses(JSpecifyOverridesStrategyMapper.class)
    public void jspecifyOverridesNullValueCheckStrategyAlways() {
        generatedSource.addComparisonToFixtureFor( JSpecifyOverridesStrategyMapper.class );

        // Even with NullValueCheckStrategy.ALWAYS, source @NonNull should skip null check
        SourceBean source = new SourceBean();
        source.setNonNullValue( "value" );

        TargetBean target = JSpecifyOverridesStrategyMapper.INSTANCE.map( source );

        assertThat( target.isNonNullTargetSet() ).isTrue();
    }

    // -- Method-level parameter null check --

    @ProcessorTest
    @WithClasses(JSpecifyNonNullParamMapper.class)
    public void nonNullParamShouldSkipMethodLevelNullGuard() {
        generatedSource.addComparisonToFixtureFor( JSpecifyNonNullParamMapper.class );

        // Non-null input: mapper maps through normally.
        SourceBean source = new SourceBean();
        source.setNonNullValue( "value" );
        source.setUnannotatedValue( "plain" );

        TargetBean target = JSpecifyNonNullParamMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getNonNullTarget() ).isEqualTo( "value" );
        assertThat( target.getUnannotatedTarget() ).isEqualTo( "plain" );

        // Null input: because the method-level guard was skipped, the mapper must dereference
        // the source and throw NPE rather than silently returning null.
        assertThatNullPointerException().isThrownBy( () -> JSpecifyNonNullParamMapper.INSTANCE.map( null ) );
    }

    @ProcessorTest
    @WithClasses(JSpecifyNullCheckMapper.class)
    public void unannotatedParamShouldHaveMethodLevelNullGuard() {
        // Unannotated source parameter -> normal "if (source == null) return null" guard
        SourceBean source = new SourceBean();

        TargetBean target = JSpecifyNullCheckMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
    }
}
