/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

public class BuildInSource {

    // CHECKSTYLE:OFF
    public Calendar publicCalendarToDate;
    public Calendar publicCalendarToString;
    public Calendar publicCalendarToXmlGregCal;
    public Calendar publicCalendarToZonedDateTime;
    public Date publicDateToCalendar;
    public Date publicDateToXmlGregCal;
    public List<XMLGregorianCalendar> publicIterableSourceTarget;
    public String publicStringToCalendar;
    public String publicStringToXmlGregCal;
    public XMLGregorianCalendar publicXmlGregCalToCalendar;
    public XMLGregorianCalendar publicXmlGregCalToDate;
    public XMLGregorianCalendar publicXmlGregCalToString;
    public ZonedDateTime publicZonedDateTimeToCalendar;
    // CHECKSTYLE:ON

    private Calendar calendarToDate;
    private Calendar calendarToString;
    private Calendar calendarToXmlGregCal;
    private Calendar calendarToZonedDateTime;
    private Date dateToCalendar;
    private Date dateToXmlGregCal;
    private List<XMLGregorianCalendar> iterableSourceTarget;
    private String stringToCalendar;
    private String stringToXmlGregCal;
    private XMLGregorianCalendar xmlGregCalToCalendar;
    private XMLGregorianCalendar xmlGregCalToDate;
    private XMLGregorianCalendar xmlGregCalToString;
    private ZonedDateTime zonedDateTimeToCalendar;

    public Calendar getCalendarToDate() {
        return calendarToDate;
    }

    public void setCalendarToDate(Calendar calendarToDate) {
        this.calendarToDate = calendarToDate;
    }

    public Calendar getCalendarToString() {
        return calendarToString;
    }

    public void setCalendarToString(Calendar calendarToString) {
        this.calendarToString = calendarToString;
    }

    public Calendar getCalendarToXmlGregCal() {
        return calendarToXmlGregCal;
    }

    public void setCalendarToXmlGregCal(Calendar calendarToXmlGregCal) {
        this.calendarToXmlGregCal = calendarToXmlGregCal;
    }

    public Calendar getCalendarToZonedDateTime() {
        return calendarToZonedDateTime;
    }

    public void setCalendarToZonedDateTime(Calendar calendarToZonedDateTime) {
        this.calendarToZonedDateTime = calendarToZonedDateTime;
    }

    public Date getDateToCalendar() {
        return dateToCalendar;
    }

    public void setDateToCalendar(Date dateToCalendar) {
        this.dateToCalendar = dateToCalendar;
    }

    public Date getDateToXmlGregCal() {
        return dateToXmlGregCal;
    }

    public void setDateToXmlGregCal(Date dateToXmlGregCal) {
        this.dateToXmlGregCal = dateToXmlGregCal;
    }

    public List<XMLGregorianCalendar> getIterableSourceTarget() {
        return iterableSourceTarget;
    }

    public void setIterableSourceTarget(List<XMLGregorianCalendar> iterableSourceTarget) {
        this.iterableSourceTarget = iterableSourceTarget;
    }

    public String getStringToCalendar() {
        return stringToCalendar;
    }

    public void setStringToCalendar(String stringToCalendar) {
        this.stringToCalendar = stringToCalendar;
    }

    public String getStringToXmlGregCal() {
        return stringToXmlGregCal;
    }

    public void setStringToXmlGregCal(String stringToXmlGregCal) {
        this.stringToXmlGregCal = stringToXmlGregCal;
    }

    public XMLGregorianCalendar getXmlGregCalToCalendar() {
        return xmlGregCalToCalendar;
    }

    public void setXmlGregCalToCalendar(XMLGregorianCalendar xmlGregCalToCalendar) {
        this.xmlGregCalToCalendar = xmlGregCalToCalendar;
    }

    public XMLGregorianCalendar getXmlGregCalToDate() {
        return xmlGregCalToDate;
    }

    public void setXmlGregCalToDate(XMLGregorianCalendar xmlGregCalToDate) {
        this.xmlGregCalToDate = xmlGregCalToDate;
    }

    public XMLGregorianCalendar getXmlGregCalToString() {
        return xmlGregCalToString;
    }

    public void setXmlGregCalToString(XMLGregorianCalendar xmlGregCalToString) {
        this.xmlGregCalToString = xmlGregCalToString;
    }

    public ZonedDateTime getZonedDateTimeToCalendar() {
        return zonedDateTimeToCalendar;
    }

    public void setZonedDateTimeToCalendar(ZonedDateTime zonedDateTimeToCalendar) {
        this.zonedDateTimeToCalendar = zonedDateTimeToCalendar;
    }
}
