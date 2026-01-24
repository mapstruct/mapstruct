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
    date = "2026-01-24T16:06:24+0100",
    comments = "version: , compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
public class OptionalSourceTargetMapperImpl implements OptionalSourceTargetMapper {

    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 = DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" );
    private final DateTimeFormatter dateTimeFormatter_HH_mm_168697690 = DateTimeFormatter.ofPattern( "HH:mm" );
    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_11900521056 = DateTimeFormatter.ofPattern( "dd.MM.yyyy" );
    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 = DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm z" );

    @Override
    public Target sourceToTarget(OptionalSource source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getZonedDateTime().isPresent() ) {
            target.setZonedDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668.format( source.getZonedDateTime().get() ) );
        }
        if ( source.getLocalDateTime().isPresent() ) {
            target.setLocalDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( source.getLocalDateTime().get() ) );
        }
        if ( source.getLocalDate().isPresent() ) {
            target.setLocalDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( source.getLocalDate().get() ) );
        }
        if ( source.getLocalTime().isPresent() ) {
            target.setLocalTime( dateTimeFormatter_HH_mm_168697690.format( source.getLocalTime().get() ) );
        }
        if ( source.getForCalendarConversion().isPresent() ) {
            target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion().get() ) );
        }
        if ( source.getForDateConversionWithZonedDateTime().isPresent() ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().get().toInstant() ) );
        }
        if ( source.getForDateConversionWithLocalDateTime().isPresent() ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().get().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( source.getForDateConversionWithLocalDate().isPresent() ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( source.getForSqlDateConversionWithLocalDate().isPresent() ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        if ( source.getForDateConversionWithInstant().isPresent() ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant().get() ) );
        }
        if ( source.getForLocalDateTimeConversionWithLocalDate().isPresent() ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().get().atStartOfDay() );
        }
        if ( source.getForInstantConversionWithString().isPresent() ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().get().toString() );
        }
        if ( source.getForPeriodConversionWithString().isPresent() ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().get().toString() );
        }
        if ( source.getForDurationConversionWithString().isPresent() ) {
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

        if ( source.getZonedDateTime().isPresent() ) {
            target.setZonedDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668.format( source.getZonedDateTime().get() ) );
        }
        if ( source.getLocalDateTime().isPresent() ) {
            target.setLocalDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( source.getLocalDateTime().get() ) );
        }
        if ( source.getLocalDate().isPresent() ) {
            target.setLocalDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( source.getLocalDate().get() ) );
        }
        if ( source.getLocalTime().isPresent() ) {
            target.setLocalTime( dateTimeFormatter_HH_mm_168697690.format( source.getLocalTime().get() ) );
        }
        if ( source.getForCalendarConversion().isPresent() ) {
            target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion().get() ) );
        }
        if ( source.getForDateConversionWithZonedDateTime().isPresent() ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().get().toInstant() ) );
        }
        if ( source.getForDateConversionWithLocalDateTime().isPresent() ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().get().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( source.getForDateConversionWithLocalDate().isPresent() ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( source.getForSqlDateConversionWithLocalDate().isPresent() ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        if ( source.getForDateConversionWithInstant().isPresent() ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant().get() ) );
        }
        if ( source.getForLocalDateTimeConversionWithLocalDate().isPresent() ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().get().atStartOfDay() );
        }
        if ( source.getForInstantConversionWithString().isPresent() ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().get().toString() );
        }
        if ( source.getForPeriodConversionWithString().isPresent() ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().get().toString() );
        }
        if ( source.getForDurationConversionWithString().isPresent() ) {
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

        if ( source.getZonedDateTime().isPresent() ) {
            target.setZonedDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668.format( source.getZonedDateTime().get() ) );
        }
        if ( source.getLocalDateTime().isPresent() ) {
            target.setLocalDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( source.getLocalDateTime().get() ) );
        }
        if ( source.getLocalDate().isPresent() ) {
            target.setLocalDate( DateTimeFormatter.ISO_LOCAL_DATE.format( source.getLocalDate().get() ) );
        }
        if ( source.getLocalTime().isPresent() ) {
            target.setLocalTime( DateTimeFormatter.ISO_LOCAL_TIME.format( source.getLocalTime().get() ) );
        }
        if ( source.getForCalendarConversion().isPresent() ) {
            target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion().get() ) );
        }
        if ( source.getForDateConversionWithZonedDateTime().isPresent() ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().get().toInstant() ) );
        }
        if ( source.getForDateConversionWithLocalDateTime().isPresent() ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().get().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( source.getForDateConversionWithLocalDate().isPresent() ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( source.getForSqlDateConversionWithLocalDate().isPresent() ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        if ( source.getForDateConversionWithInstant().isPresent() ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant().get() ) );
        }
        if ( source.getForLocalDateTimeConversionWithLocalDate().isPresent() ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().get().atStartOfDay() );
        }
        if ( source.getForInstantConversionWithString().isPresent() ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().get().toString() );
        }
        if ( source.getForPeriodConversionWithString().isPresent() ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().get().toString() );
        }
        if ( source.getForDurationConversionWithString().isPresent() ) {
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

        if ( source.getLocalDateTime().isPresent() ) {
            target.setLocalDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( source.getLocalDateTime().get() ) );
        }
        if ( source.getZonedDateTime().isPresent() ) {
            target.setZonedDateTime( DateTimeFormatter.ISO_DATE_TIME.format( source.getZonedDateTime().get() ) );
        }
        if ( source.getLocalDate().isPresent() ) {
            target.setLocalDate( DateTimeFormatter.ISO_LOCAL_DATE.format( source.getLocalDate().get() ) );
        }
        if ( source.getLocalTime().isPresent() ) {
            target.setLocalTime( DateTimeFormatter.ISO_LOCAL_TIME.format( source.getLocalTime().get() ) );
        }
        if ( source.getForCalendarConversion().isPresent() ) {
            target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion().get() ) );
        }
        if ( source.getForDateConversionWithZonedDateTime().isPresent() ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().get().toInstant() ) );
        }
        if ( source.getForDateConversionWithLocalDateTime().isPresent() ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().get().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( source.getForDateConversionWithLocalDate().isPresent() ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( source.getForSqlDateConversionWithLocalDate().isPresent() ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        if ( source.getForDateConversionWithInstant().isPresent() ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant().get() ) );
        }
        if ( source.getForLocalDateTimeConversionWithLocalDate().isPresent() ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().get().atStartOfDay() );
        }
        if ( source.getForInstantConversionWithString().isPresent() ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().get().toString() );
        }
        if ( source.getForPeriodConversionWithString().isPresent() ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().get().toString() );
        }
        if ( source.getForDurationConversionWithString().isPresent() ) {
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

        if ( source.getLocalDate().isPresent() ) {
            target.setLocalDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( source.getLocalDate().get() ) );
        }
        if ( source.getZonedDateTime().isPresent() ) {
            target.setZonedDateTime( DateTimeFormatter.ISO_DATE_TIME.format( source.getZonedDateTime().get() ) );
        }
        if ( source.getLocalDateTime().isPresent() ) {
            target.setLocalDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( source.getLocalDateTime().get() ) );
        }
        if ( source.getLocalTime().isPresent() ) {
            target.setLocalTime( DateTimeFormatter.ISO_LOCAL_TIME.format( source.getLocalTime().get() ) );
        }
        if ( source.getForCalendarConversion().isPresent() ) {
            target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion().get() ) );
        }
        if ( source.getForDateConversionWithZonedDateTime().isPresent() ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().get().toInstant() ) );
        }
        if ( source.getForDateConversionWithLocalDateTime().isPresent() ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().get().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( source.getForDateConversionWithLocalDate().isPresent() ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( source.getForSqlDateConversionWithLocalDate().isPresent() ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        if ( source.getForDateConversionWithInstant().isPresent() ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant().get() ) );
        }
        if ( source.getForLocalDateTimeConversionWithLocalDate().isPresent() ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().get().atStartOfDay() );
        }
        if ( source.getForInstantConversionWithString().isPresent() ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().get().toString() );
        }
        if ( source.getForPeriodConversionWithString().isPresent() ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().get().toString() );
        }
        if ( source.getForDurationConversionWithString().isPresent() ) {
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

        if ( source.getLocalTime().isPresent() ) {
            target.setLocalTime( dateTimeFormatter_HH_mm_168697690.format( source.getLocalTime().get() ) );
        }
        if ( source.getZonedDateTime().isPresent() ) {
            target.setZonedDateTime( DateTimeFormatter.ISO_DATE_TIME.format( source.getZonedDateTime().get() ) );
        }
        if ( source.getLocalDateTime().isPresent() ) {
            target.setLocalDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( source.getLocalDateTime().get() ) );
        }
        if ( source.getLocalDate().isPresent() ) {
            target.setLocalDate( DateTimeFormatter.ISO_LOCAL_DATE.format( source.getLocalDate().get() ) );
        }
        if ( source.getForCalendarConversion().isPresent() ) {
            target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion().get() ) );
        }
        if ( source.getForDateConversionWithZonedDateTime().isPresent() ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().get().toInstant() ) );
        }
        if ( source.getForDateConversionWithLocalDateTime().isPresent() ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().get().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( source.getForDateConversionWithLocalDate().isPresent() ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( source.getForSqlDateConversionWithLocalDate().isPresent() ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().get().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        if ( source.getForDateConversionWithInstant().isPresent() ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant().get() ) );
        }
        if ( source.getForLocalDateTimeConversionWithLocalDate().isPresent() ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().get().atStartOfDay() );
        }
        if ( source.getForInstantConversionWithString().isPresent() ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().get().toString() );
        }
        if ( source.getForPeriodConversionWithString().isPresent() ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().get().toString() );
        }
        if ( source.getForDurationConversionWithString().isPresent() ) {
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

        if ( target.getZonedDateTime() != null ) {
            optionalSource.setZonedDateTime( Optional.of( ZonedDateTime.parse( target.getZonedDateTime(), dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 ) ) );
        }
        if ( target.getLocalDateTime() != null ) {
            optionalSource.setLocalDateTime( Optional.of( LocalDateTime.parse( target.getLocalDateTime(), dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) ) );
        }
        if ( target.getLocalDate() != null ) {
            optionalSource.setLocalDate( Optional.of( LocalDate.parse( target.getLocalDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) ) );
        }
        if ( target.getLocalTime() != null ) {
            optionalSource.setLocalTime( Optional.of( LocalTime.parse( target.getLocalTime(), dateTimeFormatter_HH_mm_168697690 ) ) );
        }
        if ( target.getForCalendarConversion() != null ) {
            optionalSource.setForCalendarConversion( Optional.of( calendarToZonedDateTime( target.getForCalendarConversion() ) ) );
        }
        if ( target.getForDateConversionWithZonedDateTime() != null ) {
            optionalSource.setForDateConversionWithZonedDateTime( Optional.of( ZonedDateTime.ofInstant( target.getForDateConversionWithZonedDateTime().toInstant(), ZoneId.systemDefault() ) ) );
        }
        if ( target.getForDateConversionWithLocalDateTime() != null ) {
            optionalSource.setForDateConversionWithLocalDateTime( Optional.of( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) ) );
        }
        if ( target.getForDateConversionWithLocalDate() != null ) {
            optionalSource.setForDateConversionWithLocalDate( Optional.of( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() ) );
        }
        if ( target.getForSqlDateConversionWithLocalDate() != null ) {
            optionalSource.setForSqlDateConversionWithLocalDate( Optional.of( target.getForSqlDateConversionWithLocalDate().toLocalDate() ) );
        }
        if ( target.getForDateConversionWithInstant() != null ) {
            optionalSource.setForDateConversionWithInstant( Optional.of( target.getForDateConversionWithInstant().toInstant() ) );
        }
        if ( target.getForLocalDateTimeConversionWithLocalDate() != null ) {
            optionalSource.setForLocalDateTimeConversionWithLocalDate( Optional.of( target.getForLocalDateTimeConversionWithLocalDate().toLocalDate() ) );
        }
        if ( target.getForInstantConversionWithString() != null ) {
            optionalSource.setForInstantConversionWithString( Optional.of( Instant.parse( target.getForInstantConversionWithString() ) ) );
        }
        if ( target.getForPeriodConversionWithString() != null ) {
            optionalSource.setForPeriodConversionWithString( Optional.of( Period.parse( target.getForPeriodConversionWithString() ) ) );
        }
        if ( target.getForDurationConversionWithString() != null ) {
            optionalSource.setForDurationConversionWithString( Optional.of( Duration.parse( target.getForDurationConversionWithString() ) ) );
        }

        return optionalSource;
    }

    @Override
    public OptionalSource targetToSourceDateTimeMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        OptionalSource optionalSource = new OptionalSource();

        if ( target.getZonedDateTime() != null ) {
            optionalSource.setZonedDateTime( Optional.of( ZonedDateTime.parse( target.getZonedDateTime(), dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 ) ) );
        }
        if ( target.getLocalDateTime() != null ) {
            optionalSource.setLocalDateTime( Optional.of( LocalDateTime.parse( target.getLocalDateTime() ) ) );
        }
        if ( target.getLocalDate() != null ) {
            optionalSource.setLocalDate( Optional.of( LocalDate.parse( target.getLocalDate() ) ) );
        }
        if ( target.getLocalTime() != null ) {
            optionalSource.setLocalTime( Optional.of( LocalTime.parse( target.getLocalTime() ) ) );
        }
        if ( target.getForCalendarConversion() != null ) {
            optionalSource.setForCalendarConversion( Optional.of( calendarToZonedDateTime( target.getForCalendarConversion() ) ) );
        }
        if ( target.getForDateConversionWithZonedDateTime() != null ) {
            optionalSource.setForDateConversionWithZonedDateTime( Optional.of( ZonedDateTime.ofInstant( target.getForDateConversionWithZonedDateTime().toInstant(), ZoneId.systemDefault() ) ) );
        }
        if ( target.getForDateConversionWithLocalDateTime() != null ) {
            optionalSource.setForDateConversionWithLocalDateTime( Optional.of( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) ) );
        }
        if ( target.getForDateConversionWithLocalDate() != null ) {
            optionalSource.setForDateConversionWithLocalDate( Optional.of( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() ) );
        }
        if ( target.getForSqlDateConversionWithLocalDate() != null ) {
            optionalSource.setForSqlDateConversionWithLocalDate( Optional.of( target.getForSqlDateConversionWithLocalDate().toLocalDate() ) );
        }
        if ( target.getForDateConversionWithInstant() != null ) {
            optionalSource.setForDateConversionWithInstant( Optional.of( target.getForDateConversionWithInstant().toInstant() ) );
        }
        if ( target.getForLocalDateTimeConversionWithLocalDate() != null ) {
            optionalSource.setForLocalDateTimeConversionWithLocalDate( Optional.of( target.getForLocalDateTimeConversionWithLocalDate().toLocalDate() ) );
        }
        if ( target.getForInstantConversionWithString() != null ) {
            optionalSource.setForInstantConversionWithString( Optional.of( Instant.parse( target.getForInstantConversionWithString() ) ) );
        }
        if ( target.getForPeriodConversionWithString() != null ) {
            optionalSource.setForPeriodConversionWithString( Optional.of( Period.parse( target.getForPeriodConversionWithString() ) ) );
        }
        if ( target.getForDurationConversionWithString() != null ) {
            optionalSource.setForDurationConversionWithString( Optional.of( Duration.parse( target.getForDurationConversionWithString() ) ) );
        }

        return optionalSource;
    }

    @Override
    public OptionalSource targetToSourceLocalDateTimeMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        OptionalSource optionalSource = new OptionalSource();

        if ( target.getLocalDateTime() != null ) {
            optionalSource.setLocalDateTime( Optional.of( LocalDateTime.parse( target.getLocalDateTime(), dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) ) );
        }
        if ( target.getZonedDateTime() != null ) {
            optionalSource.setZonedDateTime( Optional.of( ZonedDateTime.parse( target.getZonedDateTime() ) ) );
        }
        if ( target.getLocalDate() != null ) {
            optionalSource.setLocalDate( Optional.of( LocalDate.parse( target.getLocalDate() ) ) );
        }
        if ( target.getLocalTime() != null ) {
            optionalSource.setLocalTime( Optional.of( LocalTime.parse( target.getLocalTime() ) ) );
        }
        if ( target.getForCalendarConversion() != null ) {
            optionalSource.setForCalendarConversion( Optional.of( calendarToZonedDateTime( target.getForCalendarConversion() ) ) );
        }
        if ( target.getForDateConversionWithZonedDateTime() != null ) {
            optionalSource.setForDateConversionWithZonedDateTime( Optional.of( ZonedDateTime.ofInstant( target.getForDateConversionWithZonedDateTime().toInstant(), ZoneId.systemDefault() ) ) );
        }
        if ( target.getForDateConversionWithLocalDateTime() != null ) {
            optionalSource.setForDateConversionWithLocalDateTime( Optional.of( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) ) );
        }
        if ( target.getForDateConversionWithLocalDate() != null ) {
            optionalSource.setForDateConversionWithLocalDate( Optional.of( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() ) );
        }
        if ( target.getForSqlDateConversionWithLocalDate() != null ) {
            optionalSource.setForSqlDateConversionWithLocalDate( Optional.of( target.getForSqlDateConversionWithLocalDate().toLocalDate() ) );
        }
        if ( target.getForDateConversionWithInstant() != null ) {
            optionalSource.setForDateConversionWithInstant( Optional.of( target.getForDateConversionWithInstant().toInstant() ) );
        }
        if ( target.getForLocalDateTimeConversionWithLocalDate() != null ) {
            optionalSource.setForLocalDateTimeConversionWithLocalDate( Optional.of( target.getForLocalDateTimeConversionWithLocalDate().toLocalDate() ) );
        }
        if ( target.getForInstantConversionWithString() != null ) {
            optionalSource.setForInstantConversionWithString( Optional.of( Instant.parse( target.getForInstantConversionWithString() ) ) );
        }
        if ( target.getForPeriodConversionWithString() != null ) {
            optionalSource.setForPeriodConversionWithString( Optional.of( Period.parse( target.getForPeriodConversionWithString() ) ) );
        }
        if ( target.getForDurationConversionWithString() != null ) {
            optionalSource.setForDurationConversionWithString( Optional.of( Duration.parse( target.getForDurationConversionWithString() ) ) );
        }

        return optionalSource;
    }

    @Override
    public OptionalSource targetToSourceLocalDateMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        OptionalSource optionalSource = new OptionalSource();

        if ( target.getLocalDate() != null ) {
            optionalSource.setLocalDate( Optional.of( LocalDate.parse( target.getLocalDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) ) );
        }
        if ( target.getZonedDateTime() != null ) {
            optionalSource.setZonedDateTime( Optional.of( ZonedDateTime.parse( target.getZonedDateTime() ) ) );
        }
        if ( target.getLocalDateTime() != null ) {
            optionalSource.setLocalDateTime( Optional.of( LocalDateTime.parse( target.getLocalDateTime() ) ) );
        }
        if ( target.getLocalTime() != null ) {
            optionalSource.setLocalTime( Optional.of( LocalTime.parse( target.getLocalTime() ) ) );
        }
        if ( target.getForCalendarConversion() != null ) {
            optionalSource.setForCalendarConversion( Optional.of( calendarToZonedDateTime( target.getForCalendarConversion() ) ) );
        }
        if ( target.getForDateConversionWithZonedDateTime() != null ) {
            optionalSource.setForDateConversionWithZonedDateTime( Optional.of( ZonedDateTime.ofInstant( target.getForDateConversionWithZonedDateTime().toInstant(), ZoneId.systemDefault() ) ) );
        }
        if ( target.getForDateConversionWithLocalDateTime() != null ) {
            optionalSource.setForDateConversionWithLocalDateTime( Optional.of( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) ) );
        }
        if ( target.getForDateConversionWithLocalDate() != null ) {
            optionalSource.setForDateConversionWithLocalDate( Optional.of( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() ) );
        }
        if ( target.getForSqlDateConversionWithLocalDate() != null ) {
            optionalSource.setForSqlDateConversionWithLocalDate( Optional.of( target.getForSqlDateConversionWithLocalDate().toLocalDate() ) );
        }
        if ( target.getForDateConversionWithInstant() != null ) {
            optionalSource.setForDateConversionWithInstant( Optional.of( target.getForDateConversionWithInstant().toInstant() ) );
        }
        if ( target.getForLocalDateTimeConversionWithLocalDate() != null ) {
            optionalSource.setForLocalDateTimeConversionWithLocalDate( Optional.of( target.getForLocalDateTimeConversionWithLocalDate().toLocalDate() ) );
        }
        if ( target.getForInstantConversionWithString() != null ) {
            optionalSource.setForInstantConversionWithString( Optional.of( Instant.parse( target.getForInstantConversionWithString() ) ) );
        }
        if ( target.getForPeriodConversionWithString() != null ) {
            optionalSource.setForPeriodConversionWithString( Optional.of( Period.parse( target.getForPeriodConversionWithString() ) ) );
        }
        if ( target.getForDurationConversionWithString() != null ) {
            optionalSource.setForDurationConversionWithString( Optional.of( Duration.parse( target.getForDurationConversionWithString() ) ) );
        }

        return optionalSource;
    }

    @Override
    public OptionalSource targetToSourceLocalTimeMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        OptionalSource optionalSource = new OptionalSource();

        if ( target.getLocalTime() != null ) {
            optionalSource.setLocalTime( Optional.of( LocalTime.parse( target.getLocalTime(), dateTimeFormatter_HH_mm_168697690 ) ) );
        }
        if ( target.getZonedDateTime() != null ) {
            optionalSource.setZonedDateTime( Optional.of( ZonedDateTime.parse( target.getZonedDateTime() ) ) );
        }
        if ( target.getLocalDateTime() != null ) {
            optionalSource.setLocalDateTime( Optional.of( LocalDateTime.parse( target.getLocalDateTime() ) ) );
        }
        if ( target.getLocalDate() != null ) {
            optionalSource.setLocalDate( Optional.of( LocalDate.parse( target.getLocalDate() ) ) );
        }
        if ( target.getForCalendarConversion() != null ) {
            optionalSource.setForCalendarConversion( Optional.of( calendarToZonedDateTime( target.getForCalendarConversion() ) ) );
        }
        if ( target.getForDateConversionWithZonedDateTime() != null ) {
            optionalSource.setForDateConversionWithZonedDateTime( Optional.of( ZonedDateTime.ofInstant( target.getForDateConversionWithZonedDateTime().toInstant(), ZoneId.systemDefault() ) ) );
        }
        if ( target.getForDateConversionWithLocalDateTime() != null ) {
            optionalSource.setForDateConversionWithLocalDateTime( Optional.of( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) ) );
        }
        if ( target.getForDateConversionWithLocalDate() != null ) {
            optionalSource.setForDateConversionWithLocalDate( Optional.of( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() ) );
        }
        if ( target.getForSqlDateConversionWithLocalDate() != null ) {
            optionalSource.setForSqlDateConversionWithLocalDate( Optional.of( target.getForSqlDateConversionWithLocalDate().toLocalDate() ) );
        }
        if ( target.getForDateConversionWithInstant() != null ) {
            optionalSource.setForDateConversionWithInstant( Optional.of( target.getForDateConversionWithInstant().toInstant() ) );
        }
        if ( target.getForLocalDateTimeConversionWithLocalDate() != null ) {
            optionalSource.setForLocalDateTimeConversionWithLocalDate( Optional.of( target.getForLocalDateTimeConversionWithLocalDate().toLocalDate() ) );
        }
        if ( target.getForInstantConversionWithString() != null ) {
            optionalSource.setForInstantConversionWithString( Optional.of( Instant.parse( target.getForInstantConversionWithString() ) ) );
        }
        if ( target.getForPeriodConversionWithString() != null ) {
            optionalSource.setForPeriodConversionWithString( Optional.of( Period.parse( target.getForPeriodConversionWithString() ) ) );
        }
        if ( target.getForDurationConversionWithString() != null ) {
            optionalSource.setForDurationConversionWithString( Optional.of( Duration.parse( target.getForDurationConversionWithString() ) ) );
        }

        return optionalSource;
    }

    @Override
    public OptionalSource targetToSourceDefaultMapping(Target target) {
        if ( target == null ) {
            return null;
        }

        OptionalSource optionalSource = new OptionalSource();

        if ( target.getZonedDateTime() != null ) {
            optionalSource.setZonedDateTime( Optional.of( ZonedDateTime.parse( target.getZonedDateTime(), dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 ) ) );
        }
        if ( target.getLocalDateTime() != null ) {
            optionalSource.setLocalDateTime( Optional.of( LocalDateTime.parse( target.getLocalDateTime(), dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) ) );
        }
        if ( target.getLocalDate() != null ) {
            optionalSource.setLocalDate( Optional.of( LocalDate.parse( target.getLocalDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) ) );
        }
        if ( target.getLocalTime() != null ) {
            optionalSource.setLocalTime( Optional.of( LocalTime.parse( target.getLocalTime(), dateTimeFormatter_HH_mm_168697690 ) ) );
        }
        if ( target.getForCalendarConversion() != null ) {
            optionalSource.setForCalendarConversion( Optional.of( calendarToZonedDateTime( target.getForCalendarConversion() ) ) );
        }
        if ( target.getForDateConversionWithZonedDateTime() != null ) {
            optionalSource.setForDateConversionWithZonedDateTime( Optional.of( ZonedDateTime.ofInstant( target.getForDateConversionWithZonedDateTime().toInstant(), ZoneId.systemDefault() ) ) );
        }
        if ( target.getForDateConversionWithLocalDateTime() != null ) {
            optionalSource.setForDateConversionWithLocalDateTime( Optional.of( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) ) );
        }
        if ( target.getForDateConversionWithLocalDate() != null ) {
            optionalSource.setForDateConversionWithLocalDate( Optional.of( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() ) );
        }
        if ( target.getForSqlDateConversionWithLocalDate() != null ) {
            optionalSource.setForSqlDateConversionWithLocalDate( Optional.of( target.getForSqlDateConversionWithLocalDate().toLocalDate() ) );
        }
        if ( target.getForDateConversionWithInstant() != null ) {
            optionalSource.setForDateConversionWithInstant( Optional.of( target.getForDateConversionWithInstant().toInstant() ) );
        }
        if ( target.getForLocalDateTimeConversionWithLocalDate() != null ) {
            optionalSource.setForLocalDateTimeConversionWithLocalDate( Optional.of( target.getForLocalDateTimeConversionWithLocalDate().toLocalDate() ) );
        }
        if ( target.getForInstantConversionWithString() != null ) {
            optionalSource.setForInstantConversionWithString( Optional.of( Instant.parse( target.getForInstantConversionWithString() ) ) );
        }
        if ( target.getForPeriodConversionWithString() != null ) {
            optionalSource.setForPeriodConversionWithString( Optional.of( Period.parse( target.getForPeriodConversionWithString() ) ) );
        }
        if ( target.getForDurationConversionWithString() != null ) {
            optionalSource.setForDurationConversionWithString( Optional.of( Duration.parse( target.getForDurationConversionWithString() ) ) );
        }

        return optionalSource;
    }

    private ZonedDateTime calendarToZonedDateTime(Calendar cal) {
        if ( cal == null ) {
            return null;
        }

        return ZonedDateTime.ofInstant( cal.toInstant(), cal.getTimeZone().toZoneId() );
    }

    private Calendar zonedDateTimeToCalendar(ZonedDateTime dateTime) {
        if ( dateTime == null ) {
            return null;
        }

        Calendar instance = Calendar.getInstance( TimeZone.getTimeZone( dateTime.getZone() ) );
        instance.setTimeInMillis( dateTime.toInstant().toEpochMilli() );
        return instance;
    }
}
