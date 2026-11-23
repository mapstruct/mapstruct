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
 * A nested source property of array type has to keep the component nullability of the array property.
 */
@WithJSpecify
@WithClasses( {
    NestedArraySource.class,
    NestedArrayTarget.class,
    NestedArrayMapper.class
} )
class NestedArrayTest {

    @ProcessorTest
    void shouldMapNestedArrayProperty() {
        NestedArraySource source = new NestedArraySource(
            new NestedArraySource.Child( new String[] { "1", "2" } )
        );

        NestedArrayTarget target = NestedArrayMapper.INSTANCE.map( source );

        assertThat( target.getIds() ).containsExactly( 1, 2 );
    }
}
