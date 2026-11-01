/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

public class BuildInTarget {

    // CHECKSTYLE:OFF
    public Date publicCalendarToDate;
    public String publicCalendarToString;
    public XMLGregorianCalendar publicCalendarToXmlGregCal;
    public ZonedDateTime publicCalendarToZonedDateTime;
    public Calendar publicDateToCalendar;
    public XMLGregorianCalendar publicDateToXmlGregCal;
    public List<String> publicIterableSourceTarget;
    public Calendar publicStringToCalendar;
    public Calendar publicStringToCalendarFormat;
    public XMLGregorianCalendar publicStringToXmlGregCal;
    public XMLGregorianCalendar publicStringToXmlGregCalFormat;
    public Calendar publicXmlGregCalToCalendar;
    public Date publicXmlGregCalToDate;
    public String publicXmlGregCalToString;
    public Calendar publicZonedDateTimeToCalendar;
    // CHECKSTYLE:ON

    private Date calendarToDate;
    private String calendarToString;
    private XMLGregorianCalendar calendarToXmlGregCal;
    private ZonedDateTime calendarToZonedDateTime;
    private Calendar dateToCalendar;
    private XMLGregorianCalendar dateToXmlGregCal;
    private List<String> iterableSourceTarget;
    private Calendar stringToCalendar;
    private Calendar stringToCalendarFormat;
    private XMLGregorianCalendar stringToXmlGregCal;
    private XMLGregorianCalendar stringToXmlGregCalFormat;
    private Calendar xmlGregCalToCalendar;
    private Date xmlGregCalToDate;
    private String xmlGregCalToString;
    private Calendar zonedDateTimeToCalendar;

    public Date getCalendarToDate() {
        return calendarToDate;
    }

    public void setCalendarToDate(Date calendarToDate) {
        this.calendarToDate = calendarToDate;
    }

    public String getCalendarToString() {
        return calendarToString;
    }

    public void setCalendarToString(String calendarToString) {
        this.calendarToString = calendarToString;
    }

    public XMLGregorianCalendar getCalendarToXmlGregCal() {
        return calendarToXmlGregCal;
    }

    public void setCalendarToXmlGregCal(XMLGregorianCalendar calendarToXmlGregCal) {
        this.calendarToXmlGregCal = calendarToXmlGregCal;
    }

    public ZonedDateTime getCalendarToZonedDateTime() {
        return calendarToZonedDateTime;
    }

    public void setCalendarToZonedDateTime(ZonedDateTime calendarToZonedDateTime) {
        this.calendarToZonedDateTime = calendarToZonedDateTime;
    }

    public Calendar getDateToCalendar() {
        return dateToCalendar;
    }

    public void setDateToCalendar(Calendar dateToCalendar) {
        this.dateToCalendar = dateToCalendar;
    }

    public XMLGregorianCalendar getDateToXmlGregCal() {
        return dateToXmlGregCal;
    }

    public void setDateToXmlGregCal(XMLGregorianCalendar dateToXmlGregCal) {
        this.dateToXmlGregCal = dateToXmlGregCal;
    }

    public List<String> getIterableSourceTarget() {
        return iterableSourceTarget;
    }

    public void setIterableSourceTarget(List<String> iterableSourceTarget) {
        this.iterableSourceTarget = iterableSourceTarget;
    }

    public Calendar getStringToCalendar() {
        return stringToCalendar;
    }

    public void setStringToCalendar(Calendar stringToCalendar) {
        this.stringToCalendar = stringToCalendar;
    }

    public Calendar getStringToCalendarFormat() {
        return stringToCalendarFormat;
    }

    public void setStringToCalendarFormat(Calendar stringToCalendarFormat) {
        this.stringToCalendarFormat = stringToCalendarFormat;
    }

    public XMLGregorianCalendar getStringToXmlGregCal() {
        return stringToXmlGregCal;
    }

    public void setStringToXmlGregCal(XMLGregorianCalendar stringToXmlGregCal) {
        this.stringToXmlGregCal = stringToXmlGregCal;
    }

    public XMLGregorianCalendar getStringToXmlGregCalFormat() {
        return stringToXmlGregCalFormat;
    }

    public void setStringToXmlGregCalFormat(XMLGregorianCalendar stringToXmlGregCalFormat) {
        this.stringToXmlGregCalFormat = stringToXmlGregCalFormat;
    }

    public Calendar getXmlGregCalToCalendar() {
        return xmlGregCalToCalendar;
    }

    public void setXmlGregCalToCalendar(Calendar xmlGregCalToCalendar) {
        this.xmlGregCalToCalendar = xmlGregCalToCalendar;
    }

    public Date getXmlGregCalToDate() {
        return xmlGregCalToDate;
    }

    public void setXmlGregCalToDate(Date xmlGregCalToDate) {
        this.xmlGregCalToDate = xmlGregCalToDate;
    }

    public String getXmlGregCalToString() {
        return xmlGregCalToString;
    }

    public void setXmlGregCalToString(String xmlGregCalToString) {
        this.xmlGregCalToString = xmlGregCalToString;
    }

    public Calendar getZonedDateTimeToCalendar() {
        return zonedDateTimeToCalendar;
    }

    public void setZonedDateTimeToCalendar(Calendar zonedDateTimeToCalendar) {
        this.zonedDateTimeToCalendar = zonedDateTimeToCalendar;
    }
}
