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

/**
 *
 */
public class Source {

    private ZonedDateTime zonedDateTime;

    private LocalDateTime localDateTime;

    private LocalDate localDate;

    private LocalTime localTime;

    private ZonedDateTime forCalendarConversion;

    private ZonedDateTime forDateConversionWithZonedDateTime;

    private LocalDateTime forDateConversionWithLocalDateTime;

    private LocalDate forDateConversionWithLocalDate;

    private LocalDate forSqlDateConversionWithLocalDate;

    private Instant forDateConversionWithInstant;

    private LocalDate forLocalDateTimeConversionWithLocalDate;

    private Instant forInstantConversionWithString;

    private Period forPeriodConversionWithString;

    private Duration forDurationConversionWithString;

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime dateTime) {
        this.zonedDateTime = dateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public ZonedDateTime getForCalendarConversion() {
        return forCalendarConversion;
    }

    public void setForCalendarConversion(ZonedDateTime forCalendarConversion) {
        this.forCalendarConversion = forCalendarConversion;
    }

    public ZonedDateTime getForDateConversionWithZonedDateTime() {
        return forDateConversionWithZonedDateTime;
    }

    public void setForDateConversionWithZonedDateTime(ZonedDateTime forDateConversionWithZonedDateTime) {
        this.forDateConversionWithZonedDateTime = forDateConversionWithZonedDateTime;
    }

    public LocalDateTime getForDateConversionWithLocalDateTime() {
        return forDateConversionWithLocalDateTime;
    }

    public void setForDateConversionWithLocalDateTime(LocalDateTime forDateConversionWithLocalDateTime) {
        this.forDateConversionWithLocalDateTime = forDateConversionWithLocalDateTime;
    }

    public LocalDate getForDateConversionWithLocalDate() {
        return forDateConversionWithLocalDate;
    }

    public void setForDateConversionWithLocalDate(LocalDate forDateConversionWithLocalDate) {
        this.forDateConversionWithLocalDate = forDateConversionWithLocalDate;
    }

    public LocalDate getForSqlDateConversionWithLocalDate() {
        return forSqlDateConversionWithLocalDate;
    }

    public void setForSqlDateConversionWithLocalDate(LocalDate forSqlDateConversionWithLocalDate) {
        this.forSqlDateConversionWithLocalDate = forSqlDateConversionWithLocalDate;
    }

    public Instant getForDateConversionWithInstant() {
        return forDateConversionWithInstant;
    }

    public void setForDateConversionWithInstant(Instant forDateConversionWithInstant) {
        this.forDateConversionWithInstant = forDateConversionWithInstant;
    }

    public LocalDate getForLocalDateTimeConversionWithLocalDate() {
        return forLocalDateTimeConversionWithLocalDate;
    }

    public void setForLocalDateTimeConversionWithLocalDate(LocalDate forLocalDateTimeConversionWithLocalDate) {
        this.forLocalDateTimeConversionWithLocalDate = forLocalDateTimeConversionWithLocalDate;
    }

    public Instant getForInstantConversionWithString() {
        return forInstantConversionWithString;
    }

    public void setForInstantConversionWithString(Instant forInstantConversionWithString) {
        this.forInstantConversionWithString = forInstantConversionWithString;
    }

    public Period getForPeriodConversionWithString() {
        return forPeriodConversionWithString;
    }

    public void setForPeriodConversionWithString(Period forPeriodConversionWithString) {
        this.forPeriodConversionWithString = forPeriodConversionWithString;
    }

    public Duration getForDurationConversionWithString() {
        return forDurationConversionWithString;
    }

    public void setForDurationConversionWithString(Duration forDurationConversionWithString) {
        this.forDurationConversionWithString = forDurationConversionWithString;
    }
}
