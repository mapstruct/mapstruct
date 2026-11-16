/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class Target {

    private String zonedDateTime;

    private String localDateTime;

    private String localDate;

    private String localTime;

    private Calendar forCalendarConversion;

    private Date forDateConversionWithZonedDateTime;

    private Date forDateConversionWithLocalDateTime;

    private Date forDateConversionWithLocalDate;

    private java.sql.Date forSqlDateConversionWithLocalDate;

    private Date forDateConversionWithInstant;

    private LocalDateTime forLocalDateTimeConversionWithLocalDate;

    private String forInstantConversionWithString;

    private String forPeriodConversionWithString;

    private String forDurationConversionWithString;

    public String getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(String zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public Calendar getForCalendarConversion() {
        return forCalendarConversion;
    }

    public void setForCalendarConversion(Calendar forCalendarConversion) {
        this.forCalendarConversion = forCalendarConversion;
    }

    public Date getForDateConversionWithZonedDateTime() {
        return forDateConversionWithZonedDateTime;
    }

    public void setForDateConversionWithZonedDateTime(Date forDateConversionWithZonedDateTime) {
        this.forDateConversionWithZonedDateTime = forDateConversionWithZonedDateTime;
    }

    public Date getForDateConversionWithLocalDateTime() {
        return forDateConversionWithLocalDateTime;
    }

    public void setForDateConversionWithLocalDateTime(Date forDateConversionWithLocalDateTime) {
        this.forDateConversionWithLocalDateTime = forDateConversionWithLocalDateTime;
    }

    public Date getForDateConversionWithLocalDate() {
        return forDateConversionWithLocalDate;
    }

    public void setForDateConversionWithLocalDate(Date forDateConversionWithLocalDate) {
        this.forDateConversionWithLocalDate = forDateConversionWithLocalDate;
    }

    public java.sql.Date getForSqlDateConversionWithLocalDate() {
        return forSqlDateConversionWithLocalDate;
    }

    public void setForSqlDateConversionWithLocalDate(java.sql.Date forSqlDateConversionWithLocalDate) {
        this.forSqlDateConversionWithLocalDate = forSqlDateConversionWithLocalDate;
    }

    public Date getForDateConversionWithInstant() {
        return forDateConversionWithInstant;
    }

    public void setForDateConversionWithInstant(Date forDateConversionWithInstant) {
        this.forDateConversionWithInstant = forDateConversionWithInstant;
    }

    public LocalDateTime getForLocalDateTimeConversionWithLocalDate() {
        return forLocalDateTimeConversionWithLocalDate;
    }

    public void setForLocalDateTimeConversionWithLocalDate(LocalDateTime forLocalDateTimeConversionWithLocalDate) {
        this.forLocalDateTimeConversionWithLocalDate = forLocalDateTimeConversionWithLocalDate;
    }

    public String getForInstantConversionWithString() {
        return forInstantConversionWithString;
    }

    public void setForInstantConversionWithString(String forInstantConversionWithString) {
        this.forInstantConversionWithString = forInstantConversionWithString;
    }

    public String getForPeriodConversionWithString() {
        return forPeriodConversionWithString;
    }

    public void setForPeriodConversionWithString(String forPeriodConversionWithString) {
        this.forPeriodConversionWithString = forPeriodConversionWithString;
    }

    public String getForDurationConversionWithString() {
        return forDurationConversionWithString;
    }

    public void setForDurationConversionWithString(String forDurationConversionWithString) {
        this.forDurationConversionWithString = forDurationConversionWithString;
    }
}
