/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
}
