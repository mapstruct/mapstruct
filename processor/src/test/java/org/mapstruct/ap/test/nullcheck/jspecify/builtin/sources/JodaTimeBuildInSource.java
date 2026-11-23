/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources;

import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class JodaTimeBuildInSource {

    // CHECKSTYLE:OFF
    public DateTime publicDateTimeToXmlGregorianCalendar;
    public LocalDateTime publicLocalDateTimeToXmlGregorianCalendar;
    public LocalDate publicLocalDateToXmlGregorianCalendar;
    public LocalTime publicLocalTimeToXmlGregorianCalendar;
    public XMLGregorianCalendar publicXmlGregorianCalendarToDateTime;
    public XMLGregorianCalendar publicXmlGregorianCalendarToLocalDate;
    public XMLGregorianCalendar publicXmlGregorianCalendarToLocalDateTime;
    public XMLGregorianCalendar publicXmlGregorianCalendarToLocalTime;
    // CHECKSTYLE:ON
    private DateTime dateTimeToXmlGregorianCalendar;
    private LocalDateTime localDateTimeToXmlGregorianCalendar;
    private LocalDate localDateToXmlGregorianCalendar;
    private LocalTime localTimeToXmlGregorianCalendar;
    private XMLGregorianCalendar xmlGregorianCalendarToDateTime;
    private XMLGregorianCalendar xmlGregorianCalendarToLocalDate;
    private XMLGregorianCalendar xmlGregorianCalendarToLocalDateTime;
    private XMLGregorianCalendar xmlGregorianCalendarToLocalTime;

    public DateTime getDateTimeToXmlGregorianCalendar() {
        return dateTimeToXmlGregorianCalendar;
    }

    public void setDateTimeToXmlGregorianCalendar(DateTime dateTimeToXmlGregorianCalendar) {
        this.dateTimeToXmlGregorianCalendar = dateTimeToXmlGregorianCalendar;
    }

    public LocalDateTime getLocalDateTimeToXmlGregorianCalendar() {
        return localDateTimeToXmlGregorianCalendar;
    }

    public void setLocalDateTimeToXmlGregorianCalendar(LocalDateTime localDateTimeToXmlGregorianCalendar) {
        this.localDateTimeToXmlGregorianCalendar = localDateTimeToXmlGregorianCalendar;
    }

    public LocalDate getLocalDateToXmlGregorianCalendar() {
        return localDateToXmlGregorianCalendar;
    }

    public void setLocalDateToXmlGregorianCalendar(LocalDate localDateToXmlGregorianCalendar) {
        this.localDateToXmlGregorianCalendar = localDateToXmlGregorianCalendar;
    }

    public LocalTime getLocalTimeToXmlGregorianCalendar() {
        return localTimeToXmlGregorianCalendar;
    }

    public void setLocalTimeToXmlGregorianCalendar(LocalTime localTimeToXmlGregorianCalendar) {
        this.localTimeToXmlGregorianCalendar = localTimeToXmlGregorianCalendar;
    }

    public XMLGregorianCalendar getXmlGregorianCalendarToDateTime() {
        return xmlGregorianCalendarToDateTime;
    }

    public void setXmlGregorianCalendarToDateTime(XMLGregorianCalendar xmlGregorianCalendarToDateTime) {
        this.xmlGregorianCalendarToDateTime = xmlGregorianCalendarToDateTime;
    }

    public XMLGregorianCalendar getXmlGregorianCalendarToLocalDate() {
        return xmlGregorianCalendarToLocalDate;
    }

    public void setXmlGregorianCalendarToLocalDate(XMLGregorianCalendar xmlGregorianCalendarToLocalDate) {
        this.xmlGregorianCalendarToLocalDate = xmlGregorianCalendarToLocalDate;
    }

    public XMLGregorianCalendar getXmlGregorianCalendarToLocalDateTime() {
        return xmlGregorianCalendarToLocalDateTime;
    }

    public void setXmlGregorianCalendarToLocalDateTime(
            XMLGregorianCalendar xmlGregorianCalendarToLocalDateTime) {
        this.xmlGregorianCalendarToLocalDateTime = xmlGregorianCalendarToLocalDateTime;
    }

    public XMLGregorianCalendar getXmlGregorianCalendarToLocalTime() {
        return xmlGregorianCalendarToLocalTime;
    }

    public void setXmlGregorianCalendarToLocalTime(XMLGregorianCalendar xmlGregorianCalendarToLocalTime) {
        this.xmlGregorianCalendarToLocalTime = xmlGregorianCalendarToLocalTime;
    }
}
