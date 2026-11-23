/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithJSpecify
class JSpecifyNullableElementArrayTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyNullableElementArrayMapper.class
    })
    void nullableElementsAreGuardedPerElement() {
        generatedSource.addComparisonToFixtureFor( JSpecifyNullableElementArrayMapper.class );

        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        // null elements are skipped (source element is nullable), non-null elements are mapped.
        NullMarkedSourceBean[] sources = {null, source};
        NullMarkedTargetBean[] result = JSpecifyNullableElementArrayMapper.INSTANCE.map( sources );

        assertThat( result ).hasSize( 2 );
        assertThat( result[0] ).isNull();
        assertThat( result[1] ).isNotNull();
        assertThat( result[1].getNonNullByDefault() ).isEqualTo( "value" );

        NullMarkedTargetBean[][] result2dim = JSpecifyNullableElementArrayMapper.INSTANCE.map2dim(
                new NullMarkedSourceBean[][]{sources} );

        assertThat( result2dim ).hasDimensions( 1,  2 );
    }
}
