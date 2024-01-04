/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.jodatime;

import java.util.Calendar;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junitpioneer.jupiter.DefaultLocale;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJoda;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the conversion between Joda-Time types and String/Date/Calendar.
 *
 * @author Timo Eckhardt
 */
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
@IssueKey("75")
@DefaultLocale("de")
@WithJoda
public class JodaConversionTest {

    @ProcessorTest
    public void testDateTimeToString() {
        Source src = new Source();
        src.setDateTime( new DateTime( 2014, 1, 1, 0, 0, 0, DateTimeZone.UTC ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetDateTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getDateTime() ).isEqualTo( "01.01.2014 00:00 UTC" );
    }

    @ProcessorTest
    public void testLocalDateTimeToString() {
        Source src = new Source();
        src.setLocalDateTime( new LocalDateTime( 2014, 1, 1, 0, 0 ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetLocalDateTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalDateTime() ).isEqualTo( "01.01.2014 00:00" );
    }

    @ProcessorTest
    public void testLocalDateToString() {
        Source src = new Source();
        src.setLocalDate( new LocalDate( 2014, 1, 1 ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetLocalDateMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalDate() ).isEqualTo( "01.01.2014" );
    }

    @ProcessorTest
    public void testLocalTimeToString() {
        Source src = new Source();
        src.setLocalTime( new LocalTime( 0, 0 ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetLocalTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalTime() ).isEqualTo( "00:00" );
    }

    @ProcessorTest
    @EnabledForJreRange(min = JRE.JAVA_21)
    // See https://bugs.openjdk.java.net/browse/JDK-8211262, there is a difference in the default formats on Java 9+
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
        assertThat( target.getDateTime() ).isEqualTo( "1. Januar 2014, 00:00:00 UTC" );
        assertThat( target.getLocalDateTime() ).isEqualTo( "1. Januar 2014, 00:00:00" );
        assertThat( target.getLocalDate() ).isEqualTo( "1. Januar 2014" );
        assertThat( target.getLocalTime() ).isEqualTo( "00:00:00" );
    }

    @ProcessorTest
    @EnabledForJreRange(min = JRE.JAVA_11, max = JRE.JAVA_17)
    // See https://bugs.openjdk.java.net/browse/JDK-8211262, there is a difference in the default formats on Java 9+
    public void testSourceToTargetMappingForStringsJdk11() {
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
        assertThat( target.getDateTime() ).isEqualTo( "1. Januar 2014 um 00:00:00 UTC" );
        assertThat( target.getLocalDateTime() ).isEqualTo( "1. Januar 2014 um 00:00:00" );
        assertThat( target.getLocalDate() ).isEqualTo( "1. Januar 2014" );
        assertThat( target.getLocalTime() ).isEqualTo( "00:00:00" );
    }

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
    public void testTargetToSourceNullMapping() {
        Target target = new Target();
        Source src = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( src ).isNotNull();
        assertThat( src.getDateTime() ).isNull();
        assertThat( src.getLocalDate() ).isNull();
        assertThat( src.getLocalDateTime() ).isNull();
        assertThat( src.getLocalTime() ).isNull();
    }

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
    @WithClasses({ StringToLocalDateMapper.class, SourceWithStringDate.class, TargetWithLocalDate.class })
    @IssueKey("456")
    public void testStringToLocalDateUsingDefaultFormat() {
        SourceWithStringDate source = new SourceWithStringDate();
        source.setDate( "19. November 2014" );

        TargetWithLocalDate target = StringToLocalDateMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getDate() ).isEqualTo( new LocalDate( 2014, 11, 19 ) );
    }
}
