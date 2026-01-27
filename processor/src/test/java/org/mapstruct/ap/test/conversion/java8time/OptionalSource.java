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
import java.time.ZonedDateTime;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OptionalSource {

    private Optional<ZonedDateTime> zonedDateTime = Optional.empty();

    private Optional<LocalDateTime> localDateTime = Optional.empty();

    private Optional<LocalDate> localDate = Optional.empty();

    private Optional<LocalTime> localTime = Optional.empty();

    private Optional<ZonedDateTime> forCalendarConversion = Optional.empty();

    private Optional<ZonedDateTime> forDateConversionWithZonedDateTime = Optional.empty();

    private Optional<LocalDateTime> forDateConversionWithLocalDateTime = Optional.empty();

    private Optional<LocalDate> forDateConversionWithLocalDate = Optional.empty();

    private Optional<LocalDate> forSqlDateConversionWithLocalDate = Optional.empty();

    private Optional<Instant> forDateConversionWithInstant = Optional.empty();

    private Optional<LocalDate> forLocalDateTimeConversionWithLocalDate = Optional.empty();

    private Optional<Instant> forInstantConversionWithString = Optional.empty();

    private Optional<Period> forPeriodConversionWithString = Optional.empty();

    private Optional<Duration> forDurationConversionWithString = Optional.empty();

    public Optional<ZonedDateTime> getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(Optional<ZonedDateTime> dateTime) {
        this.zonedDateTime = dateTime;
    }

    public Optional<LocalDateTime> getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(Optional<LocalDateTime> localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Optional<LocalDate> getLocalDate() {
        return localDate;
    }

    public void setLocalDate(Optional<LocalDate> localDate) {
        this.localDate = localDate;
    }

    public Optional<LocalTime> getLocalTime() {
        return localTime;
    }

    public void setLocalTime(Optional<LocalTime> localTime) {
        this.localTime = localTime;
    }

    public Optional<ZonedDateTime> getForCalendarConversion() {
        return forCalendarConversion;
    }

    public void setForCalendarConversion(Optional<ZonedDateTime> forCalendarConversion) {
        this.forCalendarConversion = forCalendarConversion;
    }

    public Optional<ZonedDateTime> getForDateConversionWithZonedDateTime() {
        return forDateConversionWithZonedDateTime;
    }

    public void setForDateConversionWithZonedDateTime(Optional<ZonedDateTime> forDateConversionWithZonedDateTime) {
        this.forDateConversionWithZonedDateTime = forDateConversionWithZonedDateTime;
    }

    public Optional<LocalDateTime> getForDateConversionWithLocalDateTime() {
        return forDateConversionWithLocalDateTime;
    }

    public void setForDateConversionWithLocalDateTime(Optional<LocalDateTime> forDateConversionWithLocalDateTime) {
        this.forDateConversionWithLocalDateTime = forDateConversionWithLocalDateTime;
    }

    public Optional<LocalDate> getForDateConversionWithLocalDate() {
        return forDateConversionWithLocalDate;
    }

    public void setForDateConversionWithLocalDate(Optional<LocalDate> forDateConversionWithLocalDate) {
        this.forDateConversionWithLocalDate = forDateConversionWithLocalDate;
    }

    public Optional<LocalDate> getForSqlDateConversionWithLocalDate() {
        return forSqlDateConversionWithLocalDate;
    }

    public void setForSqlDateConversionWithLocalDate(Optional<LocalDate> forSqlDateConversionWithLocalDate) {
        this.forSqlDateConversionWithLocalDate = forSqlDateConversionWithLocalDate;
    }

    public Optional<Instant> getForDateConversionWithInstant() {
        return forDateConversionWithInstant;
    }

    public void setForDateConversionWithInstant(Optional<Instant> forDateConversionWithInstant) {
        this.forDateConversionWithInstant = forDateConversionWithInstant;
    }

    public Optional<LocalDate> getForLocalDateTimeConversionWithLocalDate() {
        return forLocalDateTimeConversionWithLocalDate;
    }

    public void setForLocalDateTimeConversionWithLocalDate(
        Optional<LocalDate> forLocalDateTimeConversionWithLocalDate) {
        this.forLocalDateTimeConversionWithLocalDate = forLocalDateTimeConversionWithLocalDate;
    }

    public Optional<Instant> getForInstantConversionWithString() {
        return forInstantConversionWithString;
    }

    public void setForInstantConversionWithString(Optional<Instant> forInstantConversionWithString) {
        this.forInstantConversionWithString = forInstantConversionWithString;
    }

    public Optional<Period> getForPeriodConversionWithString() {
        return forPeriodConversionWithString;
    }

    public void setForPeriodConversionWithString(Optional<Period> forPeriodConversionWithString) {
        this.forPeriodConversionWithString = forPeriodConversionWithString;
    }

    public Optional<Duration> getForDurationConversionWithString() {
        return forDurationConversionWithString;
    }

    public void setForDurationConversionWithString(Optional<Duration> forDurationConversionWithString) {
        this.forDurationConversionWithString = forDurationConversionWithString;
    }
}
