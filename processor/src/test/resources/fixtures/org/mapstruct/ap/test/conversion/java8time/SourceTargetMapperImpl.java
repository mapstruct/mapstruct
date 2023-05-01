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
import java.util.TimeZone;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-15T18:24:04+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_275 (AdoptOpenJDK)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 = DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" );
    private final DateTimeFormatter dateTimeFormatter_HH_mm_168697690 = DateTimeFormatter.ofPattern( "HH:mm" );
    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_11900521056 = DateTimeFormatter.ofPattern( "dd.MM.yyyy" );
    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 = DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm z" );

    @Override
    public Target sourceToTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getZonedDateTime() != null ) {
            target.setZonedDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668.format( source.getZonedDateTime() ) );
        }
        if ( source.getLocalDateTime() != null ) {
            target.setLocalDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( source.getLocalDateTime() ) );
        }
        if ( source.getLocalDate() != null ) {
            target.setLocalDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( source.getLocalDate() ) );
        }
        if ( source.getLocalTime() != null ) {
            target.setLocalTime( dateTimeFormatter_HH_mm_168697690.format( source.getLocalTime() ) );
        }
        target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion() ) );
        if ( source.getForDateConversionWithZonedDateTime() != null ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().toInstant() ) );
        }
        if ( source.getForDateConversionWithLocalDateTime() != null ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( source.getForDateConversionWithLocalDate() != null ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( source.getForSqlDateConversionWithLocalDate() != null ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        if ( source.getForDateConversionWithInstant() != null ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant() ) );
        }
        if ( source.getForLocalDateTimeConversionWithLocalDate() != null ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().atStartOfDay() );
        }
        if ( source.getForInstantConversionWithString() != null ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().toString() );
        }
        if ( source.getForPeriodConversionWithString() != null ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().toString() );
        }
        if ( source.getForDurationConversionWithString() != null ) {
            target.setForDurationConversionWithString( source.getForDurationConversionWithString().toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetDefaultMapping(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getZonedDateTime() != null ) {
            target.setZonedDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668.format( source.getZonedDateTime() ) );
        }
        if ( source.getLocalDateTime() != null ) {
            target.setLocalDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( source.getLocalDateTime() ) );
        }
        if ( source.getLocalDate() != null ) {
            target.setLocalDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( source.getLocalDate() ) );
        }
        if ( source.getLocalTime() != null ) {
            target.setLocalTime( dateTimeFormatter_HH_mm_168697690.format( source.getLocalTime() ) );
        }
        target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion() ) );
        if ( source.getForDateConversionWithZonedDateTime() != null ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().toInstant() ) );
        }
        if ( source.getForDateConversionWithLocalDateTime() != null ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( source.getForDateConversionWithLocalDate() != null ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( source.getForSqlDateConversionWithLocalDate() != null ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        if ( source.getForDateConversionWithInstant() != null ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant() ) );
        }
        if ( source.getForLocalDateTimeConversionWithLocalDate() != null ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().atStartOfDay() );
        }
        if ( source.getForInstantConversionWithString() != null ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().toString() );
        }
        if ( source.getForPeriodConversionWithString() != null ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().toString() );
        }
        if ( source.getForDurationConversionWithString() != null ) {
            target.setForDurationConversionWithString( source.getForDurationConversionWithString().toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetDateTimeMapped(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getZonedDateTime() != null ) {
            target.setZonedDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668.format( source.getZonedDateTime() ) );
        }
        if ( source.getLocalDateTime() != null ) {
            target.setLocalDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( source.getLocalDateTime() ) );
        }
        if ( source.getLocalDate() != null ) {
            target.setLocalDate( DateTimeFormatter.ISO_LOCAL_DATE.format( source.getLocalDate() ) );
        }
        if ( source.getLocalTime() != null ) {
            target.setLocalTime( DateTimeFormatter.ISO_LOCAL_TIME.format( source.getLocalTime() ) );
        }
        target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion() ) );
        if ( source.getForDateConversionWithZonedDateTime() != null ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().toInstant() ) );
        }
        if ( source.getForDateConversionWithLocalDateTime() != null ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( source.getForDateConversionWithLocalDate() != null ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( source.getForSqlDateConversionWithLocalDate() != null ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        if ( source.getForDateConversionWithInstant() != null ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant() ) );
        }
        if ( source.getForLocalDateTimeConversionWithLocalDate() != null ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().atStartOfDay() );
        }
        if ( source.getForInstantConversionWithString() != null ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().toString() );
        }
        if ( source.getForPeriodConversionWithString() != null ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().toString() );
        }
        if ( source.getForDurationConversionWithString() != null ) {
            target.setForDurationConversionWithString( source.getForDurationConversionWithString().toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetLocalDateTimeMapped(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getLocalDateTime() != null ) {
            target.setLocalDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( source.getLocalDateTime() ) );
        }
        if ( source.getZonedDateTime() != null ) {
            target.setZonedDateTime( DateTimeFormatter.ISO_DATE_TIME.format( source.getZonedDateTime() ) );
        }
        if ( source.getLocalDate() != null ) {
            target.setLocalDate( DateTimeFormatter.ISO_LOCAL_DATE.format( source.getLocalDate() ) );
        }
        if ( source.getLocalTime() != null ) {
            target.setLocalTime( DateTimeFormatter.ISO_LOCAL_TIME.format( source.getLocalTime() ) );
        }
        target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion() ) );
        if ( source.getForDateConversionWithZonedDateTime() != null ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().toInstant() ) );
        }
        if ( source.getForDateConversionWithLocalDateTime() != null ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( source.getForDateConversionWithLocalDate() != null ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( source.getForSqlDateConversionWithLocalDate() != null ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        if ( source.getForDateConversionWithInstant() != null ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant() ) );
        }
        if ( source.getForLocalDateTimeConversionWithLocalDate() != null ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().atStartOfDay() );
        }
        if ( source.getForInstantConversionWithString() != null ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().toString() );
        }
        if ( source.getForPeriodConversionWithString() != null ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().toString() );
        }
        if ( source.getForDurationConversionWithString() != null ) {
            target.setForDurationConversionWithString( source.getForDurationConversionWithString().toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetLocalDateMapped(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getLocalDate() != null ) {
            target.setLocalDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( source.getLocalDate() ) );
        }
        if ( source.getZonedDateTime() != null ) {
            target.setZonedDateTime( DateTimeFormatter.ISO_DATE_TIME.format( source.getZonedDateTime() ) );
        }
        if ( source.getLocalDateTime() != null ) {
            target.setLocalDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( source.getLocalDateTime() ) );
        }
        if ( source.getLocalTime() != null ) {
            target.setLocalTime( DateTimeFormatter.ISO_LOCAL_TIME.format( source.getLocalTime() ) );
        }
        target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion() ) );
        if ( source.getForDateConversionWithZonedDateTime() != null ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().toInstant() ) );
        }
        if ( source.getForDateConversionWithLocalDateTime() != null ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( source.getForDateConversionWithLocalDate() != null ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( source.getForSqlDateConversionWithLocalDate() != null ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        if ( source.getForDateConversionWithInstant() != null ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant() ) );
        }
        if ( source.getForLocalDateTimeConversionWithLocalDate() != null ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().atStartOfDay() );
        }
        if ( source.getForInstantConversionWithString() != null ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().toString() );
        }
        if ( source.getForPeriodConversionWithString() != null ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().toString() );
        }
        if ( source.getForDurationConversionWithString() != null ) {
            target.setForDurationConversionWithString( source.getForDurationConversionWithString().toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetLocalTimeMapped(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getLocalTime() != null ) {
            target.setLocalTime( dateTimeFormatter_HH_mm_168697690.format( source.getLocalTime() ) );
        }
        if ( source.getZonedDateTime() != null ) {
            target.setZonedDateTime( DateTimeFormatter.ISO_DATE_TIME.format( source.getZonedDateTime() ) );
        }
        if ( source.getLocalDateTime() != null ) {
            target.setLocalDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( source.getLocalDateTime() ) );
        }
        if ( source.getLocalDate() != null ) {
            target.setLocalDate( DateTimeFormatter.ISO_LOCAL_DATE.format( source.getLocalDate() ) );
        }
        target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion() ) );
        if ( source.getForDateConversionWithZonedDateTime() != null ) {
            target.setForDateConversionWithZonedDateTime( Date.from( source.getForDateConversionWithZonedDateTime().toInstant() ) );
        }
        if ( source.getForDateConversionWithLocalDateTime() != null ) {
            target.setForDateConversionWithLocalDateTime( Date.from( source.getForDateConversionWithLocalDateTime().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( source.getForDateConversionWithLocalDate() != null ) {
            target.setForDateConversionWithLocalDate( Date.from( source.getForDateConversionWithLocalDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( source.getForSqlDateConversionWithLocalDate() != null ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( source.getForSqlDateConversionWithLocalDate().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        if ( source.getForDateConversionWithInstant() != null ) {
            target.setForDateConversionWithInstant( Date.from( source.getForDateConversionWithInstant() ) );
        }
        if ( source.getForLocalDateTimeConversionWithLocalDate() != null ) {
            target.setForLocalDateTimeConversionWithLocalDate( source.getForLocalDateTimeConversionWithLocalDate().atStartOfDay() );
        }
        if ( source.getForInstantConversionWithString() != null ) {
            target.setForInstantConversionWithString( source.getForInstantConversionWithString().toString() );
        }
        if ( source.getForPeriodConversionWithString() != null ) {
            target.setForPeriodConversionWithString( source.getForPeriodConversionWithString().toString() );
        }
        if ( source.getForDurationConversionWithString() != null ) {
            target.setForDurationConversionWithString( source.getForDurationConversionWithString().toString() );
        }

        return target;
    }

    @Override
    public Source targetToSource(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        if ( target.getZonedDateTime() != null ) {
            source.setZonedDateTime( ZonedDateTime.parse( target.getZonedDateTime(), dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 ) );
        }
        if ( target.getLocalDateTime() != null ) {
            source.setLocalDateTime( LocalDateTime.parse( target.getLocalDateTime(), dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) );
        }
        if ( target.getLocalDate() != null ) {
            source.setLocalDate( LocalDate.parse( target.getLocalDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        if ( target.getLocalTime() != null ) {
            source.setLocalTime( LocalTime.parse( target.getLocalTime(), dateTimeFormatter_HH_mm_168697690 ) );
        }
        source.setForCalendarConversion( calendarToZonedDateTime( target.getForCalendarConversion() ) );
        if ( target.getForDateConversionWithZonedDateTime() != null ) {
            source.setForDateConversionWithZonedDateTime( ZonedDateTime.ofInstant( target.getForDateConversionWithZonedDateTime().toInstant(), ZoneId.systemDefault() ) );
        }
        if ( target.getForDateConversionWithLocalDateTime() != null ) {
            source.setForDateConversionWithLocalDateTime( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( target.getForDateConversionWithLocalDate() != null ) {
            source.setForDateConversionWithLocalDate( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        if ( target.getForSqlDateConversionWithLocalDate() != null ) {
            source.setForSqlDateConversionWithLocalDate( target.getForSqlDateConversionWithLocalDate().toLocalDate() );
        }
        if ( target.getForDateConversionWithInstant() != null ) {
            source.setForDateConversionWithInstant( target.getForDateConversionWithInstant().toInstant() );
        }
        if ( target.getForLocalDateTimeConversionWithLocalDate() != null ) {
            source.setForLocalDateTimeConversionWithLocalDate( target.getForLocalDateTimeConversionWithLocalDate().toLocalDate() );
        }
        if ( target.getForInstantConversionWithString() != null ) {
            source.setForInstantConversionWithString( Instant.parse( target.getForInstantConversionWithString() ) );
        }
        if ( target.getForPeriodConversionWithString() != null ) {
            source.setForPeriodConversionWithString( Period.parse( target.getForPeriodConversionWithString() ) );
        }
        if ( target.getForDurationConversionWithString() != null ) {
            source.setForDurationConversionWithString( Duration.parse( target.getForDurationConversionWithString() ) );
        }

        return source;
    }

    @Override
    public Source targetToSourceDateTimeMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        if ( target.getZonedDateTime() != null ) {
            source.setZonedDateTime( ZonedDateTime.parse( target.getZonedDateTime(), dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 ) );
        }
        if ( target.getLocalDateTime() != null ) {
            source.setLocalDateTime( LocalDateTime.parse( target.getLocalDateTime() ) );
        }
        if ( target.getLocalDate() != null ) {
            source.setLocalDate( LocalDate.parse( target.getLocalDate() ) );
        }
        if ( target.getLocalTime() != null ) {
            source.setLocalTime( LocalTime.parse( target.getLocalTime() ) );
        }
        source.setForCalendarConversion( calendarToZonedDateTime( target.getForCalendarConversion() ) );
        if ( target.getForDateConversionWithZonedDateTime() != null ) {
            source.setForDateConversionWithZonedDateTime( ZonedDateTime.ofInstant( target.getForDateConversionWithZonedDateTime().toInstant(), ZoneId.systemDefault() ) );
        }
        if ( target.getForDateConversionWithLocalDateTime() != null ) {
            source.setForDateConversionWithLocalDateTime( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( target.getForDateConversionWithLocalDate() != null ) {
            source.setForDateConversionWithLocalDate( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        if ( target.getForSqlDateConversionWithLocalDate() != null ) {
            source.setForSqlDateConversionWithLocalDate( target.getForSqlDateConversionWithLocalDate().toLocalDate() );
        }
        if ( target.getForDateConversionWithInstant() != null ) {
            source.setForDateConversionWithInstant( target.getForDateConversionWithInstant().toInstant() );
        }
        if ( target.getForLocalDateTimeConversionWithLocalDate() != null ) {
            source.setForLocalDateTimeConversionWithLocalDate( target.getForLocalDateTimeConversionWithLocalDate().toLocalDate() );
        }
        if ( target.getForInstantConversionWithString() != null ) {
            source.setForInstantConversionWithString( Instant.parse( target.getForInstantConversionWithString() ) );
        }
        if ( target.getForPeriodConversionWithString() != null ) {
            source.setForPeriodConversionWithString( Period.parse( target.getForPeriodConversionWithString() ) );
        }
        if ( target.getForDurationConversionWithString() != null ) {
            source.setForDurationConversionWithString( Duration.parse( target.getForDurationConversionWithString() ) );
        }

        return source;
    }

    @Override
    public Source targetToSourceLocalDateTimeMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        if ( target.getLocalDateTime() != null ) {
            source.setLocalDateTime( LocalDateTime.parse( target.getLocalDateTime(), dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) );
        }
        if ( target.getZonedDateTime() != null ) {
            source.setZonedDateTime( ZonedDateTime.parse( target.getZonedDateTime() ) );
        }
        if ( target.getLocalDate() != null ) {
            source.setLocalDate( LocalDate.parse( target.getLocalDate() ) );
        }
        if ( target.getLocalTime() != null ) {
            source.setLocalTime( LocalTime.parse( target.getLocalTime() ) );
        }
        source.setForCalendarConversion( calendarToZonedDateTime( target.getForCalendarConversion() ) );
        if ( target.getForDateConversionWithZonedDateTime() != null ) {
            source.setForDateConversionWithZonedDateTime( ZonedDateTime.ofInstant( target.getForDateConversionWithZonedDateTime().toInstant(), ZoneId.systemDefault() ) );
        }
        if ( target.getForDateConversionWithLocalDateTime() != null ) {
            source.setForDateConversionWithLocalDateTime( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( target.getForDateConversionWithLocalDate() != null ) {
            source.setForDateConversionWithLocalDate( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        if ( target.getForSqlDateConversionWithLocalDate() != null ) {
            source.setForSqlDateConversionWithLocalDate( target.getForSqlDateConversionWithLocalDate().toLocalDate() );
        }
        if ( target.getForDateConversionWithInstant() != null ) {
            source.setForDateConversionWithInstant( target.getForDateConversionWithInstant().toInstant() );
        }
        if ( target.getForLocalDateTimeConversionWithLocalDate() != null ) {
            source.setForLocalDateTimeConversionWithLocalDate( target.getForLocalDateTimeConversionWithLocalDate().toLocalDate() );
        }
        if ( target.getForInstantConversionWithString() != null ) {
            source.setForInstantConversionWithString( Instant.parse( target.getForInstantConversionWithString() ) );
        }
        if ( target.getForPeriodConversionWithString() != null ) {
            source.setForPeriodConversionWithString( Period.parse( target.getForPeriodConversionWithString() ) );
        }
        if ( target.getForDurationConversionWithString() != null ) {
            source.setForDurationConversionWithString( Duration.parse( target.getForDurationConversionWithString() ) );
        }

        return source;
    }

    @Override
    public Source targetToSourceLocalDateMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        if ( target.getLocalDate() != null ) {
            source.setLocalDate( LocalDate.parse( target.getLocalDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        if ( target.getZonedDateTime() != null ) {
            source.setZonedDateTime( ZonedDateTime.parse( target.getZonedDateTime() ) );
        }
        if ( target.getLocalDateTime() != null ) {
            source.setLocalDateTime( LocalDateTime.parse( target.getLocalDateTime() ) );
        }
        if ( target.getLocalTime() != null ) {
            source.setLocalTime( LocalTime.parse( target.getLocalTime() ) );
        }
        source.setForCalendarConversion( calendarToZonedDateTime( target.getForCalendarConversion() ) );
        if ( target.getForDateConversionWithZonedDateTime() != null ) {
            source.setForDateConversionWithZonedDateTime( ZonedDateTime.ofInstant( target.getForDateConversionWithZonedDateTime().toInstant(), ZoneId.systemDefault() ) );
        }
        if ( target.getForDateConversionWithLocalDateTime() != null ) {
            source.setForDateConversionWithLocalDateTime( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( target.getForDateConversionWithLocalDate() != null ) {
            source.setForDateConversionWithLocalDate( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        if ( target.getForSqlDateConversionWithLocalDate() != null ) {
            source.setForSqlDateConversionWithLocalDate( target.getForSqlDateConversionWithLocalDate().toLocalDate() );
        }
        if ( target.getForDateConversionWithInstant() != null ) {
            source.setForDateConversionWithInstant( target.getForDateConversionWithInstant().toInstant() );
        }
        if ( target.getForLocalDateTimeConversionWithLocalDate() != null ) {
            source.setForLocalDateTimeConversionWithLocalDate( target.getForLocalDateTimeConversionWithLocalDate().toLocalDate() );
        }
        if ( target.getForInstantConversionWithString() != null ) {
            source.setForInstantConversionWithString( Instant.parse( target.getForInstantConversionWithString() ) );
        }
        if ( target.getForPeriodConversionWithString() != null ) {
            source.setForPeriodConversionWithString( Period.parse( target.getForPeriodConversionWithString() ) );
        }
        if ( target.getForDurationConversionWithString() != null ) {
            source.setForDurationConversionWithString( Duration.parse( target.getForDurationConversionWithString() ) );
        }

        return source;
    }

    @Override
    public Source targetToSourceLocalTimeMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        if ( target.getLocalTime() != null ) {
            source.setLocalTime( LocalTime.parse( target.getLocalTime(), dateTimeFormatter_HH_mm_168697690 ) );
        }
        if ( target.getZonedDateTime() != null ) {
            source.setZonedDateTime( ZonedDateTime.parse( target.getZonedDateTime() ) );
        }
        if ( target.getLocalDateTime() != null ) {
            source.setLocalDateTime( LocalDateTime.parse( target.getLocalDateTime() ) );
        }
        if ( target.getLocalDate() != null ) {
            source.setLocalDate( LocalDate.parse( target.getLocalDate() ) );
        }
        source.setForCalendarConversion( calendarToZonedDateTime( target.getForCalendarConversion() ) );
        if ( target.getForDateConversionWithZonedDateTime() != null ) {
            source.setForDateConversionWithZonedDateTime( ZonedDateTime.ofInstant( target.getForDateConversionWithZonedDateTime().toInstant(), ZoneId.systemDefault() ) );
        }
        if ( target.getForDateConversionWithLocalDateTime() != null ) {
            source.setForDateConversionWithLocalDateTime( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( target.getForDateConversionWithLocalDate() != null ) {
            source.setForDateConversionWithLocalDate( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        if ( target.getForSqlDateConversionWithLocalDate() != null ) {
            source.setForSqlDateConversionWithLocalDate( target.getForSqlDateConversionWithLocalDate().toLocalDate() );
        }
        if ( target.getForDateConversionWithInstant() != null ) {
            source.setForDateConversionWithInstant( target.getForDateConversionWithInstant().toInstant() );
        }
        if ( target.getForLocalDateTimeConversionWithLocalDate() != null ) {
            source.setForLocalDateTimeConversionWithLocalDate( target.getForLocalDateTimeConversionWithLocalDate().toLocalDate() );
        }
        if ( target.getForInstantConversionWithString() != null ) {
            source.setForInstantConversionWithString( Instant.parse( target.getForInstantConversionWithString() ) );
        }
        if ( target.getForPeriodConversionWithString() != null ) {
            source.setForPeriodConversionWithString( Period.parse( target.getForPeriodConversionWithString() ) );
        }
        if ( target.getForDurationConversionWithString() != null ) {
            source.setForDurationConversionWithString( Duration.parse( target.getForDurationConversionWithString() ) );
        }

        return source;
    }

    @Override
    public Source targetToSourceDefaultMapping(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        if ( target.getZonedDateTime() != null ) {
            source.setZonedDateTime( ZonedDateTime.parse( target.getZonedDateTime(), dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 ) );
        }
        if ( target.getLocalDateTime() != null ) {
            source.setLocalDateTime( LocalDateTime.parse( target.getLocalDateTime(), dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) );
        }
        if ( target.getLocalDate() != null ) {
            source.setLocalDate( LocalDate.parse( target.getLocalDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        if ( target.getLocalTime() != null ) {
            source.setLocalTime( LocalTime.parse( target.getLocalTime(), dateTimeFormatter_HH_mm_168697690 ) );
        }
        source.setForCalendarConversion( calendarToZonedDateTime( target.getForCalendarConversion() ) );
        if ( target.getForDateConversionWithZonedDateTime() != null ) {
            source.setForDateConversionWithZonedDateTime( ZonedDateTime.ofInstant( target.getForDateConversionWithZonedDateTime().toInstant(), ZoneId.systemDefault() ) );
        }
        if ( target.getForDateConversionWithLocalDateTime() != null ) {
            source.setForDateConversionWithLocalDateTime( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( target.getForDateConversionWithLocalDate() != null ) {
            source.setForDateConversionWithLocalDate( LocalDateTime.ofInstant( target.getForDateConversionWithLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        if ( target.getForSqlDateConversionWithLocalDate() != null ) {
            source.setForSqlDateConversionWithLocalDate( target.getForSqlDateConversionWithLocalDate().toLocalDate() );
        }
        if ( target.getForDateConversionWithInstant() != null ) {
            source.setForDateConversionWithInstant( target.getForDateConversionWithInstant().toInstant() );
        }
        if ( target.getForLocalDateTimeConversionWithLocalDate() != null ) {
            source.setForLocalDateTimeConversionWithLocalDate( target.getForLocalDateTimeConversionWithLocalDate().toLocalDate() );
        }
        if ( target.getForInstantConversionWithString() != null ) {
            source.setForInstantConversionWithString( Instant.parse( target.getForInstantConversionWithString() ) );
        }
        if ( target.getForPeriodConversionWithString() != null ) {
            source.setForPeriodConversionWithString( Period.parse( target.getForPeriodConversionWithString() ) );
        }
        if ( target.getForDurationConversionWithString() != null ) {
            source.setForDurationConversionWithString( Duration.parse( target.getForDurationConversionWithString() ) );
        }

        return source;
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
