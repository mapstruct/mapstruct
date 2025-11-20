/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.records.nested;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class NestedRecordsTest {

    @Test
    public void shouldMapRecord() {
        CareProvider source = new CareProvider( "kermit", new Address( "Sesame Street", "New York" ) );
        CareProviderDto target = CareProviderMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.id() ).isEqualTo( "kermit" );
        assertThat( target.street() ).isEqualTo( "Sesame Street" );
        assertThat( target.city() ).isEqualTo( "New York" );
    }
}
