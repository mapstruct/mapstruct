/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junitpioneer.jupiter.DefaultTimeZone;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for conversions to/from Java 8 date and time types.
 */
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
@IssueKey("121")
public class Java8TimeConversionTest {

    @RegisterExtension
    GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor( SourceTargetMapper.class );

    @ProcessorTest
    public void testDateTimeToString() {
        Source src = new Source();
        src.setZonedDateTime( ZonedDateTime.of( java.time.LocalDateTime.of( 2014, 1, 1, 0, 0 ), ZoneId.of( "UTC" ) ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetDateTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getZonedDateTime() ).isEqualTo( "01.01.2014 00:00 UTC" );
    }

    @ProcessorTest
    public void testLocalDateTimeToString() {
        Source src = new Source();
        src.setLocalDateTime( LocalDateTime.of( 2014, 1, 1, 0, 0 ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetLocalDateTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalDateTime() ).isEqualTo( "01.01.2014 00:00" );
    }

    @ProcessorTest
    public void testLocalDateToString() {
        Source src = new Source();
        src.setLocalDate( LocalDate.of( 2014, 1, 1 ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetLocalDateMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalDate() ).isEqualTo( "01.01.2014" );
    }

    @ProcessorTest
    public void testLocalTimeToString() {
        Source src = new Source();
        src.setLocalTime( LocalTime.of( 0, 0 ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetLocalTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalTime() ).isEqualTo( "00:00" );
    }

    @ProcessorTest
    public void testSourceToTargetMappingForStrings() {
        Source src = new Source();
        src.setLocalTime( LocalTime.of( 0, 0 ) );
        src.setLocalDate( LocalDate.of( 2014, 1, 1 ) );
        src.setLocalDateTime( LocalDateTime.of( 2014, 1, 1, 0, 0 ) );
        src.setZonedDateTime( ZonedDateTime.of( java.time.LocalDateTime.of( 2014, 1, 1, 0, 0 ), ZoneId.of( "UTC" ) ) );

        // with given format
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( src );

        assertThat( target ).isNotNull();
        assertThat( target.getZonedDateTime() ).isEqualTo( "01.01.2014 00:00 UTC" );
        assertThat( target.getLocalDateTime() ).isEqualTo( "01.01.2014 00:00" );
        assertThat( target.getLocalDate() ).isEqualTo( "01.01.2014" );
        assertThat( target.getLocalTime() ).isEqualTo( "00:00" );

        // and now with default mappings
        target = SourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( src );
        assertThat( target ).isNotNull();
        assertThat( target.getZonedDateTime() ).isEqualTo( "01.01.2014 00:00 UTC" );
        assertThat( target.getLocalDateTime() ).isEqualTo( "01.01.2014 00:00" );
        assertThat( target.getLocalDate() ).isEqualTo( "01.01.2014" );
        assertThat( target.getLocalTime() ).isEqualTo( "00:00" );
    }

    @ProcessorTest
    public void testStringToDateTime() {
        String dateTimeAsString = "01.01.2014 00:00 UTC";
        Target target = new Target();
        target.setZonedDateTime( dateTimeAsString );
        ZonedDateTime sourceDateTime =
            ZonedDateTime.of( java.time.LocalDateTime.of( 2014, 1, 1, 0, 0 ), ZoneId.of( "UTC" ) );

        Source src = SourceTargetMapper.INSTANCE.targetToSourceDateTimeMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getZonedDateTime() ).isEqualTo( sourceDateTime );
    }

    @ProcessorTest
    public void testStringToLocalDateTime() {
        String dateTimeAsString = "01.01.2014 00:00";
        Target target = new Target();
        target.setLocalDateTime( dateTimeAsString );
        LocalDateTime sourceDateTime =
            LocalDateTime.of( 2014, 1, 1, 0, 0, 0 );

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
            LocalDate.of( 2014, 1, 1 );

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
            LocalTime.of( 0, 0 );

        Source src = SourceTargetMapper.INSTANCE.targetToSourceLocalTimeMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getLocalTime() ).isEqualTo( sourceTime );
    }

    @ProcessorTest
    public void testTargetToSourceNullMapping() {
        Target target = new Target();
        Source src = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( src ).isNotNull();
        assertThat( src.getZonedDateTime() ).isNull();
        assertThat( src.getLocalDate() ).isNull();
        assertThat( src.getLocalDateTime() ).isNull();
        assertThat( src.getLocalTime() ).isNull();
    }

    @ProcessorTest
    public void testTargetToSourceMappingForStrings() {
        Target target = new Target();

        target.setZonedDateTime( "01.01.2014 00:00 UTC" );
        target.setLocalDateTime( "01.01.2014 00:00" );
        target.setLocalDate( "01.01.2014" );
        target.setLocalTime( "00:00" );

        Source src = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( src.getZonedDateTime() ).isEqualTo(
            ZonedDateTime.of(
                java.time.LocalDateTime.of(
                    2014,
                    1,
                    1,
                    0,
                    0
                ), ZoneId.of( "UTC" ) ) );
        assertThat( src.getLocalDateTime() ).isEqualTo( LocalDateTime.of( 2014, 1, 1, 0, 0 ) );
        assertThat( src.getLocalDate() ).isEqualTo( LocalDate.of( 2014, 1, 1 ) );
        assertThat( src.getLocalTime() ).isEqualTo( LocalTime.of( 0, 0 ) );
    }

    @ProcessorTest
    public void testCalendarMapping() {
        Source source = new Source();
        ZonedDateTime dateTime = ZonedDateTime.of( LocalDateTime.of( 2014, 1, 1, 0, 0 ), ZoneId.of( "UTC" ) );
        source.setForCalendarConversion(
            dateTime );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getForCalendarConversion() ).isNotNull();
        assertThat( target.getForCalendarConversion().getTimeZone() ).isEqualTo(
            TimeZone.getTimeZone(
                "UTC" ) );
        assertThat( target.getForCalendarConversion().get( Calendar.YEAR ) ).isEqualTo( dateTime.getYear() );
        assertThat( target.getForCalendarConversion().get( Calendar.MONTH ) ).isEqualTo(
            dateTime.getMonthValue() - 1 );
        assertThat( target.getForCalendarConversion().get( Calendar.DATE ) ).isEqualTo( dateTime.getDayOfMonth() );
        assertThat( target.getForCalendarConversion().get( Calendar.MINUTE ) ).isEqualTo( dateTime.getMinute() );
        assertThat( target.getForCalendarConversion().get( Calendar.HOUR ) ).isEqualTo( dateTime.getHour() );

        source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source.getForCalendarConversion() ).isEqualTo( dateTime );
    }

