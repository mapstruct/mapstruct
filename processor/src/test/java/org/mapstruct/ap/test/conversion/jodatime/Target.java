/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.jodatime;

import java.util.Calendar;
import java.util.Date;

public class Target {

    private String dateTime;

    private String localDateTime;

    private String localDate;

    private String localTime;

    private Date dateTimeForDateConversion;

    private Calendar dateTimeForCalendarConversion;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public Date getDateTimeForDateConversion() {
        return dateTimeForDateConversion;
    }

    public void setDateTimeForDateConversion(Date dateTimeForDateConversion) {
        this.dateTimeForDateConversion = dateTimeForDateConversion;
    }

    public Calendar getDateTimeForCalendarConversion() {
        return dateTimeForCalendarConversion;
    }

    public void setDateTimeForCalendarConversion(Calendar dateTimeForCalendarConversion) {
        this.dateTimeForCalendarConversion = dateTimeForCalendarConversion;
    }
}
