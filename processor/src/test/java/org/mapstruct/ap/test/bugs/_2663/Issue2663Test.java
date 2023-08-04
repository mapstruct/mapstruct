/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2663;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey( "2663" )
@WithClasses({
    Issue2663Mapper.class,
    JsonNullable.class,
    Nullable.class,
    NullableHelper.class,
    Request.class,
    RequestDto.class
})
public class Issue2663Test {

    @ProcessorTest
    public void shouldUnpackGenericsCorrectly() {
        RequestDto dto = new RequestDto();
        dto.setName( JsonNullable.of( "Tester" ) );

        Request request = Issue2663Mapper.INSTANCE.map( dto );

        assertThat( request.getName() )
            .extracting( Nullable::get )
            .isEqualTo( "Tester" );

        dto.setName( JsonNullable.undefined() );

        request = Issue2663Mapper.INSTANCE.map( dto );

        assertThat( request.getName() )
            .extracting( Nullable::isPresent )
            .isEqualTo( false );
    }
}