    @ProcessorTest
    @DefaultTimeZone("UTC")
    public void testZonedDateTimeToDateMapping() {
        Source source = new Source();
        ZonedDateTime dateTime = ZonedDateTime.of( LocalDateTime.of( 2014, 1, 1, 0, 0 ), ZoneId.of( "UTC" ) );
        source.setForDateConversionWithZonedDateTime(
            dateTime );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( source );

        assertThat( target.getForDateConversionWithZonedDateTime() ).isNotNull();

        Calendar instance = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) );
        instance.setTimeInMillis( target.getForDateConversionWithZonedDateTime().getTime() );

        assertThat( instance.get( Calendar.YEAR ) ).isEqualTo( dateTime.getYear() );
        assertThat( instance.get( Calendar.MONTH ) ).isEqualTo( dateTime.getMonthValue() - 1 );
        assertThat( instance.get( Calendar.DATE ) ).isEqualTo( dateTime.getDayOfMonth() );
        assertThat( instance.get( Calendar.MINUTE ) ).isEqualTo( dateTime.getMinute() );
        assertThat( instance.get( Calendar.HOUR ) ).isEqualTo( dateTime.getHour() );

        source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source.getForDateConversionWithZonedDateTime() ).isEqualTo( dateTime );
    }

    @ProcessorTest
    public void testInstantToDateMapping() {
        Instant instant = Instant.ofEpochMilli( 1539366615000L );

        Source source = new Source();
        source.setForDateConversionWithInstant( instant );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( source );
        Date date = target.getForDateConversionWithInstant();
        assertThat( date ).isNotNull();
        assertThat( date.getTime() ).isEqualTo( 1539366615000L );

        source = SourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source.getForDateConversionWithInstant() ).isEqualTo( instant );
    }

    @ProcessorTest
    public void testLocalDateTimeToLocalDateMapping() {
        LocalDate localDate = LocalDate.of( 2014, 1, 1 );

        Source source = new Source();
        source.setForLocalDateTimeConversionWithLocalDate( localDate );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( source );
        LocalDateTime localDateTime = target.getForLocalDateTimeConversionWithLocalDate();
        assertThat( localDateTime ).isNotNull();
        assertThat( localDateTime ).isEqualTo( LocalDateTime.of( 2014, 1, 1, 0, 0 ) );

        source = SourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source.getForLocalDateTimeConversionWithLocalDate() ).isEqualTo( localDate );
    }

    @ProcessorTest
    @DefaultTimeZone("Australia/Melbourne")
    public void testLocalDateTimeToDateMapping() {

        Source source = new Source();
        LocalDateTime dateTime = LocalDateTime.of( 2014, 1, 1, 0, 0 );
        source.setForDateConversionWithLocalDateTime( dateTime );

        Target target = SourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( source );

        assertThat( target.getForDateConversionWithLocalDateTime() ).isNotNull();

        Calendar instance = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) );
        instance.setTimeInMillis( target.getForDateConversionWithLocalDateTime().getTime() );

        assertThat( instance.get( Calendar.YEAR ) ).isEqualTo( dateTime.getYear() );
        assertThat( instance.get( Calendar.MONTH ) ).isEqualTo( dateTime.getMonthValue() - 1 );
        assertThat( instance.get( Calendar.DATE ) ).isEqualTo( dateTime.getDayOfMonth() );
        assertThat( instance.get( Calendar.MINUTE ) ).isEqualTo( dateTime.getMinute() );
        assertThat( instance.get( Calendar.HOUR ) ).isEqualTo( dateTime.getHour() );

        source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source.getForDateConversionWithLocalDateTime() ).isEqualTo( dateTime );
    }

    @ProcessorTest
    @DefaultTimeZone("Australia/Melbourne")
    public void testLocalDateToDateMapping() {

        Source source = new Source();
        LocalDate localDate = LocalDate.of( 2016, 3, 1 );
        source.setForDateConversionWithLocalDate( localDate );

        Target target = SourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( source );

        assertThat( target.getForDateConversionWithLocalDate() ).isNotNull();

        Calendar instance = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) );
        instance.setTimeInMillis( target.getForDateConversionWithLocalDate().getTime() );

        assertThat( instance.get( Calendar.YEAR ) ).isEqualTo( localDate.getYear() );
        assertThat( instance.get( Calendar.MONTH ) ).isEqualTo( localDate.getMonthValue() - 1 );
        assertThat( instance.get( Calendar.DATE ) ).isEqualTo( localDate.getDayOfMonth() );

        source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source.getForDateConversionWithLocalDate() ).isEqualTo( localDate );
    }

    @ProcessorTest
    @DefaultTimeZone("Australia/Melbourne")
    public void testLocalDateToSqlDateMapping() {

        Source source = new Source();
        LocalDate localDate = LocalDate.of( 2016, 3, 1 );
        source.setForSqlDateConversionWithLocalDate( localDate );

        Target target = SourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( source );

        assertThat( target.getForSqlDateConversionWithLocalDate() ).isNotNull();

        Calendar instance = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) );
        instance.setTimeInMillis( target.getForSqlDateConversionWithLocalDate().getTime() );

        assertThat( instance.get( Calendar.YEAR ) ).isEqualTo( localDate.getYear() );
        assertThat( instance.get( Calendar.MONTH ) ).isEqualTo( localDate.getMonthValue() - 1 );
        assertThat( instance.get( Calendar.DATE ) ).isEqualTo( localDate.getDayOfMonth() );

        source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source.getForSqlDateConversionWithLocalDate() ).isEqualTo( localDate );
    }

    @ProcessorTest
    public void testInstantToStringMapping() {
        Source source = new Source();
        source.setForInstantConversionWithString( Instant.ofEpochSecond( 42L ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        String periodString = target.getForInstantConversionWithString();
        assertThat( periodString ).isEqualTo( "1970-01-01T00:00:42Z" );
    }

    @ProcessorTest
    public void testInstantToStringNullMapping() {
        Source source = new Source();
        source.setForInstantConversionWithString( null );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        String periodString = target.getForInstantConversionWithString();
        assertThat( periodString ).isNull();
    }

    @ProcessorTest
    public void testStringToInstantMapping() {
        Target target = new Target();
        target.setForInstantConversionWithString( "1970-01-01T00:00:00.000Z" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );
        Instant instant = source.getForInstantConversionWithString();
        assertThat( instant ).isEqualTo( Instant.EPOCH );
    }

    @ProcessorTest
    public void testStringToInstantNullMapping() {
        Target target = new Target();
        target.setForInstantConversionWithString( null );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );
        Instant instant = source.getForInstantConversionWithString();
        assertThat( instant ).isNull();
    }

    @ProcessorTest
    public void testPeriodToStringMapping() {
        Source source = new Source();
        source.setForPeriodConversionWithString( Period.ofDays( 42 ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        String periodString = target.getForPeriodConversionWithString();
        assertThat( periodString ).isEqualTo( "P42D" );
    }

    @ProcessorTest
    public void testPeriodToStringNullMapping() {
        Source source = new Source();
        source.setForPeriodConversionWithString( null );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        String periodString = target.getForPeriodConversionWithString();
        assertThat( periodString ).isNull();
    }

    @ProcessorTest
    public void testStringToPeriodMapping() {
        Target target = new Target();
        target.setForPeriodConversionWithString( "P1Y2M3D" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );
        Period period = source.getForPeriodConversionWithString();
        assertThat( period ).isEqualTo( Period.of( 1, 2, 3 ) );
    }

    @ProcessorTest
    public void testStringToPeriodNullMapping() {
        Target target = new Target();
        target.setForPeriodConversionWithString( null );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );
        Period period = source.getForPeriodConversionWithString();
        assertThat( period ).isNull();
    }

    @ProcessorTest
    public void testDurationToStringMapping() {
        Source source = new Source();
        source.setForDurationConversionWithString( Duration.ofMinutes( 42L ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        String durationString = target.getForDurationConversionWithString();
        assertThat( durationString ).isEqualTo( "PT42M" );
    }

    @ProcessorTest
    public void testDurationToStringNullMapping() {
        Source source = new Source();
        source.setForDurationConversionWithString( null );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        String durationString = target.getForDurationConversionWithString();
        assertThat( durationString ).isNull();
    }

    @ProcessorTest
    public void testStringToDurationMapping() {
        Target target = new Target();
        target.setForDurationConversionWithString( "PT20.345S" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );
        Duration duration = source.getForDurationConversionWithString();
        assertThat( duration ).isEqualTo( Duration.ofSeconds( 20L, 345000000L ) );
    }

    @ProcessorTest
    public void testStringToDurationNullMapping() {
        Target target = new Target();
        target.setForDurationConversionWithString( null );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );
        Duration duration = source.getForDurationConversionWithString();
        assertThat( duration ).isNull();
    }
}
