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
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T14:56:13+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (IBM Corporation)"
)
public class OptionalSourceTargetMapperImpl implements OptionalSourceTargetMapper {

    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 = DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm z" );
    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 = DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" );
    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_11900521056 = DateTimeFormatter.ofPattern( "dd.MM.yyyy" );
    private final DateTimeFormatter dateTimeFormatter_HH_mm_168697690 = DateTimeFormatter.ofPattern( "HH:mm" );

    @Override
    public Target sourceToTarget(OptionalSource source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        Optional<ZonedDateTime> zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime.isPresent() ) {
            target.setZonedDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668.format( source.getZonedDateTime().get() ) );
        }
        Optional<LocalDateTime> localDateTime = source.getLocalDateTime();
        if ( localDateTime.isPresent() ) {
            target.setLocalDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( source.getLocalDateTime().get() ) );
        }
        Optional<LocalDate> localDate = source.getLocalDate();
        if ( localDate.isPresent() ) {
            target.setLocalDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( source.getLocalDate().get() ) );
        }
        Optional<LocalTime> localTime = source.getLocalTime();
        if ( localTime.isPresent() ) {
            target.setLocalTime( dateTimeFormatter_HH_mm_168697690.format( source.getLocalTime().get() ) );
        }
        Optional<ZonedDateTime> forCalendarConversion = source.getForCalendarConversion();
        if ( forCalendarConversion.isPresent() ) {
            target.setForCalendarConversion( zonedDateTimeToCalendar( forCalendarConversion.get() ) );
        }
        Optional<ZonedDateTime> forDateConversionWithZonedDateTime = source.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime.isPresent() ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().get().toInstant() ) );
        }
        Optional<LocalDateTime> forDateConversionWithLocalDateTime = source.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime.isPresent() ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().get().toInstant( ZoneOffset.UTC ) ) );
        }
        Optional<LocalDate> forDateConversionWithLocalDate = source.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate.isPresent() ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        Optional<LocalDate> forSqlDateConversionWithLocalDate = source.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate.isPresent() ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        Optional<Instant> forDateConversionWithInstant = source.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant.isPresent() ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant().get() ) );
        }
        Optional<LocalDate> forLocalDateTimeConversionWithLocalDate = source.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate.isPresent() ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().get().atStartOfDay() );
        }
        Optional<Instant> forInstantConversionWithString = source.getForInstantConversionWithString();
        if ( forInstantConversionWithString.isPresent() ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().get().toString() );
        }
        Optional<Period> forPeriodConversionWithString = source.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString.isPresent() ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().get().toString() );
        }
        Optional<Duration> forDurationConversionWithString = source.getForDurationConversionWithString();
        if ( forDurationConversionWithString.isPresent() ) {
            target.setForDurationConversionWithString( source.getForDurationConversionWithString().get().toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetDefaultMapping(OptionalSource source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        Optional<ZonedDateTime> zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime.isPresent() ) {
            target.setZonedDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668.format( source.getZonedDateTime().get() ) );
        }
        Optional<LocalDateTime> localDateTime = source.getLocalDateTime();
        if ( localDateTime.isPresent() ) {
            target.setLocalDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( source.getLocalDateTime().get() ) );
        }
        Optional<LocalDate> localDate = source.getLocalDate();
        if ( localDate.isPresent() ) {
            target.setLocalDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( source.getLocalDate().get() ) );
        }
        Optional<LocalTime> localTime = source.getLocalTime();
        if ( localTime.isPresent() ) {
            target.setLocalTime( dateTimeFormatter_HH_mm_168697690.format( source.getLocalTime().get() ) );
        }
        Optional<ZonedDateTime> forCalendarConversion = source.getForCalendarConversion();
        if ( forCalendarConversion.isPresent() ) {
            target.setForCalendarConversion( zonedDateTimeToCalendar( forCalendarConversion.get() ) );
        }
        Optional<ZonedDateTime> forDateConversionWithZonedDateTime = source.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime.isPresent() ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().get().toInstant() ) );
        }
        Optional<LocalDateTime> forDateConversionWithLocalDateTime = source.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime.isPresent() ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().get().toInstant( ZoneOffset.UTC ) ) );
        }
        Optional<LocalDate> forDateConversionWithLocalDate = source.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate.isPresent() ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        Optional<LocalDate> forSqlDateConversionWithLocalDate = source.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate.isPresent() ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        Optional<Instant> forDateConversionWithInstant = source.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant.isPresent() ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant().get() ) );
        }
        Optional<LocalDate> forLocalDateTimeConversionWithLocalDate = source.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate.isPresent() ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().get().atStartOfDay() );
        }
        Optional<Instant> forInstantConversionWithString = source.getForInstantConversionWithString();
        if ( forInstantConversionWithString.isPresent() ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().get().toString() );
        }
        Optional<Period> forPeriodConversionWithString = source.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString.isPresent() ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().get().toString() );
        }
        Optional<Duration> forDurationConversionWithString = source.getForDurationConversionWithString();
        if ( forDurationConversionWithString.isPresent() ) {
            target.setForDurationConversionWithString( source.getForDurationConversionWithString().get().toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetDateTimeMapped(OptionalSource source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        Optional<ZonedDateTime> zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime.isPresent() ) {
            target.setZonedDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668.format( source.getZonedDateTime().get() ) );
        }
        Optional<LocalDateTime> localDateTime = source.getLocalDateTime();
        if ( localDateTime.isPresent() ) {
            target.setLocalDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( source.getLocalDateTime().get() ) );
        }
        Optional<LocalDate> localDate = source.getLocalDate();
        if ( localDate.isPresent() ) {
            target.setLocalDate( DateTimeFormatter.ISO_LOCAL_DATE.format( source.getLocalDate().get() ) );
        }
        Optional<LocalTime> localTime = source.getLocalTime();
        if ( localTime.isPresent() ) {
            target.setLocalTime( DateTimeFormatter.ISO_LOCAL_TIME.format( source.getLocalTime().get() ) );
        }
        Optional<ZonedDateTime> forCalendarConversion = source.getForCalendarConversion();
        if ( forCalendarConversion.isPresent() ) {
            target.setForCalendarConversion( zonedDateTimeToCalendar( forCalendarConversion.get() ) );
        }
        Optional<ZonedDateTime> forDateConversionWithZonedDateTime = source.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime.isPresent() ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().get().toInstant() ) );
        }
        Optional<LocalDateTime> forDateConversionWithLocalDateTime = source.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime.isPresent() ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().get().toInstant( ZoneOffset.UTC ) ) );
        }
        Optional<LocalDate> forDateConversionWithLocalDate = source.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate.isPresent() ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        Optional<LocalDate> forSqlDateConversionWithLocalDate = source.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate.isPresent() ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        Optional<Instant> forDateConversionWithInstant = source.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant.isPresent() ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant().get() ) );
        }
        Optional<LocalDate> forLocalDateTimeConversionWithLocalDate = source.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate.isPresent() ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().get().atStartOfDay() );
        }
        Optional<Instant> forInstantConversionWithString = source.getForInstantConversionWithString();
        if ( forInstantConversionWithString.isPresent() ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().get().toString() );
        }
        Optional<Period> forPeriodConversionWithString = source.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString.isPresent() ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().get().toString() );
        }
        Optional<Duration> forDurationConversionWithString = source.getForDurationConversionWithString();
        if ( forDurationConversionWithString.isPresent() ) {
            target.setForDurationConversionWithString( source.getForDurationConversionWithString().get().toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetLocalDateTimeMapped(OptionalSource source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        Optional<LocalDateTime> localDateTime = source.getLocalDateTime();
        if ( localDateTime.isPresent() ) {
            target.setLocalDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( source.getLocalDateTime().get() ) );
        }
        Optional<ZonedDateTime> zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime.isPresent() ) {
            target.setZonedDateTime( DateTimeFormatter.ISO_DATE_TIME.format( source.getZonedDateTime().get() ) );
        }
        Optional<LocalDate> localDate = source.getLocalDate();
        if ( localDate.isPresent() ) {
            target.setLocalDate( DateTimeFormatter.ISO_LOCAL_DATE.format( source.getLocalDate().get() ) );
        }
        Optional<LocalTime> localTime = source.getLocalTime();
        if ( localTime.isPresent() ) {
            target.setLocalTime( DateTimeFormatter.ISO_LOCAL_TIME.format( source.getLocalTime().get() ) );
        }
        Optional<ZonedDateTime> forCalendarConversion = source.getForCalendarConversion();
        if ( forCalendarConversion.isPresent() ) {
            target.setForCalendarConversion( zonedDateTimeToCalendar( forCalendarConversion.get() ) );
        }
        Optional<ZonedDateTime> forDateConversionWithZonedDateTime = source.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime.isPresent() ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().get().toInstant() ) );
        }
        Optional<LocalDateTime> forDateConversionWithLocalDateTime = source.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime.isPresent() ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().get().toInstant( ZoneOffset.UTC ) ) );
        }
        Optional<LocalDate> forDateConversionWithLocalDate = source.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate.isPresent() ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        Optional<LocalDate> forSqlDateConversionWithLocalDate = source.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate.isPresent() ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        Optional<Instant> forDateConversionWithInstant = source.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant.isPresent() ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant().get() ) );
        }
        Optional<LocalDate> forLocalDateTimeConversionWithLocalDate = source.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate.isPresent() ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().get().atStartOfDay() );
        }
        Optional<Instant> forInstantConversionWithString = source.getForInstantConversionWithString();
        if ( forInstantConversionWithString.isPresent() ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().get().toString() );
        }
        Optional<Period> forPeriodConversionWithString = source.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString.isPresent() ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().get().toString() );
        }
        Optional<Duration> forDurationConversionWithString = source.getForDurationConversionWithString();
        if ( forDurationConversionWithString.isPresent() ) {
            target.setForDurationConversionWithString( source.getForDurationConversionWithString().get().toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetLocalDateMapped(OptionalSource source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        Optional<LocalDate> localDate = source.getLocalDate();
        if ( localDate.isPresent() ) {
            target.setLocalDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( source.getLocalDate().get() ) );
        }
        Optional<ZonedDateTime> zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime.isPresent() ) {
            target.setZonedDateTime( DateTimeFormatter.ISO_DATE_TIME.format( source.getZonedDateTime().get() ) );
        }
        Optional<LocalDateTime> localDateTime = source.getLocalDateTime();
        if ( localDateTime.isPresent() ) {
            target.setLocalDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( source.getLocalDateTime().get() ) );
        }
        Optional<LocalTime> localTime = source.getLocalTime();
        if ( localTime.isPresent() ) {
            target.setLocalTime( DateTimeFormatter.ISO_LOCAL_TIME.format( source.getLocalTime().get() ) );
        }
        Optional<ZonedDateTime> forCalendarConversion = source.getForCalendarConversion();
        if ( forCalendarConversion.isPresent() ) {
            target.setForCalendarConversion( zonedDateTimeToCalendar( forCalendarConversion.get() ) );
        }
        Optional<ZonedDateTime> forDateConversionWithZonedDateTime = source.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime.isPresent() ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().get().toInstant() ) );
        }
        Optional<LocalDateTime> forDateConversionWithLocalDateTime = source.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime.isPresent() ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().get().toInstant( ZoneOffset.UTC ) ) );
        }
        Optional<LocalDate> forDateConversionWithLocalDate = source.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate.isPresent() ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        Optional<LocalDate> forSqlDateConversionWithLocalDate = source.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate.isPresent() ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        Optional<Instant> forDateConversionWithInstant = source.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant.isPresent() ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant().get() ) );
        }
        Optional<LocalDate> forLocalDateTimeConversionWithLocalDate = source.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate.isPresent() ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().get().atStartOfDay() );
        }
        Optional<Instant> forInstantConversionWithString = source.getForInstantConversionWithString();
        if ( forInstantConversionWithString.isPresent() ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().get().toString() );
        }
        Optional<Period> forPeriodConversionWithString = source.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString.isPresent() ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().get().toString() );
        }
        Optional<Duration> forDurationConversionWithString = source.getForDurationConversionWithString();
        if ( forDurationConversionWithString.isPresent() ) {
            target.setForDurationConversionWithString( source.getForDurationConversionWithString().get().toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetLocalTimeMapped(OptionalSource source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        Optional<LocalTime> localTime = source.getLocalTime();
        if ( localTime.isPresent() ) {
            target.setLocalTime( dateTimeFormatter_HH_mm_168697690.format( source.getLocalTime().get() ) );
        }
        Optional<ZonedDateTime> zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime.isPresent() ) {
            target.setZonedDateTime( DateTimeFormatter.ISO_DATE_TIME.format( source.getZonedDateTime().get() ) );
        }
        Optional<LocalDateTime> localDateTime = source.getLocalDateTime();
        if ( localDateTime.isPresent() ) {
            target.setLocalDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( source.getLocalDateTime().get() ) );
        }
        Optional<LocalDate> localDate = source.getLocalDate();
        if ( localDate.isPresent() ) {
            target.setLocalDate( DateTimeFormatter.ISO_LOCAL_DATE.format( source.getLocalDate().get() ) );
        }
        Optional<ZonedDateTime> forCalendarConversion = source.getForCalendarConversion();
        if ( forCalendarConversion.isPresent() ) {
            target.setForCalendarConversion( zonedDateTimeToCalendar( forCalendarConversion.get() ) );
        }
        Optional<ZonedDateTime> forDateConversionWithZonedDateTime = source.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime.isPresent() ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().get().toInstant() ) );
        }
        Optional<LocalDateTime> forDateConversionWithLocalDateTime = source.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime.isPresent() ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().get().toInstant( ZoneOffset.UTC ) ) );
        }
        Optional<LocalDate> forDateConversionWithLocalDate = source.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate.isPresent() ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        Optional<LocalDate> forSqlDateConversionWithLocalDate = source.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate.isPresent() ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        Optional<Instant> forDateConversionWithInstant = source.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant.isPresent() ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant().get() ) );
        }
        Optional<LocalDate> forLocalDateTimeConversionWithLocalDate = source.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate.isPresent() ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().get().atStartOfDay() );
        }
        Optional<Instant> forInstantConversionWithString = source.getForInstantConversionWithString();
        if ( forInstantConversionWithString.isPresent() ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().get().toString() );
        }
        Optional<Period> forPeriodConversionWithString = source.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString.isPresent() ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().get().toString() );
        }
        Optional<Duration> forDurationConversionWithString = source.getForDurationConversionWithString();
        if ( forDurationConversionWithString.isPresent() ) {
            target.setForDurationConversionWithString( source.getForDurationConversionWithString().get().toString() );
        }

        return target;
    }

    @Override
    public OptionalSource targetToSource(Target target) {
        if ( target == null ) {
            return null;
        }

        OptionalSource optionalSource = new OptionalSource();

        String zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            optionalSource.setZonedDateTime( Optional.of( ZonedDateTime.parse( zonedDateTime, dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 ) ) );
        }
        String localDateTime = target.getLocalDateTime();
        if ( localDateTime != null ) {
            optionalSource.setLocalDateTime( Optional.of( LocalDateTime.parse( localDateTime, dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) ) );
        }
        String localDate = target.getLocalDate();
        if ( localDate != null ) {
            optionalSource.setLocalDate( Optional.of( LocalDate.parse( localDate, dateTimeFormatter_dd_MM_yyyy_11900521056 ) ) );
        }
        String localTime = target.getLocalTime();
        if ( localTime != null ) {
            optionalSource.setLocalTime( Optional.of( LocalTime.parse( localTime, dateTimeFormatter_HH_mm_168697690 ) ) );
        }
        Calendar forCalendarConversion = target.getForCalendarConversion();
        if ( forCalendarConversion != null ) {
            optionalSource.setForCalendarConversion( Optional.of( calendarToZonedDateTime( forCalendarConversion ) ) );
        }
        Date forDateConversionWithZonedDateTime = target.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            optionalSource.setForDateConversionWithZonedDateTime( Optional.of( ZonedDateTime.ofInstant( forDateConversionWithZonedDateTime.toInstant(), ZoneId.systemDefault() ) ) );
        }
        Date forDateConversionWithLocalDateTime = target.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            optionalSource.setForDateConversionWithLocalDateTime( Optional.of( LocalDateTime.ofInstant( forDateConversionWithLocalDateTime.toInstant(), ZoneId.of( "UTC" ) ) ) );
        }
        Date forDateConversionWithLocalDate = target.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            optionalSource.setForDateConversionWithLocalDate( Optional.of( LocalDateTime.ofInstant( forDateConversionWithLocalDate.toInstant(), ZoneOffset.UTC ).toLocalDate() ) );
        }
        java.sql.Date forSqlDateConversionWithLocalDate = target.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            optionalSource.setForSqlDateConversionWithLocalDate( Optional.of( forSqlDateConversionWithLocalDate.toLocalDate() ) );
        }
        Date forDateConversionWithInstant = target.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            optionalSource.setForDateConversionWithInstant( Optional.of( forDateConversionWithInstant.toInstant() ) );
        }
        LocalDateTime forLocalDateTimeConversionWithLocalDate = target.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            optionalSource.setForLocalDateTimeConversionWithLocalDate( Optional.of( forLocalDateTimeConversionWithLocalDate.toLocalDate() ) );
        }
        String forInstantConversionWithString = target.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            optionalSource.setForInstantConversionWithString( Optional.of( Instant.parse( forInstantConversionWithString ) ) );
        }
        String forPeriodConversionWithString = target.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            optionalSource.setForPeriodConversionWithString( Optional.of( Period.parse( forPeriodConversionWithString ) ) );
        }
        String forDurationConversionWithString = target.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            optionalSource.setForDurationConversionWithString( Optional.of( Duration.parse( forDurationConversionWithString ) ) );
        }

        return optionalSource;
    }

    @Override
    public OptionalSource targetToSourceDateTimeMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        OptionalSource optionalSource = new OptionalSource();

        String zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            optionalSource.setZonedDateTime( Optional.of( ZonedDateTime.parse( zonedDateTime, dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 ) ) );
        }
        String localDateTime = target.getLocalDateTime();
        if ( localDateTime != null ) {
            optionalSource.setLocalDateTime( Optional.of( LocalDateTime.parse( localDateTime ) ) );
        }
        String localDate = target.getLocalDate();
        if ( localDate != null ) {
            optionalSource.setLocalDate( Optional.of( LocalDate.parse( localDate ) ) );
        }
        String localTime = target.getLocalTime();
        if ( localTime != null ) {
            optionalSource.setLocalTime( Optional.of( LocalTime.parse( localTime ) ) );
        }
        Calendar forCalendarConversion = target.getForCalendarConversion();
        if ( forCalendarConversion != null ) {
            optionalSource.setForCalendarConversion( Optional.of( calendarToZonedDateTime( forCalendarConversion ) ) );
        }
        Date forDateConversionWithZonedDateTime = target.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            optionalSource.setForDateConversionWithZonedDateTime( Optional.of( ZonedDateTime.ofInstant( forDateConversionWithZonedDateTime.toInstant(), ZoneId.systemDefault() ) ) );
        }
        Date forDateConversionWithLocalDateTime = target.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            optionalSource.setForDateConversionWithLocalDateTime( Optional.of( LocalDateTime.ofInstant( forDateConversionWithLocalDateTime.toInstant(), ZoneId.of( "UTC" ) ) ) );
        }
        Date forDateConversionWithLocalDate = target.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            optionalSource.setForDateConversionWithLocalDate( Optional.of( LocalDateTime.ofInstant( forDateConversionWithLocalDate.toInstant(), ZoneOffset.UTC ).toLocalDate() ) );
        }
        java.sql.Date forSqlDateConversionWithLocalDate = target.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            optionalSource.setForSqlDateConversionWithLocalDate( Optional.of( forSqlDateConversionWithLocalDate.toLocalDate() ) );
        }
        Date forDateConversionWithInstant = target.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            optionalSource.setForDateConversionWithInstant( Optional.of( forDateConversionWithInstant.toInstant() ) );
        }
        LocalDateTime forLocalDateTimeConversionWithLocalDate = target.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            optionalSource.setForLocalDateTimeConversionWithLocalDate( Optional.of( forLocalDateTimeConversionWithLocalDate.toLocalDate() ) );
        }
        String forInstantConversionWithString = target.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            optionalSource.setForInstantConversionWithString( Optional.of( Instant.parse( forInstantConversionWithString ) ) );
        }
        String forPeriodConversionWithString = target.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            optionalSource.setForPeriodConversionWithString( Optional.of( Period.parse( forPeriodConversionWithString ) ) );
        }
        String forDurationConversionWithString = target.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            optionalSource.setForDurationConversionWithString( Optional.of( Duration.parse( forDurationConversionWithString ) ) );
        }

        return optionalSource;
    }

    @Override
    public OptionalSource targetToSourceLocalDateTimeMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        OptionalSource optionalSource = new OptionalSource();

        String localDateTime = target.getLocalDateTime();
        if ( localDateTime != null ) {
            optionalSource.setLocalDateTime( Optional.of( LocalDateTime.parse( localDateTime, dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) ) );
        }
        String zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            optionalSource.setZonedDateTime( Optional.of( ZonedDateTime.parse( zonedDateTime ) ) );
        }
        String localDate = target.getLocalDate();
        if ( localDate != null ) {
            optionalSource.setLocalDate( Optional.of( LocalDate.parse( localDate ) ) );
        }
        String localTime = target.getLocalTime();
        if ( localTime != null ) {
            optionalSource.setLocalTime( Optional.of( LocalTime.parse( localTime ) ) );
        }
        Calendar forCalendarConversion = target.getForCalendarConversion();
        if ( forCalendarConversion != null ) {
            optionalSource.setForCalendarConversion( Optional.of( calendarToZonedDateTime( forCalendarConversion ) ) );
        }
        Date forDateConversionWithZonedDateTime = target.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            optionalSource.setForDateConversionWithZonedDateTime( Optional.of( ZonedDateTime.ofInstant( forDateConversionWithZonedDateTime.toInstant(), ZoneId.systemDefault() ) ) );
        }
        Date forDateConversionWithLocalDateTime = target.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            optionalSource.setForDateConversionWithLocalDateTime( Optional.of( LocalDateTime.ofInstant( forDateConversionWithLocalDateTime.toInstant(), ZoneId.of( "UTC" ) ) ) );
        }
        Date forDateConversionWithLocalDate = target.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            optionalSource.setForDateConversionWithLocalDate( Optional.of( LocalDateTime.ofInstant( forDateConversionWithLocalDate.toInstant(), ZoneOffset.UTC ).toLocalDate() ) );
        }
        java.sql.Date forSqlDateConversionWithLocalDate = target.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            optionalSource.setForSqlDateConversionWithLocalDate( Optional.of( forSqlDateConversionWithLocalDate.toLocalDate() ) );
        }
        Date forDateConversionWithInstant = target.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            optionalSource.setForDateConversionWithInstant( Optional.of( forDateConversionWithInstant.toInstant() ) );
        }
        LocalDateTime forLocalDateTimeConversionWithLocalDate = target.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            optionalSource.setForLocalDateTimeConversionWithLocalDate( Optional.of( forLocalDateTimeConversionWithLocalDate.toLocalDate() ) );
        }
        String forInstantConversionWithString = target.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            optionalSource.setForInstantConversionWithString( Optional.of( Instant.parse( forInstantConversionWithString ) ) );
        }
        String forPeriodConversionWithString = target.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            optionalSource.setForPeriodConversionWithString( Optional.of( Period.parse( forPeriodConversionWithString ) ) );
        }
        String forDurationConversionWithString = target.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            optionalSource.setForDurationConversionWithString( Optional.of( Duration.parse( forDurationConversionWithString ) ) );
        }

        return optionalSource;
    }

    @Override
    public OptionalSource targetToSourceLocalDateMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        OptionalSource optionalSource = new OptionalSource();

        String localDate = target.getLocalDate();
        if ( localDate != null ) {
            optionalSource.setLocalDate( Optional.of( LocalDate.parse( localDate, dateTimeFormatter_dd_MM_yyyy_11900521056 ) ) );
        }
        String zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            optionalSource.setZonedDateTime( Optional.of( ZonedDateTime.parse( zonedDateTime ) ) );
        }
        String localDateTime = target.getLocalDateTime();
        if ( localDateTime != null ) {
            optionalSource.setLocalDateTime( Optional.of( LocalDateTime.parse( localDateTime ) ) );
        }
        String localTime = target.getLocalTime();
        if ( localTime != null ) {
            optionalSource.setLocalTime( Optional.of( LocalTime.parse( localTime ) ) );
        }
        Calendar forCalendarConversion = target.getForCalendarConversion();
        if ( forCalendarConversion != null ) {
            optionalSource.setForCalendarConversion( Optional.of( calendarToZonedDateTime( forCalendarConversion ) ) );
        }
        Date forDateConversionWithZonedDateTime = target.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            optionalSource.setForDateConversionWithZonedDateTime( Optional.of( ZonedDateTime.ofInstant( forDateConversionWithZonedDateTime.toInstant(), ZoneId.systemDefault() ) ) );
        }
        Date forDateConversionWithLocalDateTime = target.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            optionalSource.setForDateConversionWithLocalDateTime( Optional.of( LocalDateTime.ofInstant( forDateConversionWithLocalDateTime.toInstant(), ZoneId.of( "UTC" ) ) ) );
        }
        Date forDateConversionWithLocalDate = target.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            optionalSource.setForDateConversionWithLocalDate( Optional.of( LocalDateTime.ofInstant( forDateConversionWithLocalDate.toInstant(), ZoneOffset.UTC ).toLocalDate() ) );
        }
        java.sql.Date forSqlDateConversionWithLocalDate = target.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            optionalSource.setForSqlDateConversionWithLocalDate( Optional.of( forSqlDateConversionWithLocalDate.toLocalDate() ) );
        }
        Date forDateConversionWithInstant = target.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            optionalSource.setForDateConversionWithInstant( Optional.of( forDateConversionWithInstant.toInstant() ) );
        }
        LocalDateTime forLocalDateTimeConversionWithLocalDate = target.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            optionalSource.setForLocalDateTimeConversionWithLocalDate( Optional.of( forLocalDateTimeConversionWithLocalDate.toLocalDate() ) );
        }
        String forInstantConversionWithString = target.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            optionalSource.setForInstantConversionWithString( Optional.of( Instant.parse( forInstantConversionWithString ) ) );
        }
        String forPeriodConversionWithString = target.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            optionalSource.setForPeriodConversionWithString( Optional.of( Period.parse( forPeriodConversionWithString ) ) );
        }
        String forDurationConversionWithString = target.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            optionalSource.setForDurationConversionWithString( Optional.of( Duration.parse( forDurationConversionWithString ) ) );
        }

        return optionalSource;
    }

    @Override
    public OptionalSource targetToSourceLocalTimeMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        OptionalSource optionalSource = new OptionalSource();

        String localTime = target.getLocalTime();
        if ( localTime != null ) {
            optionalSource.setLocalTime( Optional.of( LocalTime.parse( localTime, dateTimeFormatter_HH_mm_168697690 ) ) );
        }
        String zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            optionalSource.setZonedDateTime( Optional.of( ZonedDateTime.parse( zonedDateTime ) ) );
        }
        String localDateTime = target.getLocalDateTime();
        if ( localDateTime != null ) {
            optionalSource.setLocalDateTime( Optional.of( LocalDateTime.parse( localDateTime ) ) );
        }
        String localDate = target.getLocalDate();
        if ( localDate != null ) {
            optionalSource.setLocalDate( Optional.of( LocalDate.parse( localDate ) ) );
        }
        Calendar forCalendarConversion = target.getForCalendarConversion();
        if ( forCalendarConversion != null ) {
            optionalSource.setForCalendarConversion( Optional.of( calendarToZonedDateTime( forCalendarConversion ) ) );
        }
        Date forDateConversionWithZonedDateTime = target.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            optionalSource.setForDateConversionWithZonedDateTime( Optional.of( ZonedDateTime.ofInstant( forDateConversionWithZonedDateTime.toInstant(), ZoneId.systemDefault() ) ) );
        }
        Date forDateConversionWithLocalDateTime = target.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            optionalSource.setForDateConversionWithLocalDateTime( Optional.of( LocalDateTime.ofInstant( forDateConversionWithLocalDateTime.toInstant(), ZoneId.of( "UTC" ) ) ) );
        }
        Date forDateConversionWithLocalDate = target.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            optionalSource.setForDateConversionWithLocalDate( Optional.of( LocalDateTime.ofInstant( forDateConversionWithLocalDate.toInstant(), ZoneOffset.UTC ).toLocalDate() ) );
        }
        java.sql.Date forSqlDateConversionWithLocalDate = target.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            optionalSource.setForSqlDateConversionWithLocalDate( Optional.of( forSqlDateConversionWithLocalDate.toLocalDate() ) );
        }
        Date forDateConversionWithInstant = target.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            optionalSource.setForDateConversionWithInstant( Optional.of( forDateConversionWithInstant.toInstant() ) );
        }
        LocalDateTime forLocalDateTimeConversionWithLocalDate = target.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            optionalSource.setForLocalDateTimeConversionWithLocalDate( Optional.of( forLocalDateTimeConversionWithLocalDate.toLocalDate() ) );
        }
        String forInstantConversionWithString = target.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            optionalSource.setForInstantConversionWithString( Optional.of( Instant.parse( forInstantConversionWithString ) ) );
        }
        String forPeriodConversionWithString = target.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            optionalSource.setForPeriodConversionWithString( Optional.of( Period.parse( forPeriodConversionWithString ) ) );
        }
        String forDurationConversionWithString = target.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            optionalSource.setForDurationConversionWithString( Optional.of( Duration.parse( forDurationConversionWithString ) ) );
        }

        return optionalSource;
    }

    @Override
    public OptionalSource targetToSourceDefaultMapping(Target target) {
        if ( target == null ) {
            return null;
        }

        OptionalSource optionalSource = new OptionalSource();

        String zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            optionalSource.setZonedDateTime( Optional.of( ZonedDateTime.parse( zonedDateTime, dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 ) ) );
        }
        String localDateTime = target.getLocalDateTime();
        if ( localDateTime != null ) {
            optionalSource.setLocalDateTime( Optional.of( LocalDateTime.parse( localDateTime, dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) ) );
        }
        String localDate = target.getLocalDate();
        if ( localDate != null ) {
            optionalSource.setLocalDate( Optional.of( LocalDate.parse( localDate, dateTimeFormatter_dd_MM_yyyy_11900521056 ) ) );
        }
        String localTime = target.getLocalTime();
        if ( localTime != null ) {
            optionalSource.setLocalTime( Optional.of( LocalTime.parse( localTime, dateTimeFormatter_HH_mm_168697690 ) ) );
        }
        Calendar forCalendarConversion = target.getForCalendarConversion();
        if ( forCalendarConversion != null ) {
            optionalSource.setForCalendarConversion( Optional.of( calendarToZonedDateTime( forCalendarConversion ) ) );
        }
        Date forDateConversionWithZonedDateTime = target.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            optionalSource.setForDateConversionWithZonedDateTime( Optional.of( ZonedDateTime.ofInstant( forDateConversionWithZonedDateTime.toInstant(), ZoneId.systemDefault() ) ) );
        }
        Date forDateConversionWithLocalDateTime = target.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            optionalSource.setForDateConversionWithLocalDateTime( Optional.of( LocalDateTime.ofInstant( forDateConversionWithLocalDateTime.toInstant(), ZoneId.of( "UTC" ) ) ) );
        }
        Date forDateConversionWithLocalDate = target.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            optionalSource.setForDateConversionWithLocalDate( Optional.of( LocalDateTime.ofInstant( forDateConversionWithLocalDate.toInstant(), ZoneOffset.UTC ).toLocalDate() ) );
        }
        java.sql.Date forSqlDateConversionWithLocalDate = target.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            optionalSource.setForSqlDateConversionWithLocalDate( Optional.of( forSqlDateConversionWithLocalDate.toLocalDate() ) );
        }
        Date forDateConversionWithInstant = target.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            optionalSource.setForDateConversionWithInstant( Optional.of( forDateConversionWithInstant.toInstant() ) );
        }
        LocalDateTime forLocalDateTimeConversionWithLocalDate = target.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            optionalSource.setForLocalDateTimeConversionWithLocalDate( Optional.of( forLocalDateTimeConversionWithLocalDate.toLocalDate() ) );
        }
        String forInstantConversionWithString = target.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            optionalSource.setForInstantConversionWithString( Optional.of( Instant.parse( forInstantConversionWithString ) ) );
        }
        String forPeriodConversionWithString = target.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            optionalSource.setForPeriodConversionWithString( Optional.of( Period.parse( forPeriodConversionWithString ) ) );
        }
        String forDurationConversionWithString = target.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            optionalSource.setForDurationConversionWithString( Optional.of( Duration.parse( forDurationConversionWithString ) ) );
        }

        return optionalSource;
    }

    private Calendar zonedDateTimeToCalendar(ZonedDateTime dateTime) {
        if ( dateTime == null ) {
            return null;
        }

        Calendar instance = Calendar.getInstance( TimeZone.getTimeZone( dateTime.getZone() ) );
        instance.setTimeInMillis( dateTime.toInstant().toEpochMilli() );
        return instance;
    }

    private ZonedDateTime calendarToZonedDateTime(Calendar cal) {
        if ( cal == null ) {
            return null;
        }

        return ZonedDateTime.ofInstant( cal.toInstant(), cal.getTimeZone().toZoneId() );
    }
}
