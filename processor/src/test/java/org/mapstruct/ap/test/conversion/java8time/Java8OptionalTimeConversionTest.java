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
import java.util.Optional;
import java.util.TimeZone;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.junitpioneer.jupiter.DefaultTimeZone;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for conversions to/from Java 8 date and time types wrapped in {@link Optional}.
 */
@WithClasses({ OptionalSource.class, Target.class, OptionalSourceTargetMapper.class })
public class Java8OptionalTimeConversionTest {

    @RegisterExtension
    GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void generatedCode() {
        generatedSource.addComparisonToFixtureFor( OptionalSourceTargetMapper.class );
    }

    @ProcessorTest
    public void testDateTimeToString() {
        OptionalSource src = new OptionalSource();
        src.setZonedDateTime( Optional.of( ZonedDateTime.of(
            LocalDateTime.of( 2014, 1, 1, 0, 0 ),
            ZoneId.of( "UTC" )
        ) ) );
        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTargetDateTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getZonedDateTime() ).isEqualTo( "01.01.2014 00:00 UTC" );
    }

    @ProcessorTest
    public void testLocalDateTimeToString() {
        OptionalSource src = new OptionalSource();
        src.setLocalDateTime( Optional.of( LocalDateTime.of( 2014, 1, 1, 0, 0 ) ) );
        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTargetLocalDateTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalDateTime() ).isEqualTo( "01.01.2014 00:00" );
    }

    @ProcessorTest
    public void testLocalDateToString() {
        OptionalSource src = new OptionalSource();
        src.setLocalDate( Optional.of( LocalDate.of( 2014, 1, 1 ) ) );
        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTargetLocalDateMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalDate() ).isEqualTo( "01.01.2014" );
    }

    @ProcessorTest
    public void testLocalTimeToString() {
        OptionalSource src = new OptionalSource();
        src.setLocalTime( Optional.ofNullable( LocalTime.of( 0, 0 ) ) );
        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTargetLocalTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalTime() ).isEqualTo( "00:00" );
    }

    @ProcessorTest
    public void testSourceToTargetMappingForStrings() {
        OptionalSource src = new OptionalSource();
        src.setLocalTime( Optional.ofNullable( LocalTime.of( 0, 0 ) ) );
        src.setLocalDate( Optional.of( LocalDate.of( 2014, 1, 1 ) ) );
        src.setLocalDateTime( Optional.of( LocalDateTime.of( 2014, 1, 1, 0, 0 ) ) );
        src.setZonedDateTime( Optional.of( ZonedDateTime.of(
            LocalDateTime.of( 2014, 1, 1, 0, 0 ),
            ZoneId.of( "UTC" )
        ) ) );

        // with given format
        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTarget( src );

        assertThat( target ).isNotNull();
        assertThat( target.getZonedDateTime() ).isEqualTo( "01.01.2014 00:00 UTC" );
        assertThat( target.getLocalDateTime() ).isEqualTo( "01.01.2014 00:00" );
        assertThat( target.getLocalDate() ).isEqualTo( "01.01.2014" );
        assertThat( target.getLocalTime() ).isEqualTo( "00:00" );

        // and now with default mappings
        target = OptionalSourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( src );
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
            ZonedDateTime.of( LocalDateTime.of( 2014, 1, 1, 0, 0 ), ZoneId.of( "UTC" ) );

        OptionalSource src = OptionalSourceTargetMapper.INSTANCE.targetToSourceDateTimeMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getZonedDateTime() ).contains( sourceDateTime );
    }

    @ProcessorTest
    public void testStringToLocalDateTime() {
        String dateTimeAsString = "01.01.2014 00:00";
        Target target = new Target();
        target.setLocalDateTime( dateTimeAsString );
        LocalDateTime sourceDateTime =
            LocalDateTime.of( 2014, 1, 1, 0, 0, 0 );

        OptionalSource src = OptionalSourceTargetMapper.INSTANCE.targetToSourceLocalDateTimeMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getLocalDateTime() ).contains( sourceDateTime );
    }

    @ProcessorTest
    public void testStringToLocalDate() {
        String dateTimeAsString = "01.01.2014";
        Target target = new Target();
        target.setLocalDate( dateTimeAsString );
        LocalDate sourceDate =
            LocalDate.of( 2014, 1, 1 );

        OptionalSource src = OptionalSourceTargetMapper.INSTANCE.targetToSourceLocalDateMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getLocalDate() ).contains( sourceDate );
    }

    @ProcessorTest
    public void testStringToLocalTime() {
        String dateTimeAsString = "00:00";
        Target target = new Target();
        target.setLocalTime( dateTimeAsString );
        LocalTime sourceTime =
            LocalTime.of( 0, 0 );

        OptionalSource src = OptionalSourceTargetMapper.INSTANCE.targetToSourceLocalTimeMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getLocalTime() ).contains( sourceTime );
    }

    @ProcessorTest
    public void testTargetToSourceNullMapping() {
        Target target = new Target();
        OptionalSource src = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( src ).isNotNull();
        assertThat( src.getZonedDateTime() ).isEmpty();
        assertThat( src.getLocalDate() ).isEmpty();
        assertThat( src.getLocalDateTime() ).isEmpty();
        assertThat( src.getLocalTime() ).isEmpty();
    }

    @ProcessorTest
    public void testTargetToSourceMappingForStrings() {
        Target target = new Target();

        target.setZonedDateTime( "01.01.2014 00:00 UTC" );
        target.setLocalDateTime( "01.01.2014 00:00" );
        target.setLocalDate( "01.01.2014" );
        target.setLocalTime( "00:00" );

        OptionalSource src = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( src.getZonedDateTime() ).contains(
            ZonedDateTime.of(
                LocalDateTime.of(
                    2014,
                    1,
                    1,
                    0,
                    0
                ), ZoneId.of( "UTC" )
            ) );
        assertThat( src.getLocalDateTime() ).contains( LocalDateTime.of( 2014, 1, 1, 0, 0 ) );
        assertThat( src.getLocalDate() ).contains( LocalDate.of( 2014, 1, 1 ) );
        assertThat( src.getLocalTime() ).contains( LocalTime.of( 0, 0 ) );
    }

    @ProcessorTest
    public void testCalendarMapping() {
        OptionalSource source = new OptionalSource();
        ZonedDateTime dateTime = ZonedDateTime.of( LocalDateTime.of( 2014, 1, 1, 0, 0 ), ZoneId.of( "UTC" ) );
        source.setForCalendarConversion( Optional.of( dateTime ) );

        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTarget( source );

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

        source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source.getForCalendarConversion() ).contains( dateTime );
    }

    @ProcessorTest
    @DefaultTimeZone("UTC")
    public void testZonedDateTimeToDateMapping() {
        OptionalSource source = new OptionalSource();
        ZonedDateTime dateTime = ZonedDateTime.of( LocalDateTime.of( 2014, 1, 1, 0, 0 ), ZoneId.of( "UTC" ) );
        source.setForDateConversionWithZonedDateTime( Optional.of( dateTime ) );
        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( source );

        assertThat( target.getForDateConversionWithZonedDateTime() ).isNotNull();

        Calendar instance = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) );
        instance.setTimeInMillis( target.getForDateConversionWithZonedDateTime().getTime() );

        assertThat( instance.get( Calendar.YEAR ) ).isEqualTo( dateTime.getYear() );
        assertThat( instance.get( Calendar.MONTH ) ).isEqualTo( dateTime.getMonthValue() - 1 );
        assertThat( instance.get( Calendar.DATE ) ).isEqualTo( dateTime.getDayOfMonth() );
        assertThat( instance.get( Calendar.MINUTE ) ).isEqualTo( dateTime.getMinute() );
        assertThat( instance.get( Calendar.HOUR ) ).isEqualTo( dateTime.getHour() );

        source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source.getForDateConversionWithZonedDateTime() ).contains( dateTime );
    }

    @ProcessorTest
    public void testInstantToDateMapping() {
        Instant instant = Instant.ofEpochMilli( 1539366615000L );

        OptionalSource source = new OptionalSource();
        source.setForDateConversionWithInstant( Optional.ofNullable( instant ) );
        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( source );
        Date date = target.getForDateConversionWithInstant();
        assertThat( date ).isNotNull();
        assertThat( date.getTime() ).isEqualTo( 1539366615000L );

        source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source.getForDateConversionWithInstant() ).contains( instant );
    }

    @ProcessorTest
    public void testLocalDateTimeToLocalDateMapping() {
        LocalDate localDate = LocalDate.of( 2014, 1, 1 );

        OptionalSource source = new OptionalSource();
        source.setForLocalDateTimeConversionWithLocalDate( Optional.of( localDate ) );
        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( source );
        LocalDateTime localDateTime = target.getForLocalDateTimeConversionWithLocalDate();
        assertThat( localDateTime ).isNotNull();
        assertThat( localDateTime ).isEqualTo( LocalDateTime.of( 2014, 1, 1, 0, 0 ) );

        source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source.getForLocalDateTimeConversionWithLocalDate() ).contains( localDate );
    }

    @ProcessorTest
    @DefaultTimeZone("Australia/Melbourne")
    public void testLocalDateTimeToDateMapping() {

        OptionalSource source = new OptionalSource();
        LocalDateTime dateTime = LocalDateTime.of( 2014, 1, 1, 0, 0 );
        source.setForDateConversionWithLocalDateTime( Optional.of( dateTime ) );

        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( source );

        assertThat( target.getForDateConversionWithLocalDateTime() ).isNotNull();

        Calendar instance = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) );
        instance.setTimeInMillis( target.getForDateConversionWithLocalDateTime().getTime() );

        assertThat( instance.get( Calendar.YEAR ) ).isEqualTo( dateTime.getYear() );
        assertThat( instance.get( Calendar.MONTH ) ).isEqualTo( dateTime.getMonthValue() - 1 );
        assertThat( instance.get( Calendar.DATE ) ).isEqualTo( dateTime.getDayOfMonth() );
        assertThat( instance.get( Calendar.MINUTE ) ).isEqualTo( dateTime.getMinute() );
        assertThat( instance.get( Calendar.HOUR ) ).isEqualTo( dateTime.getHour() );

        source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source.getForDateConversionWithLocalDateTime() ).contains( dateTime );
    }

    @ProcessorTest
    @DefaultTimeZone("Australia/Melbourne")
    public void testLocalDateToDateMapping() {

        OptionalSource source = new OptionalSource();
        LocalDate localDate = LocalDate.of( 2016, 3, 1 );
        source.setForDateConversionWithLocalDate( Optional.of( localDate ) );

        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( source );

        assertThat( target.getForDateConversionWithLocalDate() ).isNotNull();

        Calendar instance = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) );
        instance.setTimeInMillis( target.getForDateConversionWithLocalDate().getTime() );

        assertThat( instance.get( Calendar.YEAR ) ).isEqualTo( localDate.getYear() );
        assertThat( instance.get( Calendar.MONTH ) ).isEqualTo( localDate.getMonthValue() - 1 );
        assertThat( instance.get( Calendar.DATE ) ).isEqualTo( localDate.getDayOfMonth() );

        source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source.getForDateConversionWithLocalDate() ).contains( localDate );
    }

    @ProcessorTest
    @DefaultTimeZone("Australia/Melbourne")
    public void testLocalDateToSqlDateMapping() {

        OptionalSource source = new OptionalSource();
        LocalDate localDate = LocalDate.of( 2016, 3, 1 );
        source.setForSqlDateConversionWithLocalDate( Optional.of( localDate ) );

        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( source );

        assertThat( target.getForSqlDateConversionWithLocalDate() ).isNotNull();

        Calendar instance = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) );
        instance.setTimeInMillis( target.getForSqlDateConversionWithLocalDate().getTime() );

        assertThat( instance.get( Calendar.YEAR ) ).isEqualTo( localDate.getYear() );
        assertThat( instance.get( Calendar.MONTH ) ).isEqualTo( localDate.getMonthValue() - 1 );
        assertThat( instance.get( Calendar.DATE ) ).isEqualTo( localDate.getDayOfMonth() );

        source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source.getForSqlDateConversionWithLocalDate() ).contains( localDate );
    }

    @ProcessorTest
    public void testInstantToStringMapping() {
        OptionalSource source = new OptionalSource();
        source.setForInstantConversionWithString( Optional.ofNullable( Instant.ofEpochSecond( 42L ) ) );

        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTarget( source );
        String periodString = target.getForInstantConversionWithString();
        assertThat( periodString ).isEqualTo( "1970-01-01T00:00:42Z" );
    }

    @ProcessorTest
    public void testInstantToStringNullMapping() {
        OptionalSource source = new OptionalSource();
        source.setForInstantConversionWithString( Optional.empty() );

        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTarget( source );
        String periodString = target.getForInstantConversionWithString();
        assertThat( periodString ).isNull();
    }

    @ProcessorTest
    public void testStringToInstantMapping() {
        Target target = new Target();
        target.setForInstantConversionWithString( "1970-01-01T00:00:00.000Z" );

        OptionalSource source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source.getForInstantConversionWithString() ).contains( Instant.EPOCH );
    }

    @ProcessorTest
    public void testStringToInstantNullMapping() {
        Target target = new Target();
        target.setForInstantConversionWithString( null );

        OptionalSource source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source.getForInstantConversionWithString() ).isEmpty();
    }

    @ProcessorTest
    public void testPeriodToStringMapping() {
        OptionalSource source = new OptionalSource();
        source.setForPeriodConversionWithString( Optional.ofNullable( Period.ofDays( 42 ) ) );

        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTarget( source );
        String periodString = target.getForPeriodConversionWithString();
        assertThat( periodString ).isEqualTo( "P42D" );
    }

    @ProcessorTest
    public void testPeriodToStringNullMapping() {
        OptionalSource source = new OptionalSource();
        source.setForPeriodConversionWithString( Optional.empty() );

        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTarget( source );
        String periodString = target.getForPeriodConversionWithString();
        assertThat( periodString ).isNull();
    }

    @ProcessorTest
    public void testStringToPeriodMapping() {
        Target target = new Target();
        target.setForPeriodConversionWithString( "P1Y2M3D" );

        OptionalSource source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source.getForPeriodConversionWithString() ).contains( Period.of( 1, 2, 3 ) );
    }

    @ProcessorTest
    public void testStringToPeriodNullMapping() {
        Target target = new Target();
        target.setForPeriodConversionWithString( null );

        OptionalSource source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source.getForPeriodConversionWithString() ).isEmpty();
    }

    @ProcessorTest
    public void testDurationToStringMapping() {
        OptionalSource source = new OptionalSource();
        source.setForDurationConversionWithString( Optional.ofNullable( Duration.ofMinutes( 42L ) ) );

        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTarget( source );
        String durationString = target.getForDurationConversionWithString();
        assertThat( durationString ).isEqualTo( "PT42M" );
    }

    @ProcessorTest
    public void testDurationToStringNullMapping() {
        OptionalSource source = new OptionalSource();
        source.setForDurationConversionWithString( Optional.empty() );

        Target target = OptionalSourceTargetMapper.INSTANCE.sourceToTarget( source );
        String durationString = target.getForDurationConversionWithString();
        assertThat( durationString ).isNull();
    }

    @ProcessorTest
    public void testStringToDurationMapping() {
        Target target = new Target();
        target.setForDurationConversionWithString( "PT20.345S" );

        OptionalSource source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source.getForDurationConversionWithString() ).contains( Duration.ofSeconds( 20L, 345000000L ) );
    }

    @ProcessorTest
    public void testStringToDurationNullMapping() {
        Target target = new Target();
        target.setForDurationConversionWithString( null );

        OptionalSource source = OptionalSourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source.getForDurationConversionWithString() ).isEmpty();
    }
}
