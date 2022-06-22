/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1594;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1594")
@WithClasses({
    Issue1594Mapper.class
})
public class Issue1594Test {

    @ProcessorTest
    public void shouldGenerateCorrectMapping() {
        Issue1594Mapper.Dto dto = new Issue1594Mapper.Dto();
        dto.setFullAddress( "Switzerland-Zurich" );

        Issue1594Mapper.Client client = Issue1594Mapper.INSTANCE.toClient( dto );

        assertThat( client ).isNotNull();
        assertThat( client.getAddress() ).isNotNull();
        assertThat( client.getAddress().getCountry() ).isNotNull();
        assertThat( client.getAddress().getCountry().getOid() ).isEqualTo( "Switzerland" );
        assertThat( client.getAddress().getCity() ).isNotNull();
        assertThat( client.getAddress().getCity().getOid() ).isEqualTo( "Zurich" );

    }
}
