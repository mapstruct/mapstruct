/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets;

import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class JodaTimeBuildInTarget {

    // CHECKSTYLE:OFF
    public XMLGregorianCalendar publicDateTimeToXmlGregorianCalendar;
    public XMLGregorianCalendar publicLocalDateTimeToXmlGregorianCalendar;
    public XMLGregorianCalendar publicLocalDateToXmlGregorianCalendar;
    public XMLGregorianCalendar publicLocalTimeToXmlGregorianCalendar;
    public DateTime publicXmlGregorianCalendarToDateTime;
    public LocalDate publicXmlGregorianCalendarToLocalDate;
    public LocalDateTime publicXmlGregorianCalendarToLocalDateTime;
    public LocalTime publicXmlGregorianCalendarToLocalTime;
    // XMLGregorianCalendar:ON
    private XMLGregorianCalendar dateTimeToXmlGregorianCalendar;
    private XMLGregorianCalendar localDateTimeToXmlGregorianCalendar;
    private XMLGregorianCalendar localDateToXmlGregorianCalendar;
    private XMLGregorianCalendar localTimeToXmlGregorianCalendar;
    private DateTime xmlGregorianCalendarToDateTime;
    private LocalDate xmlGregorianCalendarToLocalDate;
    private LocalDateTime xmlGregorianCalendarToLocalDateTime;
    private LocalTime xmlGregorianCalendarToLocalTime;

    public XMLGregorianCalendar getDateTimeToXmlGregorianCalendar() {
        return dateTimeToXmlGregorianCalendar;
    }

    public void setDateTimeToXmlGregorianCalendar(XMLGregorianCalendar dateTimeToXmlGregorianCalendar) {
        this.dateTimeToXmlGregorianCalendar = dateTimeToXmlGregorianCalendar;
    }

    public XMLGregorianCalendar getLocalDateTimeToXmlGregorianCalendar() {
        return localDateTimeToXmlGregorianCalendar;
    }

    public void setLocalDateTimeToXmlGregorianCalendar(
            XMLGregorianCalendar localDateTimeToXmlGregorianCalendar) {
        this.localDateTimeToXmlGregorianCalendar = localDateTimeToXmlGregorianCalendar;
    }

    public XMLGregorianCalendar getLocalDateToXmlGregorianCalendar() {
        return localDateToXmlGregorianCalendar;
    }

    public void setLocalDateToXmlGregorianCalendar(XMLGregorianCalendar localDateToXmlGregorianCalendar) {
        this.localDateToXmlGregorianCalendar = localDateToXmlGregorianCalendar;
    }

    public XMLGregorianCalendar getLocalTimeToXmlGregorianCalendar() {
        return localTimeToXmlGregorianCalendar;
    }

    public void setLocalTimeToXmlGregorianCalendar(XMLGregorianCalendar localTimeToXmlGregorianCalendar) {
        this.localTimeToXmlGregorianCalendar = localTimeToXmlGregorianCalendar;
    }

    public DateTime getXmlGregorianCalendarToDateTime() {
        return xmlGregorianCalendarToDateTime;
    }

    public void setXmlGregorianCalendarToDateTime(DateTime xmlGregorianCalendarToDateTime) {
        this.xmlGregorianCalendarToDateTime = xmlGregorianCalendarToDateTime;
    }

    public LocalDate getXmlGregorianCalendarToLocalDate() {
        return xmlGregorianCalendarToLocalDate;
    }

    public void setXmlGregorianCalendarToLocalDate(LocalDate xmlGregorianCalendarToLocalDate) {
        this.xmlGregorianCalendarToLocalDate = xmlGregorianCalendarToLocalDate;
    }

    public LocalDateTime getXmlGregorianCalendarToLocalDateTime() {
        return xmlGregorianCalendarToLocalDateTime;
    }

    public void setXmlGregorianCalendarToLocalDateTime(LocalDateTime xmlGregorianCalendarToLocalDateTime) {
        this.xmlGregorianCalendarToLocalDateTime = xmlGregorianCalendarToLocalDateTime;
    }

    public LocalTime getXmlGregorianCalendarToLocalTime() {
        return xmlGregorianCalendarToLocalTime;
    }

    public void setXmlGregorianCalendarToLocalTime(LocalTime xmlGregorianCalendarToLocalTime) {
        this.xmlGregorianCalendarToLocalTime = xmlGregorianCalendarToLocalTime;
    }
}
