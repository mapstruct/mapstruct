/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1523.java8;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test will evaluate if the conversion from {@code Calendar} to {@code XMLGregorianCalendar} works in case the
 * default timezone was not used. Additionally a direct conversion between {@code ZonedDateTime} to
 * {@code XMLGregorianCalendar} was added for this issue to improve readability / performance. This will be tested as
 * well.
 *
 * @author Christian Bandowski
 */
@WithClasses({
    Issue1523Mapper.class,
    Source.class,
    Target.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1523")
public class Issue1523Test {

    private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getDefault();

    @BeforeClass
    public static void before() {
        // we want to test that the timezone will correctly be used in mapped XMLGregorianCalendar and not the
        // default one, so we must ensure that we use a different timezone than the default one -> set the default
        // one explicitly to UTC
        TimeZone.setDefault( TimeZone.getTimeZone( "UTC" ) );
    }

    @AfterClass
    public static void after() {
        // revert the changed default TZ
        TimeZone.setDefault( DEFAULT_TIMEZONE );
    }

    @Test
    public void testThatCorrectTimeZoneWillBeUsedInTarget() {
        Source source = new Source();
        // default one was explicitly set to UTC, thus +01:00 is a different one
        source.setValue( ZonedDateTime.parse( "2018-06-15T00:00:00+01:00" ) );
        Calendar cal = Calendar.getInstance( TimeZone.getTimeZone( "GMT+01:00" ) );
        cal.set( 2018, Calendar.MARCH, 15, 00, 00, 00 );
        source.setValue2( cal );

        Target target = Issue1523Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isNotNull();
        assertThat( target.getValue2() ).isNotNull();
        // +01:00 -> offset is 60 min
        assertThat( target.getValue().getTimezone() ).isEqualTo( 60 );
        assertThat( target.getValue2().getTimezone() ).isEqualTo( 60 );
    }
}
