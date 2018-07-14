/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.erroneous;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

import org.joda.time.DateTime;

/**
 * @author Filip Hrisafov
 */
public class Source {

    private Date date;

    private ZonedDateTime zonedDateTime;
    private LocalDateTime localDateTime;
    private LocalDate localDate;
    private LocalTime localTime;

    private DateTime dateTime;
    private org.joda.time.LocalDateTime jodaLocalDateTime;
    private org.joda.time.LocalDate jodaLocalDate;
    private org.joda.time.LocalTime jodaLocalTime;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
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

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public org.joda.time.LocalDateTime getJodaLocalDateTime() {
        return jodaLocalDateTime;
    }

    public void setJodaLocalDateTime(org.joda.time.LocalDateTime jodaLocalDateTime) {
        this.jodaLocalDateTime = jodaLocalDateTime;
    }

    public org.joda.time.LocalDate getJodaLocalDate() {
        return jodaLocalDate;
    }

    public void setJodaLocalDate(org.joda.time.LocalDate jodaLocalDate) {
        this.jodaLocalDate = jodaLocalDate;
    }

    public org.joda.time.LocalTime getJodaLocalTime() {
        return jodaLocalTime;
    }

    public void setJodaLocalTime(org.joda.time.LocalTime jodaLocalTime) {
        this.jodaLocalTime = jodaLocalTime;
    }
}
