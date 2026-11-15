/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithJSpecify
class JSpecifyStreamToNonNullElementArrayTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyStreamToNonNullElementArrayMapper.class
    })
    void nullElementsAreFilteredOut() {
        generatedSource.addComparisonToFixtureFor( JSpecifyStreamToNonNullElementArrayMapper.class );

        // a null stream is mapped to an empty array (the non-null array return must never be null)
        assertThat( JSpecifyStreamToNonNullElementArrayMapper.INSTANCE.mapAll( null ) )
            .isNotNull()
            .isEmpty();

        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        // the target array does not accept null as an element value, so null source elements
        // are filtered out instead of being mapped to null
        NullMarkedTargetBean[] result = JSpecifyStreamToNonNullElementArrayMapper.INSTANCE
            .mapAll( Stream.of( null, source, null ) );

        assertThat( result ).hasSize( 1 );
        assertThat( result[0].getNonNullByDefault() ).isEqualTo( "value" );
    }
}
