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
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T14:56:18+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (IBM Corporation)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 = DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm z" );
    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 = DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" );
    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_11900521056 = DateTimeFormatter.ofPattern( "dd.MM.yyyy" );
    private final DateTimeFormatter dateTimeFormatter_HH_mm_168697690 = DateTimeFormatter.ofPattern( "HH:mm" );

    @Override
    public Target sourceToTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        ZonedDateTime zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime != null ) {
            target.setZonedDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668.format( zonedDateTime ) );
        }
        LocalDateTime localDateTime = source.getLocalDateTime();
        if ( localDateTime != null ) {
            target.setLocalDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( localDateTime ) );
        }
        LocalDate localDate = source.getLocalDate();
        if ( localDate != null ) {
            target.setLocalDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( localDate ) );
        }
        LocalTime localTime = source.getLocalTime();
        if ( localTime != null ) {
            target.setLocalTime( dateTimeFormatter_HH_mm_168697690.format( localTime ) );
        }
        target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion() ) );
        ZonedDateTime forDateConversionWithZonedDateTime = source.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            target.setForDateConversionWithZonedDateTime( Date.from( forDateConversionWithZonedDateTime.toInstant() ) );
        }
        LocalDateTime forDateConversionWithLocalDateTime = source.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            target.setForDateConversionWithLocalDateTime( Date.from( forDateConversionWithLocalDateTime.toInstant( ZoneOffset.UTC ) ) );
        }
        LocalDate forDateConversionWithLocalDate = source.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            target.setForDateConversionWithLocalDate( Date.from( forDateConversionWithLocalDate.atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        LocalDate forSqlDateConversionWithLocalDate = source.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( forSqlDateConversionWithLocalDate.atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        Instant forDateConversionWithInstant = source.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            target.setForDateConversionWithInstant( Date.from( forDateConversionWithInstant ) );
        }
        LocalDate forLocalDateTimeConversionWithLocalDate = source.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            target.setForLocalDateTimeConversionWithLocalDate( forLocalDateTimeConversionWithLocalDate.atStartOfDay() );
        }
        Instant forInstantConversionWithString = source.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            target.setForInstantConversionWithString( forInstantConversionWithString.toString() );
        }
        Period forPeriodConversionWithString = source.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            target.setForPeriodConversionWithString( forPeriodConversionWithString.toString() );
        }
        Duration forDurationConversionWithString = source.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            target.setForDurationConversionWithString( forDurationConversionWithString.toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetDefaultMapping(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        ZonedDateTime zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime != null ) {
            target.setZonedDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668.format( zonedDateTime ) );
        }
        LocalDateTime localDateTime = source.getLocalDateTime();
        if ( localDateTime != null ) {
            target.setLocalDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( localDateTime ) );
        }
        LocalDate localDate = source.getLocalDate();
        if ( localDate != null ) {
            target.setLocalDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( localDate ) );
        }
        LocalTime localTime = source.getLocalTime();
        if ( localTime != null ) {
            target.setLocalTime( dateTimeFormatter_HH_mm_168697690.format( localTime ) );
        }
        target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion() ) );
        ZonedDateTime forDateConversionWithZonedDateTime = source.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            target.setForDateConversionWithZonedDateTime( Date.from( forDateConversionWithZonedDateTime.toInstant() ) );
        }
        LocalDateTime forDateConversionWithLocalDateTime = source.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            target.setForDateConversionWithLocalDateTime( Date.from( forDateConversionWithLocalDateTime.toInstant( ZoneOffset.UTC ) ) );
        }
        LocalDate forDateConversionWithLocalDate = source.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            target.setForDateConversionWithLocalDate( Date.from( forDateConversionWithLocalDate.atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        LocalDate forSqlDateConversionWithLocalDate = source.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( forSqlDateConversionWithLocalDate.atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        Instant forDateConversionWithInstant = source.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            target.setForDateConversionWithInstant( Date.from( forDateConversionWithInstant ) );
        }
        LocalDate forLocalDateTimeConversionWithLocalDate = source.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            target.setForLocalDateTimeConversionWithLocalDate( forLocalDateTimeConversionWithLocalDate.atStartOfDay() );
        }
        Instant forInstantConversionWithString = source.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            target.setForInstantConversionWithString( forInstantConversionWithString.toString() );
        }
        Period forPeriodConversionWithString = source.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            target.setForPeriodConversionWithString( forPeriodConversionWithString.toString() );
        }
        Duration forDurationConversionWithString = source.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            target.setForDurationConversionWithString( forDurationConversionWithString.toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetDateTimeMapped(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        ZonedDateTime zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime != null ) {
            target.setZonedDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668.format( zonedDateTime ) );
        }
        LocalDateTime localDateTime = source.getLocalDateTime();
        if ( localDateTime != null ) {
            target.setLocalDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( localDateTime ) );
        }
        LocalDate localDate = source.getLocalDate();
        if ( localDate != null ) {
            target.setLocalDate( DateTimeFormatter.ISO_LOCAL_DATE.format( localDate ) );
        }
        LocalTime localTime = source.getLocalTime();
        if ( localTime != null ) {
            target.setLocalTime( DateTimeFormatter.ISO_LOCAL_TIME.format( localTime ) );
        }
        target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion() ) );
        ZonedDateTime forDateConversionWithZonedDateTime = source.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            target.setForDateConversionWithZonedDateTime( Date.from( forDateConversionWithZonedDateTime.toInstant() ) );
        }
        LocalDateTime forDateConversionWithLocalDateTime = source.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            target.setForDateConversionWithLocalDateTime( Date.from( forDateConversionWithLocalDateTime.toInstant( ZoneOffset.UTC ) ) );
        }
        LocalDate forDateConversionWithLocalDate = source.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            target.setForDateConversionWithLocalDate( Date.from( forDateConversionWithLocalDate.atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        LocalDate forSqlDateConversionWithLocalDate = source.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( forSqlDateConversionWithLocalDate.atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        Instant forDateConversionWithInstant = source.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            target.setForDateConversionWithInstant( Date.from( forDateConversionWithInstant ) );
        }
        LocalDate forLocalDateTimeConversionWithLocalDate = source.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            target.setForLocalDateTimeConversionWithLocalDate( forLocalDateTimeConversionWithLocalDate.atStartOfDay() );
        }
        Instant forInstantConversionWithString = source.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            target.setForInstantConversionWithString( forInstantConversionWithString.toString() );
        }
        Period forPeriodConversionWithString = source.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            target.setForPeriodConversionWithString( forPeriodConversionWithString.toString() );
        }
        Duration forDurationConversionWithString = source.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            target.setForDurationConversionWithString( forDurationConversionWithString.toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetLocalDateTimeMapped(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        LocalDateTime localDateTime = source.getLocalDateTime();
        if ( localDateTime != null ) {
            target.setLocalDateTime( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( localDateTime ) );
        }
        ZonedDateTime zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime != null ) {
            target.setZonedDateTime( DateTimeFormatter.ISO_DATE_TIME.format( zonedDateTime ) );
        }
        LocalDate localDate = source.getLocalDate();
        if ( localDate != null ) {
            target.setLocalDate( DateTimeFormatter.ISO_LOCAL_DATE.format( localDate ) );
        }
        LocalTime localTime = source.getLocalTime();
        if ( localTime != null ) {
            target.setLocalTime( DateTimeFormatter.ISO_LOCAL_TIME.format( localTime ) );
        }
        target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion() ) );
        ZonedDateTime forDateConversionWithZonedDateTime = source.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            target.setForDateConversionWithZonedDateTime( Date.from( forDateConversionWithZonedDateTime.toInstant() ) );
        }
        LocalDateTime forDateConversionWithLocalDateTime = source.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            target.setForDateConversionWithLocalDateTime( Date.from( forDateConversionWithLocalDateTime.toInstant( ZoneOffset.UTC ) ) );
        }
        LocalDate forDateConversionWithLocalDate = source.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            target.setForDateConversionWithLocalDate( Date.from( forDateConversionWithLocalDate.atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        LocalDate forSqlDateConversionWithLocalDate = source.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( forSqlDateConversionWithLocalDate.atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        Instant forDateConversionWithInstant = source.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            target.setForDateConversionWithInstant( Date.from( forDateConversionWithInstant ) );
        }
        LocalDate forLocalDateTimeConversionWithLocalDate = source.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            target.setForLocalDateTimeConversionWithLocalDate( forLocalDateTimeConversionWithLocalDate.atStartOfDay() );
        }
        Instant forInstantConversionWithString = source.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            target.setForInstantConversionWithString( forInstantConversionWithString.toString() );
        }
        Period forPeriodConversionWithString = source.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            target.setForPeriodConversionWithString( forPeriodConversionWithString.toString() );
        }
        Duration forDurationConversionWithString = source.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            target.setForDurationConversionWithString( forDurationConversionWithString.toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetLocalDateMapped(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        LocalDate localDate = source.getLocalDate();
        if ( localDate != null ) {
            target.setLocalDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( localDate ) );
        }
        ZonedDateTime zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime != null ) {
            target.setZonedDateTime( DateTimeFormatter.ISO_DATE_TIME.format( zonedDateTime ) );
        }
        LocalDateTime localDateTime = source.getLocalDateTime();
        if ( localDateTime != null ) {
            target.setLocalDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( localDateTime ) );
        }
        LocalTime localTime = source.getLocalTime();
        if ( localTime != null ) {
            target.setLocalTime( DateTimeFormatter.ISO_LOCAL_TIME.format( localTime ) );
        }
        target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion() ) );
        ZonedDateTime forDateConversionWithZonedDateTime = source.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            target.setForDateConversionWithZonedDateTime( Date.from( forDateConversionWithZonedDateTime.toInstant() ) );
        }
        LocalDateTime forDateConversionWithLocalDateTime = source.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            target.setForDateConversionWithLocalDateTime( Date.from( forDateConversionWithLocalDateTime.toInstant( ZoneOffset.UTC ) ) );
        }
        LocalDate forDateConversionWithLocalDate = source.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            target.setForDateConversionWithLocalDate( Date.from( forDateConversionWithLocalDate.atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        LocalDate forSqlDateConversionWithLocalDate = source.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( forSqlDateConversionWithLocalDate.atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        Instant forDateConversionWithInstant = source.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            target.setForDateConversionWithInstant( Date.from( forDateConversionWithInstant ) );
        }
        LocalDate forLocalDateTimeConversionWithLocalDate = source.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            target.setForLocalDateTimeConversionWithLocalDate( forLocalDateTimeConversionWithLocalDate.atStartOfDay() );
        }
        Instant forInstantConversionWithString = source.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            target.setForInstantConversionWithString( forInstantConversionWithString.toString() );
        }
        Period forPeriodConversionWithString = source.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            target.setForPeriodConversionWithString( forPeriodConversionWithString.toString() );
        }
        Duration forDurationConversionWithString = source.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            target.setForDurationConversionWithString( forDurationConversionWithString.toString() );
        }

        return target;
    }

    @Override
    public Target sourceToTargetLocalTimeMapped(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        LocalTime localTime = source.getLocalTime();
        if ( localTime != null ) {
            target.setLocalTime( dateTimeFormatter_HH_mm_168697690.format( localTime ) );
        }
        ZonedDateTime zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime != null ) {
            target.setZonedDateTime( DateTimeFormatter.ISO_DATE_TIME.format( zonedDateTime ) );
        }
        LocalDateTime localDateTime = source.getLocalDateTime();
        if ( localDateTime != null ) {
            target.setLocalDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( localDateTime ) );
        }
        LocalDate localDate = source.getLocalDate();
        if ( localDate != null ) {
            target.setLocalDate( DateTimeFormatter.ISO_LOCAL_DATE.format( localDate ) );
        }
        target.setForCalendarConversion( zonedDateTimeToCalendar( source.getForCalendarConversion() ) );
        ZonedDateTime forDateConversionWithZonedDateTime = source.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            target.setForDateConversionWithZonedDateTime( Date.from( forDateConversionWithZonedDateTime.toInstant() ) );
        }
        LocalDateTime forDateConversionWithLocalDateTime = source.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            target.setForDateConversionWithLocalDateTime( Date.from( forDateConversionWithLocalDateTime.toInstant( ZoneOffset.UTC ) ) );
        }
        LocalDate forDateConversionWithLocalDate = source.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            target.setForDateConversionWithLocalDate( Date.from( forDateConversionWithLocalDate.atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        LocalDate forSqlDateConversionWithLocalDate = source.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            target.setForSqlDateConversionWithLocalDate( new java.sql.Date( forSqlDateConversionWithLocalDate.atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }
        Instant forDateConversionWithInstant = source.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            target.setForDateConversionWithInstant( Date.from( forDateConversionWithInstant ) );
        }
        LocalDate forLocalDateTimeConversionWithLocalDate = source.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            target.setForLocalDateTimeConversionWithLocalDate( forLocalDateTimeConversionWithLocalDate.atStartOfDay() );
        }
        Instant forInstantConversionWithString = source.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            target.setForInstantConversionWithString( forInstantConversionWithString.toString() );
        }
        Period forPeriodConversionWithString = source.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            target.setForPeriodConversionWithString( forPeriodConversionWithString.toString() );
        }
        Duration forDurationConversionWithString = source.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            target.setForDurationConversionWithString( forDurationConversionWithString.toString() );
        }

        return target;
    }

    @Override
    public Source targetToSource(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        String zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            source.setZonedDateTime( ZonedDateTime.parse( zonedDateTime, dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 ) );
        }
        String localDateTime = target.getLocalDateTime();
        if ( localDateTime != null ) {
            source.setLocalDateTime( LocalDateTime.parse( localDateTime, dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) );
        }
        String localDate = target.getLocalDate();
        if ( localDate != null ) {
            source.setLocalDate( LocalDate.parse( localDate, dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        String localTime = target.getLocalTime();
        if ( localTime != null ) {
            source.setLocalTime( LocalTime.parse( localTime, dateTimeFormatter_HH_mm_168697690 ) );
        }
        source.setForCalendarConversion( calendarToZonedDateTime( target.getForCalendarConversion() ) );
        Date forDateConversionWithZonedDateTime = target.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            source.setForDateConversionWithZonedDateTime( ZonedDateTime.ofInstant( forDateConversionWithZonedDateTime.toInstant(), ZoneId.systemDefault() ) );
        }
        Date forDateConversionWithLocalDateTime = target.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            source.setForDateConversionWithLocalDateTime( LocalDateTime.ofInstant( forDateConversionWithLocalDateTime.toInstant(), ZoneId.of( "UTC" ) ) );
        }
        Date forDateConversionWithLocalDate = target.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            source.setForDateConversionWithLocalDate( LocalDateTime.ofInstant( forDateConversionWithLocalDate.toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        java.sql.Date forSqlDateConversionWithLocalDate = target.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            source.setForSqlDateConversionWithLocalDate( forSqlDateConversionWithLocalDate.toLocalDate() );
        }
        Date forDateConversionWithInstant = target.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            source.setForDateConversionWithInstant( forDateConversionWithInstant.toInstant() );
        }
        LocalDateTime forLocalDateTimeConversionWithLocalDate = target.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            source.setForLocalDateTimeConversionWithLocalDate( forLocalDateTimeConversionWithLocalDate.toLocalDate() );
        }
        String forInstantConversionWithString = target.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            source.setForInstantConversionWithString( Instant.parse( forInstantConversionWithString ) );
        }
        String forPeriodConversionWithString = target.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            source.setForPeriodConversionWithString( Period.parse( forPeriodConversionWithString ) );
        }
        String forDurationConversionWithString = target.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            source.setForDurationConversionWithString( Duration.parse( forDurationConversionWithString ) );
        }

        return source;
    }

    @Override
    public Source targetToSourceDateTimeMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        String zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            source.setZonedDateTime( ZonedDateTime.parse( zonedDateTime, dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 ) );
        }
        String localDateTime = target.getLocalDateTime();
        if ( localDateTime != null ) {
            source.setLocalDateTime( LocalDateTime.parse( localDateTime ) );
        }
        String localDate = target.getLocalDate();
        if ( localDate != null ) {
            source.setLocalDate( LocalDate.parse( localDate ) );
        }
        String localTime = target.getLocalTime();
        if ( localTime != null ) {
            source.setLocalTime( LocalTime.parse( localTime ) );
        }
        source.setForCalendarConversion( calendarToZonedDateTime( target.getForCalendarConversion() ) );
        Date forDateConversionWithZonedDateTime = target.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            source.setForDateConversionWithZonedDateTime( ZonedDateTime.ofInstant( forDateConversionWithZonedDateTime.toInstant(), ZoneId.systemDefault() ) );
        }
        Date forDateConversionWithLocalDateTime = target.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            source.setForDateConversionWithLocalDateTime( LocalDateTime.ofInstant( forDateConversionWithLocalDateTime.toInstant(), ZoneId.of( "UTC" ) ) );
        }
        Date forDateConversionWithLocalDate = target.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            source.setForDateConversionWithLocalDate( LocalDateTime.ofInstant( forDateConversionWithLocalDate.toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        java.sql.Date forSqlDateConversionWithLocalDate = target.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            source.setForSqlDateConversionWithLocalDate( forSqlDateConversionWithLocalDate.toLocalDate() );
        }
        Date forDateConversionWithInstant = target.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            source.setForDateConversionWithInstant( forDateConversionWithInstant.toInstant() );
        }
        LocalDateTime forLocalDateTimeConversionWithLocalDate = target.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            source.setForLocalDateTimeConversionWithLocalDate( forLocalDateTimeConversionWithLocalDate.toLocalDate() );
        }
        String forInstantConversionWithString = target.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            source.setForInstantConversionWithString( Instant.parse( forInstantConversionWithString ) );
        }
        String forPeriodConversionWithString = target.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            source.setForPeriodConversionWithString( Period.parse( forPeriodConversionWithString ) );
        }
        String forDurationConversionWithString = target.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            source.setForDurationConversionWithString( Duration.parse( forDurationConversionWithString ) );
        }

        return source;
    }

    @Override
    public Source targetToSourceLocalDateTimeMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        String localDateTime = target.getLocalDateTime();
        if ( localDateTime != null ) {
            source.setLocalDateTime( LocalDateTime.parse( localDateTime, dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) );
        }
        String zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            source.setZonedDateTime( ZonedDateTime.parse( zonedDateTime ) );
        }
        String localDate = target.getLocalDate();
        if ( localDate != null ) {
            source.setLocalDate( LocalDate.parse( localDate ) );
        }
        String localTime = target.getLocalTime();
        if ( localTime != null ) {
            source.setLocalTime( LocalTime.parse( localTime ) );
        }
        source.setForCalendarConversion( calendarToZonedDateTime( target.getForCalendarConversion() ) );
        Date forDateConversionWithZonedDateTime = target.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            source.setForDateConversionWithZonedDateTime( ZonedDateTime.ofInstant( forDateConversionWithZonedDateTime.toInstant(), ZoneId.systemDefault() ) );
        }
        Date forDateConversionWithLocalDateTime = target.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            source.setForDateConversionWithLocalDateTime( LocalDateTime.ofInstant( forDateConversionWithLocalDateTime.toInstant(), ZoneId.of( "UTC" ) ) );
        }
        Date forDateConversionWithLocalDate = target.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            source.setForDateConversionWithLocalDate( LocalDateTime.ofInstant( forDateConversionWithLocalDate.toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        java.sql.Date forSqlDateConversionWithLocalDate = target.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            source.setForSqlDateConversionWithLocalDate( forSqlDateConversionWithLocalDate.toLocalDate() );
        }
        Date forDateConversionWithInstant = target.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            source.setForDateConversionWithInstant( forDateConversionWithInstant.toInstant() );
        }
        LocalDateTime forLocalDateTimeConversionWithLocalDate = target.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            source.setForLocalDateTimeConversionWithLocalDate( forLocalDateTimeConversionWithLocalDate.toLocalDate() );
        }
        String forInstantConversionWithString = target.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            source.setForInstantConversionWithString( Instant.parse( forInstantConversionWithString ) );
        }
        String forPeriodConversionWithString = target.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            source.setForPeriodConversionWithString( Period.parse( forPeriodConversionWithString ) );
        }
        String forDurationConversionWithString = target.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            source.setForDurationConversionWithString( Duration.parse( forDurationConversionWithString ) );
        }

        return source;
    }

    @Override
    public Source targetToSourceLocalDateMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        String localDate = target.getLocalDate();
        if ( localDate != null ) {
            source.setLocalDate( LocalDate.parse( localDate, dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        String zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            source.setZonedDateTime( ZonedDateTime.parse( zonedDateTime ) );
        }
        String localDateTime = target.getLocalDateTime();
        if ( localDateTime != null ) {
            source.setLocalDateTime( LocalDateTime.parse( localDateTime ) );
        }
        String localTime = target.getLocalTime();
        if ( localTime != null ) {
            source.setLocalTime( LocalTime.parse( localTime ) );
        }
        source.setForCalendarConversion( calendarToZonedDateTime( target.getForCalendarConversion() ) );
        Date forDateConversionWithZonedDateTime = target.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            source.setForDateConversionWithZonedDateTime( ZonedDateTime.ofInstant( forDateConversionWithZonedDateTime.toInstant(), ZoneId.systemDefault() ) );
        }
        Date forDateConversionWithLocalDateTime = target.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            source.setForDateConversionWithLocalDateTime( LocalDateTime.ofInstant( forDateConversionWithLocalDateTime.toInstant(), ZoneId.of( "UTC" ) ) );
        }
        Date forDateConversionWithLocalDate = target.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            source.setForDateConversionWithLocalDate( LocalDateTime.ofInstant( forDateConversionWithLocalDate.toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        java.sql.Date forSqlDateConversionWithLocalDate = target.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            source.setForSqlDateConversionWithLocalDate( forSqlDateConversionWithLocalDate.toLocalDate() );
        }
        Date forDateConversionWithInstant = target.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            source.setForDateConversionWithInstant( forDateConversionWithInstant.toInstant() );
        }
        LocalDateTime forLocalDateTimeConversionWithLocalDate = target.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            source.setForLocalDateTimeConversionWithLocalDate( forLocalDateTimeConversionWithLocalDate.toLocalDate() );
        }
        String forInstantConversionWithString = target.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            source.setForInstantConversionWithString( Instant.parse( forInstantConversionWithString ) );
        }
        String forPeriodConversionWithString = target.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            source.setForPeriodConversionWithString( Period.parse( forPeriodConversionWithString ) );
        }
        String forDurationConversionWithString = target.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            source.setForDurationConversionWithString( Duration.parse( forDurationConversionWithString ) );
        }

        return source;
    }

    @Override
    public Source targetToSourceLocalTimeMapped(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        String localTime = target.getLocalTime();
        if ( localTime != null ) {
            source.setLocalTime( LocalTime.parse( localTime, dateTimeFormatter_HH_mm_168697690 ) );
        }
        String zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            source.setZonedDateTime( ZonedDateTime.parse( zonedDateTime ) );
        }
        String localDateTime = target.getLocalDateTime();
        if ( localDateTime != null ) {
            source.setLocalDateTime( LocalDateTime.parse( localDateTime ) );
        }
        String localDate = target.getLocalDate();
        if ( localDate != null ) {
            source.setLocalDate( LocalDate.parse( localDate ) );
        }
        source.setForCalendarConversion( calendarToZonedDateTime( target.getForCalendarConversion() ) );
        Date forDateConversionWithZonedDateTime = target.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            source.setForDateConversionWithZonedDateTime( ZonedDateTime.ofInstant( forDateConversionWithZonedDateTime.toInstant(), ZoneId.systemDefault() ) );
        }
        Date forDateConversionWithLocalDateTime = target.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            source.setForDateConversionWithLocalDateTime( LocalDateTime.ofInstant( forDateConversionWithLocalDateTime.toInstant(), ZoneId.of( "UTC" ) ) );
        }
        Date forDateConversionWithLocalDate = target.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            source.setForDateConversionWithLocalDate( LocalDateTime.ofInstant( forDateConversionWithLocalDate.toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        java.sql.Date forSqlDateConversionWithLocalDate = target.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            source.setForSqlDateConversionWithLocalDate( forSqlDateConversionWithLocalDate.toLocalDate() );
        }
        Date forDateConversionWithInstant = target.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            source.setForDateConversionWithInstant( forDateConversionWithInstant.toInstant() );
        }
        LocalDateTime forLocalDateTimeConversionWithLocalDate = target.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            source.setForLocalDateTimeConversionWithLocalDate( forLocalDateTimeConversionWithLocalDate.toLocalDate() );
        }
        String forInstantConversionWithString = target.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            source.setForInstantConversionWithString( Instant.parse( forInstantConversionWithString ) );
        }
        String forPeriodConversionWithString = target.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            source.setForPeriodConversionWithString( Period.parse( forPeriodConversionWithString ) );
        }
        String forDurationConversionWithString = target.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            source.setForDurationConversionWithString( Duration.parse( forDurationConversionWithString ) );
        }

        return source;
    }

    @Override
    public Source targetToSourceDefaultMapping(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        String zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            source.setZonedDateTime( ZonedDateTime.parse( zonedDateTime, dateTimeFormatter_dd_MM_yyyy_HH_mm_z_01894582668 ) );
        }
        String localDateTime = target.getLocalDateTime();
        if ( localDateTime != null ) {
            source.setLocalDateTime( LocalDateTime.parse( localDateTime, dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) );
        }
        String localDate = target.getLocalDate();
        if ( localDate != null ) {
            source.setLocalDate( LocalDate.parse( localDate, dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        String localTime = target.getLocalTime();
        if ( localTime != null ) {
            source.setLocalTime( LocalTime.parse( localTime, dateTimeFormatter_HH_mm_168697690 ) );
        }
        source.setForCalendarConversion( calendarToZonedDateTime( target.getForCalendarConversion() ) );
        Date forDateConversionWithZonedDateTime = target.getForDateConversionWithZonedDateTime();
        if ( forDateConversionWithZonedDateTime != null ) {
            source.setForDateConversionWithZonedDateTime( ZonedDateTime.ofInstant( forDateConversionWithZonedDateTime.toInstant(), ZoneId.systemDefault() ) );
        }
        Date forDateConversionWithLocalDateTime = target.getForDateConversionWithLocalDateTime();
        if ( forDateConversionWithLocalDateTime != null ) {
            source.setForDateConversionWithLocalDateTime( LocalDateTime.ofInstant( forDateConversionWithLocalDateTime.toInstant(), ZoneId.of( "UTC" ) ) );
        }
        Date forDateConversionWithLocalDate = target.getForDateConversionWithLocalDate();
        if ( forDateConversionWithLocalDate != null ) {
            source.setForDateConversionWithLocalDate( LocalDateTime.ofInstant( forDateConversionWithLocalDate.toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        java.sql.Date forSqlDateConversionWithLocalDate = target.getForSqlDateConversionWithLocalDate();
        if ( forSqlDateConversionWithLocalDate != null ) {
            source.setForSqlDateConversionWithLocalDate( forSqlDateConversionWithLocalDate.toLocalDate() );
        }
        Date forDateConversionWithInstant = target.getForDateConversionWithInstant();
        if ( forDateConversionWithInstant != null ) {
            source.setForDateConversionWithInstant( forDateConversionWithInstant.toInstant() );
        }
        LocalDateTime forLocalDateTimeConversionWithLocalDate = target.getForLocalDateTimeConversionWithLocalDate();
        if ( forLocalDateTimeConversionWithLocalDate != null ) {
            source.setForLocalDateTimeConversionWithLocalDate( forLocalDateTimeConversionWithLocalDate.toLocalDate() );
        }
        String forInstantConversionWithString = target.getForInstantConversionWithString();
        if ( forInstantConversionWithString != null ) {
            source.setForInstantConversionWithString( Instant.parse( forInstantConversionWithString ) );
        }
        String forPeriodConversionWithString = target.getForPeriodConversionWithString();
        if ( forPeriodConversionWithString != null ) {
            source.setForPeriodConversionWithString( Period.parse( forPeriodConversionWithString ) );
        }
        String forDurationConversionWithString = target.getForDurationConversionWithString();
        if ( forDurationConversionWithString != null ) {
            source.setForDurationConversionWithString( Duration.parse( forDurationConversionWithString ) );
        }

        return source;
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
