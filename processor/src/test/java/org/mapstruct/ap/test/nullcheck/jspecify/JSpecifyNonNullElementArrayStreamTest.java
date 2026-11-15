/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithJSpecify
class JSpecifyNonNullElementArrayStreamTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyNonNullElementArrayStreamMapper.class
    })
    void nullableArrayIsMappedToEmptyStream() {
        generatedSource.addComparisonToFixtureFor( JSpecifyNonNullElementArrayStreamMapper.class );

        // a null array source is mapped to an empty stream (the elements are non-null, so no per-element check)
        assertThat( JSpecifyNonNullElementArrayStreamMapper.INSTANCE.mapAll( null ) ).isNotNull();
        assertThat( JSpecifyNonNullElementArrayStreamMapper.INSTANCE.mapAll( null ).collect( Collectors.toList() ) )
            .isEmpty();

        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        List<NullMarkedTargetBean> result = JSpecifyNonNullElementArrayStreamMapper.INSTANCE
            .mapAll( new NullMarkedSourceBean[] { source } )
            .collect( Collectors.toList() );

        assertThat( result ).hasSize( 1 );
        assertThat( result.get( 0 ).getNonNullByDefault() ).isEqualTo( "value" );
    }
}
