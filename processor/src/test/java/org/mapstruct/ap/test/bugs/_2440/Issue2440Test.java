/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2440;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2440")
@WithClasses({
    Issue2440Mapper.class
})
class Issue2440Test {

    @ProcessorTest
    void shouldCompile() {
        Issue2440Mapper.User user = new Issue2440Mapper.User();
        user.setDate( Date.from( Instant.parse( "2021-05-16T14:33:10Z" ) ) );
        user.setMoney( BigDecimal.valueOf( 120.234 ) );

        Issue2440Mapper.UserDto dto = Issue2440Mapper.MAPPER.toDto( user );

        assertThat( dto.getDateStr() ).isEqualTo( "2021-05-16" );
        assertThat( dto.getMoneyStr() ).isEqualTo( "120.23" );

    }
}
