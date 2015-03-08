/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.conversion.jodatime;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests the conversion between Joda-Time types and String/Date/Calendar.
 *
 * @author Timo Eckhardt
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
@IssueKey("75")
public class JodaConversionTest {

    @Before
    public void setDefaultLocale() {
        Locale.setDefault( Locale.GERMAN );
    }

    @Test
    public void testDateTimeToString() {
        Source src = new Source();
        src.setDateTime( new DateTime( 2014, 1, 1, 0, 0, 0, DateTimeZone.UTC ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetDateTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getDateTime() ).isEqualTo( "01.01.2014 00:00 UTC" );
    }

    @Test
    public void testLocalDateTimeToString() {
        Source src = new Source();
        src.setLocalDateTime( new LocalDateTime( 2014, 1, 1, 0, 0 ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetLocalDateTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalDateTime() ).isEqualTo( "01.01.2014 00:00" );
    }

    @Test
    public void testLocalDateToString() {
        Source src = new Source();
        src.setLocalDate( new LocalDate( 2014, 1, 1 ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetLocalDateMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalDate() ).isEqualTo( "01.01.2014" );
    }

    @Test
    public void testLocalTimeToString() {
        Source src = new Source();
        src.setLocalTime( new LocalTime( 0, 0 ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetLocalTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalTime() ).isEqualTo( "00:00" );
    }

    @Test
    public void testSourceToTargetMappingForStrings() {
        Source src = new Source();
        src.setLocalTime( new LocalTime( 0, 0 ) );
        src.setLocalDate( new LocalDate( 2014, 1, 1 ) );
        src.setLocalDateTime( new LocalDateTime( 2014, 1, 1, 0, 0 ) );
        src.setDateTime( new DateTime( 2014, 1, 1, 0, 0, 0, DateTimeZone.UTC ) );

        // with given format
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( src );

        assertThat( target ).isNotNull();
        assertThat( target.getDateTime() ).isEqualTo( "01.01.2014 00:00 UTC" );
        assertThat( target.getLocalDateTime() ).isEqualTo( "01.01.2014 00:00" );
        assertThat( target.getLocalDate() ).isEqualTo( "01.01.2014" );
        assertThat( target.getLocalTime() ).isEqualTo( "00:00" );

        // and now with default mappings
        target = SourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( src );
        assertThat( target ).isNotNull();
        assertThat( target.getDateTime() ).isEqualTo( "1. Januar 2014 00:00:00 UTC" );
        assertThat( target.getLocalDateTime() ).isEqualTo( "1. Januar 2014 00:00:00" );
        assertThat( target.getLocalDate() ).isEqualTo( "1. Januar 2014" );
        assertThat( target.getLocalTime() ).isEqualTo( "00:00:00" );
    }

    @Test
    public void testStringToDateTime() {
        String dateTimeAsString = "01.01.2014 00:00 UTC";
        Target target = new Target();
        target.setDateTime( dateTimeAsString );
        DateTime sourceDateTime =
            new DateTime( 2014, 1, 1, 0, 0, 0, DateTimeZone.UTC );

        Source src = SourceTargetMapper.INSTANCE.targetToSourceDateTimeMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getDateTime() ).isEqualTo( sourceDateTime );
    }

    @Test
    public void testStringToLocalDateTime() {
        String dateTimeAsString = "01.01.2014 00:00";
        Target target = new Target();
        target.setLocalDateTime( dateTimeAsString );
        LocalDateTime sourceDateTime =
            new LocalDateTime( 2014, 1, 1, 0, 0, 0 );

        Source src = SourceTargetMapper.INSTANCE.targetToSourceLocalDateTimeMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getLocalDateTime() ).isEqualTo( sourceDateTime );
    }

    @Test
    public void testStringToLocalDate() {
        String dateTimeAsString = "01.01.2014";
        Target target = new Target();
        target.setLocalDate( dateTimeAsString );
        LocalDate sourceDate =
            new LocalDate( 2014, 1, 1 );

        Source src = SourceTargetMapper.INSTANCE.targetToSourceLocalDateMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getLocalDate() ).isEqualTo( sourceDate );
    }

    @Test
    public void testStringToLocalTime() {
        String dateTimeAsString = "00:00";
        Target target = new Target();
        target.setLocalTime( dateTimeAsString );
        LocalTime sourceTime =
            new LocalTime( 0, 0 );

        Source src = SourceTargetMapper.INSTANCE.targetToSourceLocalTimeMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getLocalTime() ).isEqualTo( sourceTime );
    }

    @Test
    public void testTargetToSourceNullMapping() {
        Target target = new Target();
        Source src = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( src ).isNotNull();
        assertThat( src.getDateTime() ).isNull();
        assertThat( src.getLocalDate() ).isNull();
        assertThat( src.getLocalDateTime() ).isNull();
        assertThat( src.getLocalTime() ).isNull();
    }

    @Test
    public void testTargetToSourceMappingForStrings() {
        Target target = new Target();

        target.setDateTime( "01.01.2014 00:00 UTC" );
        target.setLocalDateTime( "01.01.2014 00:00" );
        target.setLocalDate( "01.01.2014" );
        target.setLocalTime( "00:00" );

        Source src = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( src.getDateTime() ).isEqualTo( new DateTime( 2014, 1, 1, 0, 0, DateTimeZone.UTC ) );
        assertThat( src.getLocalDateTime() ).isEqualTo( new LocalDateTime( 2014, 1, 1, 0, 0 ) );
        assertThat( src.getLocalDate() ).isEqualTo( new LocalDate( 2014, 1, 1 ) );
        assertThat( src.getLocalTime() ).isEqualTo( new LocalTime( 0, 0 ) );
    }

    @Test
    public void testCalendar() {
        Calendar calendar = Calendar.getInstance( TimeZone.getTimeZone( "CET" ) );
        DateTime dateTimeWithCalendar = new DateTime( calendar );

        Source src = new Source();
        src.setDateTimeForCalendarConversion( dateTimeWithCalendar );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( src );
        assertThat( target ).isNotNull();
        assertThat( target.getDateTimeForCalendarConversion().getTime() ).isEqualTo( calendar.getTime() );

        Source mappedSource = SourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( mappedSource ).isNotNull();
        assertThat( mappedSource.getDateTimeForCalendarConversion() ).isEqualTo( dateTimeWithCalendar );
    }

    @Test
    @WithClasses({ StringToLocalDateMapper.class, SourceWithStringDate.class, TargetWithLocalDate.class })
    @IssueKey("456")
    public void testStringToLocalDateUsingDefaultFormat() {
        SourceWithStringDate source = new SourceWithStringDate();
        source.setDate( "19. November 2014" );

        TargetWithLocalDate target = StringToLocalDateMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getDate() ).isEqualTo( new LocalDate( 2014, 11, 19 ) );
    }
}
