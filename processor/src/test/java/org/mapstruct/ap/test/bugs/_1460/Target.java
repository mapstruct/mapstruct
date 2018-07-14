/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1460;

import java.util.Calendar;

import org.joda.time.DateTime;

public class Target {
    public enum Issue1460Enum {
        OK, KO;
    }

    private Issue1460Enum stringToEnum;
    private Calendar jodaDateTimeToCalendar;
    private DateTime dateToJodaDateTime;

    public Issue1460Enum getStringToEnum() {
        return stringToEnum;
    }

    public void setStringToEnum(Issue1460Enum stringToEnum) {
        this.stringToEnum = stringToEnum;
    }

    public Calendar getJodaDateTimeToCalendar() {
        return jodaDateTimeToCalendar;
    }

    public void setJodaDateTimeToCalendar(Calendar jodaDateTimeToCalendar) {
        this.jodaDateTimeToCalendar = jodaDateTimeToCalendar;
    }

    public DateTime getDateToJodaDateTime() {
        return dateToJodaDateTime;
    }

    public void setDateToJodaDateTime(DateTime dateToJodaDateTime) {
        this.dateToJodaDateTime = dateToJodaDateTime;
    }
}
