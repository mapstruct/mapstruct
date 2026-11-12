/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time;

import java.time.OffsetDateTime;
import java.time.OffsetTime;

import org.mapstruct.ap.test.conversion.java8time.OffsetMapper.TextValues;
import org.mapstruct.ap.test.conversion.java8time.OffsetMapper.TimeValues;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("4154")
@WithClasses(OffsetMapper.class)
public class OffsetTimeConversionTest {

    @ProcessorTest
    public void convertsOffsetTypesUsingIsoAndCustomFormats() {
        OffsetMapper mapper = Mappers.getMapper( OffsetMapper.class );
        TimeValues input = new TimeValues();
        input.dateTime = OffsetDateTime.parse( "2026-11-12T14:35:42+05:30" );
        input.time = OffsetTime.parse( "14:35:42+05:30" );

        TextValues iso = mapper.toText( input );
        assertThat( iso.dateTime ).isEqualTo( "2026-11-12T14:35:42+05:30" );
        assertThat( iso.time ).isEqualTo( "14:35:42+05:30" );
        assertThat( mapper.fromText( iso ) ).usingRecursiveComparison().isEqualTo( input );

        TextValues formatted = mapper.toFormattedText( input );
        assertThat( formatted.dateTime ).isEqualTo( "12/11/2026 14:35:42 +0530" );
        assertThat( formatted.time ).isEqualTo( "14.35.42 +0530" );
        assertThat( mapper.fromFormattedText( formatted ) ).usingRecursiveComparison().isEqualTo( input );
    }
}
