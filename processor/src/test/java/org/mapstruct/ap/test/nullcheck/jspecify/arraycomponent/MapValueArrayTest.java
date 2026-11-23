/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.arraycomponent;

import java.util.Collections;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * An array that is read from a map value has no resolved component nullability.
 * The processor must still be able to generate the element mapping.
 */
@WithClasses( MapValueArrayMapper.class )
class MapValueArrayTest {

    @ProcessorTest
    void shouldMapArrayFromMapValue() {
        MapValueArrayMapper.Target target = MapValueArrayMapper.INSTANCE.map(
            Collections.singletonMap( "ids", new String[] { "1", null, "3" } )
        );

        assertThat( target.getIds() ).containsExactly( 1, null, 3 );
    }
}
