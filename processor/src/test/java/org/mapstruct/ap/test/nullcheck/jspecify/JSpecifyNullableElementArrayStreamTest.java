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
class JSpecifyNullableElementArrayStreamTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyNullableElementArrayStreamMapper.class
    })
    void nullableElementsAreMappedToNull() {
        generatedSource.addComparisonToFixtureFor( JSpecifyNullableElementArrayStreamMapper.class );

        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        // null elements are mapped to null (set-to-null), non-null elements are mapped.
        List<NullMarkedTargetBean> result = JSpecifyNullableElementArrayStreamMapper.INSTANCE
            .mapAll( new NullMarkedSourceBean[] { null, source } )
            .collect( Collectors.toList() );

        assertThat( result ).hasSize( 2 );
        assertThat( result.get( 0 ) ).isNull();
        assertThat( result.get( 1 ) ).isNotNull();
        assertThat( result.get( 1 ).getNonNullByDefault() ).isEqualTo( "value" );
    }
}
