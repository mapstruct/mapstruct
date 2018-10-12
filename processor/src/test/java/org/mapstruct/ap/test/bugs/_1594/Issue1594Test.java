/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1594;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1594")
@WithClasses({
    Issue1594Mapper.class
})
public class Issue1594Test {

    @Test
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
