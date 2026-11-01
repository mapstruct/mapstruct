/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Positive counterpart to {@link JSpecifyStreamMethodTest}: with a {@code @Nullable} return
 * type plus {@code NullValueMappingStrategy.RETURN_DEFAULT}, {@code SourceMethod#getNullability}
 * forces a {@code (NON_NULL, HARDCODED)} method-level nullability (the STREAM_MAPPING branch). The
 * generated implementation must NOT carry a {@code @Nullable} return annotation, and mapping of a
 * null source must yield an empty stream rather than {@code null}.
 */
@IssueKey("1243")
@WithJSpecify
class JSpecifyReturnDefaultStreamTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyReturnDefaultStreamMapper.class
    })
    void returnDefaultForcesNonNullableReturn() {
        generatedSource.addComparisonToFixtureFor( JSpecifyReturnDefaultStreamMapper.class );

        // RETURN_DEFAULT -> mapAll(null) must return an empty stream, not null
        List<NullMarkedTargetBean> fromNull =
            JSpecifyReturnDefaultStreamMapper.INSTANCE.mapAll( null ).collect( Collectors.toList() );
        assertThat( fromNull ).isEmpty();

        // Sanity: non-null input still maps correctly
        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        List<NullMarkedTargetBean> targets = JSpecifyReturnDefaultStreamMapper.INSTANCE
            .mapAll( Stream.of( source ) )
            .collect( Collectors.toList() );

        assertThat( targets ).hasSize( 1 );
        assertThat( targets.get( 0 ).getNonNullByDefault() ).isEqualTo( "value" );
    }
}
