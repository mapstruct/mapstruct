/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Positive counterpart to {@link JSpecifyMapMethodTest}: with a {@code @Nullable} return type plus
 * {@code NullValueMappingStrategy.RETURN_DEFAULT}, {@code SourceMethod#getNullability} forces a
 * {@code (NON_NULL, HARDCODED)} method-level nullability (the MAP_MAPPING branch). The generated
 * implementation must NOT carry a {@code @Nullable} return annotation, and mapping of a null source
 * must yield an empty map rather than {@code null}.
 */
@IssueKey("1243")
@WithJSpecify
class JSpecifyReturnDefaultMapTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyReturnDefaultMapMapper.class
    })
    void returnDefaultForcesNonNullableReturn() {
        generatedSource.addComparisonToFixtureFor( JSpecifyReturnDefaultMapMapper.class );

        // RETURN_DEFAULT -> mapAll(null) must return an empty map, not null
        assertThat( JSpecifyReturnDefaultMapMapper.INSTANCE.mapAll( null ) ).isEmpty();

        // Sanity: non-null input still maps correctly
        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        Map<String, NullMarkedTargetBean> targets =
            JSpecifyReturnDefaultMapMapper.INSTANCE.mapAll( Collections.singletonMap( "key", source ) );

        assertThat( targets ).hasSize( 1 );
        assertThat( targets.get( "key" ).getNonNullByDefault() ).isEqualTo( "value" );
    }
}
