/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.arraycomponent;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The nullability of each array dimension has to be applied to that dimension, and not to another one.
 */
@WithJSpecify
@WithClasses( NullableRowArrayMapper.class )
class NullableRowArrayTest {

    @ProcessorTest
    void shouldNullCheckNullableRows() {
        Integer[][] result = NullableRowArrayMapper.INSTANCE.map( new String[][] { { "1", "2" }, null } );

        assertThat( result ).hasNumberOfRows( 2 );
        assertThat( result[0] ).containsExactly( 1, 2 );
        assertThat( result[1] ).isNull();
    }

    @ProcessorTest
    void shouldNullCheckNullablePrimitiveRows() {
        long[][] result = NullableRowArrayMapper.INSTANCE.mapPrimitive( new int[][] { { 1, 2 }, null } );

        assertThat( result ).hasNumberOfRows( 2 );
        assertThat( result[0] ).containsExactly( 1L, 2L );
        assertThat( result[1] ).isNull();
    }
}
