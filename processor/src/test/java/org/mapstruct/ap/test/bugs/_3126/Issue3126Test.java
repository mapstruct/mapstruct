/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3126;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3126")
@WithClasses(Issue3126Mapper.class)
class Issue3126Test {

    @ProcessorTest
    void shouldCompile() {
        Issue3126Mapper.Auditable auditable = new Issue3126Mapper.Auditable( "home-user" );
        Issue3126Mapper.Address address = new Issue3126Mapper.HomeAddress( auditable );
        Issue3126Mapper.AddressDto addressDto = Issue3126Mapper.INSTANCE.map( address );

        assertThat( addressDto ).isInstanceOfSatisfying( Issue3126Mapper.HomeAddressDto.class, homeAddress -> {
            assertThat( homeAddress.getCreatedBy() ).isEqualTo( "home-user" );
        } );
    }
}
