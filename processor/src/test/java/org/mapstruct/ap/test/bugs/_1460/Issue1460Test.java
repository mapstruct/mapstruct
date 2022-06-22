/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1460;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithJoda;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Christian Bandowski
 */
@WithClasses({
    Issue1460Mapper.class,
    Source.class,
    Target.class
})
@IssueKey("1460")
@WithJoda
public class Issue1460Test {

    @ProcessorTest
    public void shouldTestMappingLocalDates() {
        long dateInMs = 1524693600000L;
        String dateAsString = "2018-04-26";
        String dateTimeAsString = dateAsString + "T00:00:00+00:00";

        Source source = new Source();
        source.setStringToEnum( "OK" );
        source.setDateToJodaDateTime( new Date( dateInMs ) );
        source.setJodaDateTimeToCalendar( DateTime.parse( dateTimeAsString ) );

        Target target = Issue1460Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringToEnum() ).isEqualTo( Target.Issue1460Enum.OK );
        assertThat( target.getDateToJodaDateTime() ).isEqualTo(
            new DateTime( new Date( dateInMs ) )
        );
        assertThat( target.getJodaDateTimeToCalendar().getTimeInMillis() ).isEqualTo(
            DateTime.parse( dateTimeAsString ).toCalendar( Locale.getDefault() ).getTimeInMillis()
        );
    }
}
