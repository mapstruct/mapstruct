/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.mapkey;

import java.util.HashMap;
import java.util.Map;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@WithClasses(NullMapKeyMapper.class)
class NullMapKeyTest {

    @ProcessorTest
    void entriesWithNullKeyOrValueAreKept() {
        Map<Integer, Integer> source = new HashMap<>();
        source.put( null, 1 );
        source.put( 2, null );
        source.put( 3, 3 );

        Map<String, String> target = NullMapKeyMapper.INSTANCE.map( source );

        assertThat( target ).containsOnly(
            entry( null, "1" ),
            entry( "2", null ),
            entry( "3", "3" )
        );
    }
}
