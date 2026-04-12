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

/**
 * Tests for JSpecify nullness annotations with update methods ({@code @MappingTarget}).
 *
 * @author Filip Hrisafov
 */
@IssueKey("1243")
@WithClasses({
    SourceBean.class,
    TargetBean.class,
    JSpecifyUpdateMapper.class
})
@WithJSpecify
public class JSpecifyUpdateTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void verifyGeneratedCode() {
        generatedSource.addComparisonToFixtureFor( JSpecifyUpdateMapper.class );
    }

    @ProcessorTest
    public void sourceNonNullSkipsNullCheckInUpdateMethod() {
        // Source @NonNull getter -> no null check, setter always called
        SourceBean source = new SourceBean();
        source.setNonNullValue( "updated" );

        TargetBean target = new TargetBean();
        JSpecifyUpdateMapper.INSTANCE.update( source, target );

        assertThat( target.isNonNullTargetSet() ).isTrue();
        assertThat( target.getNonNullTarget() ).isEqualTo( "updated" );
    }

    @ProcessorTest
    public void sourceNullableWithIgnoreStrategyKeepsNullCheck() {
        // Source @Nullable + NVPMS=IGNORE -> null check must be kept (safety guard)
        // When source is null, target should be unchanged (IGNORE behavior)
        SourceBean source = new SourceBean();
        // nullableValue is null

        TargetBean target = new TargetBean();
        target.setNullableTarget( "original" );

        JSpecifyUpdateMapper.INSTANCE.update( source, target );

        // IGNORE strategy: null source should not overwrite -> original value preserved
        assertThat( target.getNullableTarget() ).isEqualTo( "original" );
    }

    @ProcessorTest
    public void sourceNullableTargetNonNullWithIgnoreStrategyKeepsNullCheck() {
        // Source @Nullable + Target @NonNull + NVPMS=IGNORE -> null check kept
        SourceBean source = new SourceBean();
        // nullableValue is null

        TargetBean target = new TargetBean();
        target.setNonNullTargetFromNullable( "original" );

        JSpecifyUpdateMapper.INSTANCE.update( source, target );

        // null source should not overwrite (IGNORE + null check)
        assertThat( target.getNonNullTargetFromNullable() ).isEqualTo( "original" );
    }
}
