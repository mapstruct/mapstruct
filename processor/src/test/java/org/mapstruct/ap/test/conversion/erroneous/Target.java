/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.erroneous;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private String date;

    private String zonedDateTime;
    private String localDateTime;
    private String localDate;
    private String localTime;

    private String dateTime;
    private String jodaLocalDateTime;
    private String jodaLocalDate;
    private String jodaLocalTime;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getJodaLocalDateTime() {
        return jodaLocalDateTime;
    }

    public void setJodaLocalDateTime(String jodaLocalDateTime) {
        this.jodaLocalDateTime = jodaLocalDateTime;
    }

    public String getJodaLocalDate() {
        return jodaLocalDate;
    }

    public void setJodaLocalDate(String jodaLocalDate) {
        this.jodaLocalDate = jodaLocalDate;
    }

    public String getJodaLocalTime() {
        return jodaLocalTime;
    }

    public void setJodaLocalTime(String jodaLocalTime) {
        this.jodaLocalTime = jodaLocalTime;
    }
}
