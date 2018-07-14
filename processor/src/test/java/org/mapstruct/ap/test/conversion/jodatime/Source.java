/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.jodatime;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class Source {

    private DateTime dateTime;

    private LocalDateTime localDateTime;

    private LocalDate localDate;

    private LocalTime localTime;

    private DateTime dateTimeForDateConversion;

    private DateTime dateTimeForCalendarConversion;

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
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

    public DateTime getDateTimeForDateConversion() {
        return dateTimeForDateConversion;
    }

    public void setDateTimeForDateConversion(DateTime dateTimeForDateConversion) {
        this.dateTimeForDateConversion = dateTimeForDateConversion;
    }

    public DateTime getDateTimeForCalendarConversion() {
        return dateTimeForCalendarConversion;
    }

    public void setDateTimeForCalendarConversion(DateTime dateTimeForCalendarConversion) {
        this.dateTimeForCalendarConversion = dateTimeForCalendarConversion;
    }
}
