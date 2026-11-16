/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Negative counterpart to {@link JSpecifyIterableMethodTest}: a {@code @Nullable} source parameter (and a
 * {@code @Nullable} return, so the @NonNull-return forcing does not kick in) must keep the method-level
 * {@code if ( sources == null ) return null;} guard.
 */
@IssueKey("1243")
@WithJSpecify
class JSpecifyNullableSourceIterableTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyNullableSourceIterableMapper.class
    })
    void nullableSourceKeepsMethodGuard() {
        generatedSource.addComparisonToFixtureFor( JSpecifyNullableSourceIterableMapper.class );

        assertThat( JSpecifyNullableSourceIterableMapper.INSTANCE.mapAll( null ) ).isNull();

        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        List<NullMarkedTargetBean> targets =
            JSpecifyNullableSourceIterableMapper.INSTANCE.mapAll( Arrays.asList( source ) );

        assertThat( targets ).hasSize( 1 );
        assertThat( targets.get( 0 ).getNonNullByDefault() ).isEqualTo( "value" );
    }
}
