/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1460;

import java.util.Date;

import org.joda.time.DateTime;

public class Source {
    private String stringToEnum;
    private DateTime jodaDateTimeToCalendar;
    private Date dateToJodaDateTime;

    public String getStringToEnum() {
        return stringToEnum;
    }

    public void setStringToEnum(String stringToEnum) {
        this.stringToEnum = stringToEnum;
    }

    public DateTime getJodaDateTimeToCalendar() {
        return jodaDateTimeToCalendar;
    }

    public void setJodaDateTimeToCalendar(DateTime jodaDateTimeToCalendar) {
        this.jodaDateTimeToCalendar = jodaDateTimeToCalendar;
    }

    public Date getDateToJodaDateTime() {
        return dateToJodaDateTime;
    }

    public void setDateToJodaDateTime(Date dateToJodaDateTime) {
        this.dateToJodaDateTime = dateToJodaDateTime;
    }
}
