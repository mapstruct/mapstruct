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
 * Positive counterpart to {@link JSpecifyNullableSourceIterableTest}: with a
 * {@code @Nullable} return type plus
 * {@code NullValueMappingStrategy.RETURN_DEFAULT}, {@code SourceMethod#getNullability} forces
 * a {@code (NON_NULL, HARDCODED)} method-level nullability. The generated implementation must
 * NOT carry a {@code @Nullable} return annotation, and {@code mapAll(null)} must return an
 * empty list rather than {@code null}.
 */
@IssueKey("1243")
@WithJSpecify
class JSpecifyReturnDefaultIterableTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyReturnDefaultIterableMapper.class
    })
    void returnDefaultForcesNonNullableReturn() {
        generatedSource.addComparisonToFixtureFor( JSpecifyReturnDefaultIterableMapper.class );

        // RETURN_DEFAULT -> mapAll(null) must return an empty list, not null
        assertThat( JSpecifyReturnDefaultIterableMapper.INSTANCE.mapAll( null ) ).isEmpty();

        // Sanity: non-null input still maps correctly
        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        List<NullMarkedTargetBean> targets =
            JSpecifyReturnDefaultIterableMapper.INSTANCE.mapAll( Arrays.asList( source ) );

        assertThat( targets ).hasSize( 1 );
        assertThat( targets.get( 0 ).getNonNullByDefault() ).isEqualTo( "value" );
    }
}
