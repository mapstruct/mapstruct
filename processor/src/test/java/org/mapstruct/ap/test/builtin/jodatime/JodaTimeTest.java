/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.builtin.jodatime;

import static org.assertj.core.api.Assertions.assertThat;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.builtin.jodatime.bean.DateTimeBean;
import org.mapstruct.ap.test.builtin.jodatime.bean.LocalDateBean;
import org.mapstruct.ap.test.builtin.jodatime.bean.LocalDateTimeBean;
import org.mapstruct.ap.test.builtin.jodatime.bean.LocalTimeBean;
import org.mapstruct.ap.test.builtin.jodatime.bean.XmlGregorianCalendarBean;
import org.mapstruct.ap.test.builtin.jodatime.mapper.DateTimeToXmlGregorianCalendar;
import org.mapstruct.ap.test.builtin.jodatime.mapper.LocalDateTimeToXmlGregorianCalendar;
import org.mapstruct.ap.test.builtin.jodatime.mapper.LocalDateToXmlGregorianCalendar;
import org.mapstruct.ap.test.builtin.jodatime.mapper.LocalTimeToXmlGregorianCalendar;
import org.mapstruct.ap.test.builtin.jodatime.mapper.XmlGregorianCalendarToDateTime;
import org.mapstruct.ap.test.builtin.jodatime.mapper.XmlGregorianCalendarToLocalDate;
import org.mapstruct.ap.test.builtin.jodatime.mapper.XmlGregorianCalendarToLocalDateTime;
import org.mapstruct.ap.test.builtin.jodatime.mapper.XmlGregorianCalendarToLocalTime;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */
@WithClasses({
    DateTimeBean.class,
    LocalTimeBean.class,
    LocalDateBean.class,
    LocalDateTimeBean.class,
    XmlGregorianCalendarBean.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey( "689" )
public class JodaTimeTest {

    @Test
    @WithClasses(DateTimeToXmlGregorianCalendar.class)
    public void shouldMapDateTimeToXmlGregorianCalendar() {

        DateTimeBean  in = new DateTimeBean();
        DateTime dt = new DateTime(2010, 1, 15, 1, 1, 1, 100, DateTimeZone.forOffsetHours( -1 ) );
        in.setDateTime( dt );
        XmlGregorianCalendarBean res = DateTimeToXmlGregorianCalendar.INSTANCE.toXmlGregorianCalendarBean( in );

        assertThat( res.getxMLGregorianCalendar().getYear() ).isEqualTo( 2010 );
        assertThat( res.getxMLGregorianCalendar().getMonth() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getDay() ).isEqualTo( 15 );
        assertThat( res.getxMLGregorianCalendar().getHour() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getMinute() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getSecond() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getMillisecond() ).isEqualTo( 100 );
        assertThat( res.getxMLGregorianCalendar().getTimezone() ).isEqualTo( -60 );
    }

    @Test
    @WithClasses(DateTimeToXmlGregorianCalendar.class)
    public void shouldMapIncompleteDateTimeToXmlGregorianCalendar() {

        DateTimeBean  in = new DateTimeBean();
        DateTime dt = new DateTime(2010, 1, 15, 1, 1 );
        in.setDateTime( dt );
        XmlGregorianCalendarBean res = DateTimeToXmlGregorianCalendar.INSTANCE.toXmlGregorianCalendarBean( in );

        assertThat( res.getxMLGregorianCalendar().getYear() ).isEqualTo( 2010 );
        assertThat( res.getxMLGregorianCalendar().getMonth() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getDay() ).isEqualTo( 15 );
        assertThat( res.getxMLGregorianCalendar().getHour() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getMinute() ).isEqualTo( 1 );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToDateTime.class)
    public void shouldMapXmlGregorianCalendarToDateTime() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal =
            DatatypeFactory.newInstance().newXMLGregorianCalendar( 2010, 1, 15, 1, 1, 1, 100, 60 );
        in.setxMLGregorianCalendar( xcal );

        DateTimeBean res = XmlGregorianCalendarToDateTime.INSTANCE.toDateTimeBean( in );
        assertThat( res.getDateTime().getYear() ).isEqualTo( 2010 );
        assertThat( res.getDateTime().getMonthOfYear() ).isEqualTo( 1 );
        assertThat( res.getDateTime().getDayOfMonth() ).isEqualTo( 15 );
        assertThat( res.getDateTime().getHourOfDay() ).isEqualTo( 1 );
        assertThat( res.getDateTime().getMinuteOfHour() ).isEqualTo( 1 );
        assertThat( res.getDateTime().getSecondOfMinute() ).isEqualTo( 1 );
        assertThat( res.getDateTime().getMillisOfSecond() ).isEqualTo( 100 );
        assertThat( res.getDateTime().getZone().getOffset( null ) ).isEqualTo( 3600000 );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToDateTime.class)
    public void shouldMapXmlGregorianCalendarWithoutTimeZoneToDateTimeWithDefaultTimeZone() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xcal.setYear( 1999 );
        xcal.setMonth( 5 );
        xcal.setDay( 25 );
        xcal.setHour( 23 );
        xcal.setMinute( 34 );
        xcal.setSecond( 45 );
        xcal.setMillisecond( 500 );
        in.setxMLGregorianCalendar( xcal );

        DateTimeBean res = XmlGregorianCalendarToDateTime.INSTANCE.toDateTimeBean( in );
        assertThat( res.getDateTime().getYear() ).isEqualTo( 1999 );
        assertThat( res.getDateTime().getMonthOfYear() ).isEqualTo( 5 );
        assertThat( res.getDateTime().getDayOfMonth() ).isEqualTo( 25 );
        assertThat( res.getDateTime().getHourOfDay() ).isEqualTo( 23 );
        assertThat( res.getDateTime().getMinuteOfHour() ).isEqualTo( 34 );
        assertThat( res.getDateTime().getSecondOfMinute() ).isEqualTo( 45 );
        assertThat( res.getDateTime().getMillisOfSecond() ).isEqualTo( 500 );
        assertThat( res.getDateTime().getZone().getOffset( 0 ) ).isEqualTo( DateTimeZone.getDefault().getOffset( 0 ) );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToDateTime.class)
    public void shouldMapXmlGregorianCalendarWithoutMillisToDateTime() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xcal.setYear( 1999 );
        xcal.setMonth( 5 );
        xcal.setDay( 25 );
        xcal.setHour( 23 );
        xcal.setMinute( 34 );
        xcal.setSecond( 45 );
        xcal.setTimezone( 60 );
        in.setxMLGregorianCalendar( xcal );

        DateTimeBean res = XmlGregorianCalendarToDateTime.INSTANCE.toDateTimeBean( in );
        assertThat( res.getDateTime().getYear() ).isEqualTo( 1999 );
        assertThat( res.getDateTime().getMonthOfYear() ).isEqualTo( 5 );
        assertThat( res.getDateTime().getDayOfMonth() ).isEqualTo( 25 );
        assertThat( res.getDateTime().getHourOfDay() ).isEqualTo( 23 );
        assertThat( res.getDateTime().getMinuteOfHour() ).isEqualTo( 34 );
        assertThat( res.getDateTime().getSecondOfMinute() ).isEqualTo( 45 );
        assertThat( res.getDateTime().getMillisOfSecond() ).isEqualTo( 0 );
        assertThat( res.getDateTime().getZone().getOffset( null ) ).isEqualTo( 3600000 );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToDateTime.class)
    public void shouldMapXmlGregorianCalendarWithoutMillisAndTimeZoneToDateTimeWithDefaultTimeZone() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xcal.setYear( 1999 );
        xcal.setMonth( 5 );
        xcal.setDay( 25 );
        xcal.setHour( 23 );
        xcal.setMinute( 34 );
        xcal.setSecond( 45 );
        in.setxMLGregorianCalendar( xcal );

        DateTimeBean res = XmlGregorianCalendarToDateTime.INSTANCE.toDateTimeBean( in );
        assertThat( res.getDateTime().getYear() ).isEqualTo( 1999 );
        assertThat( res.getDateTime().getMonthOfYear() ).isEqualTo( 5 );
        assertThat( res.getDateTime().getDayOfMonth() ).isEqualTo( 25 );
        assertThat( res.getDateTime().getHourOfDay() ).isEqualTo( 23 );
        assertThat( res.getDateTime().getMinuteOfHour() ).isEqualTo( 34 );
        assertThat( res.getDateTime().getSecondOfMinute() ).isEqualTo( 45 );
        assertThat( res.getDateTime().getMillisOfSecond() ).isEqualTo( 0 );
        assertThat( res.getDateTime().getZone().getOffset( 0 ) ).isEqualTo( DateTimeZone.getDefault().getOffset( 0 ) );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToDateTime.class)
    public void shouldMapXmlGregorianCalendarWithoutSecondsToDateTime() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xcal.setYear( 1999 );
        xcal.setMonth( 5 );
        xcal.setDay( 25 );
        xcal.setHour( 23 );
        xcal.setMinute( 34 );
        xcal.setTimezone( 60 );
        in.setxMLGregorianCalendar( xcal );

        DateTimeBean res = XmlGregorianCalendarToDateTime.INSTANCE.toDateTimeBean( in );
        assertThat( res.getDateTime().getYear() ).isEqualTo( 1999 );
        assertThat( res.getDateTime().getMonthOfYear() ).isEqualTo( 5 );
        assertThat( res.getDateTime().getDayOfMonth() ).isEqualTo( 25 );
        assertThat( res.getDateTime().getHourOfDay() ).isEqualTo( 23 );
        assertThat( res.getDateTime().getMinuteOfHour() ).isEqualTo( 34 );
        assertThat( res.getDateTime().getSecondOfMinute() ).isEqualTo( 0 );
        assertThat( res.getDateTime().getMillisOfSecond() ).isEqualTo( 0 );
        assertThat( res.getDateTime().getZone().getOffset( null ) ).isEqualTo( 3600000 );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToDateTime.class)
    public void shouldMapXmlGregorianCalendarWithoutSecondsAndTimeZoneToDateTimeWithDefaultTimeZone() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xcal.setYear( 1999 );
        xcal.setMonth( 5 );
        xcal.setDay( 25 );
        xcal.setHour( 23 );
        xcal.setMinute( 34 );
        in.setxMLGregorianCalendar( xcal );

        DateTimeBean res = XmlGregorianCalendarToDateTime.INSTANCE.toDateTimeBean( in );
        assertThat( res.getDateTime().getYear() ).isEqualTo( 1999 );
        assertThat( res.getDateTime().getMonthOfYear() ).isEqualTo( 5 );
        assertThat( res.getDateTime().getDayOfMonth() ).isEqualTo( 25 );
        assertThat( res.getDateTime().getHourOfDay() ).isEqualTo( 23 );
        assertThat( res.getDateTime().getMinuteOfHour() ).isEqualTo( 34 );
        assertThat( res.getDateTime().getSecondOfMinute() ).isEqualTo( 0 );
        assertThat( res.getDateTime().getMillisOfSecond() ).isEqualTo( 0 );
        assertThat( res.getDateTime().getZone().getOffset( 0 ) ).isEqualTo( DateTimeZone.getDefault().getOffset( 0 ) );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToDateTime.class)
    public void shouldNotMapXmlGregorianCalendarWithoutMinutes() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xcal.setYear( 1999 );
        xcal.setMonth( 5 );
        xcal.setDay( 25 );
        xcal.setHour( 23 );
        in.setxMLGregorianCalendar( xcal );

        DateTimeBean res = XmlGregorianCalendarToDateTime.INSTANCE.toDateTimeBean( in );
        assertThat( res.getDateTime() ).isNull();
    }

    @Test
    @WithClasses({DateTimeToXmlGregorianCalendar.class, XmlGregorianCalendarToDateTime.class})
    public void shouldMapRoundTrip() {

        DateTimeBean  dtb1 = new DateTimeBean();
        DateTime dt = new DateTime(2010, 1, 15, 1, 1, 1, 100, DateTimeZone.forOffsetHours( -1 ) );
        dtb1.setDateTime( dt );
        XmlGregorianCalendarBean xcb1 = DateTimeToXmlGregorianCalendar.INSTANCE.toXmlGregorianCalendarBean( dtb1 );
        DateTimeBean dtb2 = XmlGregorianCalendarToDateTime.INSTANCE.toDateTimeBean( xcb1 );
        XmlGregorianCalendarBean xcb2 = DateTimeToXmlGregorianCalendar.INSTANCE.toXmlGregorianCalendarBean( dtb2 );

        assertThat( dtb1.getDateTime() ).isEqualTo( dtb2.getDateTime() );
        assertThat( xcb1.getxMLGregorianCalendar() ).isEqualTo( xcb2.getxMLGregorianCalendar() );

    }

    @Test
    @WithClasses(LocalDateTimeToXmlGregorianCalendar.class)
    public void shouldMapLocalDateTimeToXmlGregorianCalendar() {

        LocalDateTimeBean  in = new LocalDateTimeBean();
        LocalDateTime dt = new LocalDateTime(2010, 1, 15, 1, 1, 1, 100 );
        in.setLocalDateTime( dt );
        XmlGregorianCalendarBean res = LocalDateTimeToXmlGregorianCalendar.INSTANCE.toXmlGregorianCalendarBean( in );

        assertThat( res.getxMLGregorianCalendar().getYear() ).isEqualTo( 2010 );
        assertThat( res.getxMLGregorianCalendar().getMonth() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getDay() ).isEqualTo( 15 );
        assertThat( res.getxMLGregorianCalendar().getHour() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getMinute() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getSecond() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getMillisecond() ).isEqualTo( 100 );
        assertThat( res.getxMLGregorianCalendar().getTimezone() ).isEqualTo( DatatypeConstants.FIELD_UNDEFINED );
    }

    @Test
    @WithClasses(LocalDateTimeToXmlGregorianCalendar.class)
    public void shouldMapIncompleteLocalDateTimeToXmlGregorianCalendar() {

        LocalDateTimeBean  in = new LocalDateTimeBean();
        LocalDateTime dt = new LocalDateTime( 2010, 1, 15, 1, 1 );
        in.setLocalDateTime( dt );
        XmlGregorianCalendarBean res = LocalDateTimeToXmlGregorianCalendar.INSTANCE.toXmlGregorianCalendarBean( in );

        assertThat( res.getxMLGregorianCalendar().getYear() ).isEqualTo( 2010 );
        assertThat( res.getxMLGregorianCalendar().getMonth() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getDay() ).isEqualTo( 15 );
        assertThat( res.getxMLGregorianCalendar().getHour() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getMinute() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getSecond() ).isEqualTo( 0 );
        assertThat( res.getxMLGregorianCalendar().getMillisecond() ).isEqualTo( 0 );
        assertThat( res.getxMLGregorianCalendar().getTimezone() ).isEqualTo( DatatypeConstants.FIELD_UNDEFINED );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToLocalDateTime.class)
    public void shouldMapXmlGregorianCalendarToLocalDateTime() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal =
            DatatypeFactory.newInstance().newXMLGregorianCalendar( 2010, 1, 15, 1, 1, 1, 100, 60 );
        in.setxMLGregorianCalendar( xcal );

        LocalDateTimeBean res = XmlGregorianCalendarToLocalDateTime.INSTANCE.toDateTimeBean( in );
        assertThat( res.getLocalDateTime().getYear() ).isEqualTo( 2010 );
        assertThat( res.getLocalDateTime().getMonthOfYear() ).isEqualTo( 1 );
        assertThat( res.getLocalDateTime().getDayOfMonth() ).isEqualTo( 15 );
        assertThat( res.getLocalDateTime().getHourOfDay() ).isEqualTo( 1 );
        assertThat( res.getLocalDateTime().getMinuteOfHour() ).isEqualTo( 1 );
        assertThat( res.getLocalDateTime().getSecondOfMinute() ).isEqualTo( 1 );
        assertThat( res.getLocalDateTime().getMillisOfSecond() ).isEqualTo( 100 );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToLocalDateTime.class)
    public void shouldMapXmlGregorianCalendarWithoutMillisToLocalDateTime() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xcal.setYear( 1999 );
        xcal.setMonth( 5 );
        xcal.setDay( 25 );
        xcal.setHour( 23 );
        xcal.setMinute( 34 );
        xcal.setSecond( 45 );
        in.setxMLGregorianCalendar( xcal );

        LocalDateTimeBean res = XmlGregorianCalendarToLocalDateTime.INSTANCE.toDateTimeBean( in );
        assertThat( res.getLocalDateTime().getYear() ).isEqualTo( 1999 );
        assertThat( res.getLocalDateTime().getMonthOfYear() ).isEqualTo( 5 );
        assertThat( res.getLocalDateTime().getDayOfMonth() ).isEqualTo( 25 );
        assertThat( res.getLocalDateTime().getHourOfDay() ).isEqualTo( 23 );
        assertThat( res.getLocalDateTime().getMinuteOfHour() ).isEqualTo( 34 );
        assertThat( res.getLocalDateTime().getSecondOfMinute() ).isEqualTo( 45 );
        assertThat( res.getLocalDateTime().getMillisOfSecond() ).isEqualTo( 0 );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToLocalDateTime.class)
    public void shouldMapXmlGregorianCalendarWithoutSecondsToLocalDateTime() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xcal.setYear( 1999 );
        xcal.setMonth( 5 );
        xcal.setDay( 25 );
        xcal.setHour( 23 );
        xcal.setMinute( 34 );
        xcal.setTimezone( 60 );
        in.setxMLGregorianCalendar( xcal );

        LocalDateTimeBean res = XmlGregorianCalendarToLocalDateTime.INSTANCE.toDateTimeBean( in );
        assertThat( res.getLocalDateTime().getYear() ).isEqualTo( 1999 );
        assertThat( res.getLocalDateTime().getMonthOfYear() ).isEqualTo( 5 );
        assertThat( res.getLocalDateTime().getDayOfMonth() ).isEqualTo( 25 );
        assertThat( res.getLocalDateTime().getHourOfDay() ).isEqualTo( 23 );
        assertThat( res.getLocalDateTime().getMinuteOfHour() ).isEqualTo( 34 );
        assertThat( res.getLocalDateTime().getSecondOfMinute() ).isEqualTo( 0 );
        assertThat( res.getLocalDateTime().getMillisOfSecond() ).isEqualTo( 0 );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToLocalDateTime.class)
    public void shouldNotMapXmlGregorianCalendarWithoutMinutesToLocalDateTime() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xcal.setYear( 1999 );
        xcal.setMonth( 5 );
        xcal.setDay( 25 );
        xcal.setHour( 23 );
        in.setxMLGregorianCalendar( xcal );

        LocalDateTimeBean res = XmlGregorianCalendarToLocalDateTime.INSTANCE.toDateTimeBean( in );
        assertThat( res.getLocalDateTime() ).isNull();

    }

    @Test
    @WithClasses(LocalDateToXmlGregorianCalendar.class)
    public void shouldMapLocalDateToXmlGregorianCalendar() {

        LocalDateBean  in = new LocalDateBean();
        LocalDate dt = new LocalDate(2010, 1, 15 );
        in.setLocalDate( dt );
        XmlGregorianCalendarBean res = LocalDateToXmlGregorianCalendar.INSTANCE.toXmlGregorianCalendarBean( in );

        assertThat( res.getxMLGregorianCalendar().getYear() ).isEqualTo( 2010 );
        assertThat( res.getxMLGregorianCalendar().getMonth() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getDay() ).isEqualTo( 15 );
        assertThat( res.getxMLGregorianCalendar().getHour() ).isEqualTo( DatatypeConstants.FIELD_UNDEFINED );
        assertThat( res.getxMLGregorianCalendar().getMinute() ).isEqualTo( DatatypeConstants.FIELD_UNDEFINED );
        assertThat( res.getxMLGregorianCalendar().getSecond() ).isEqualTo( DatatypeConstants.FIELD_UNDEFINED );
        assertThat( res.getxMLGregorianCalendar().getMillisecond() ).isEqualTo( DatatypeConstants.FIELD_UNDEFINED );
        assertThat( res.getxMLGregorianCalendar().getTimezone() ).isEqualTo( DatatypeConstants.FIELD_UNDEFINED );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToLocalDate.class)
    public void shouldMapXmlGregorianCalendarToLocalDate() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
            1999,
            5,
            25,
            DatatypeConstants.FIELD_UNDEFINED );
        in.setxMLGregorianCalendar( xcal );

        LocalDateBean res = XmlGregorianCalendarToLocalDate.INSTANCE.toLocalDateBean( in );
        assertThat( res.getLocalDate().getYear() ).isEqualTo( 1999 );
        assertThat( res.getLocalDate().getMonthOfYear() ).isEqualTo( 5 );
        assertThat( res.getLocalDate().getDayOfMonth() ).isEqualTo( 25 );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToLocalDate.class)
    public void shouldNotMapXmlGregorianCalendarWithoutDaysToLocalDate() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
            1999,
            5,
            DatatypeConstants.FIELD_UNDEFINED,
            DatatypeConstants.FIELD_UNDEFINED );
        in.setxMLGregorianCalendar( xcal );

        LocalDateBean res = XmlGregorianCalendarToLocalDate.INSTANCE.toLocalDateBean( in );
        assertThat( res.getLocalDate() ).isNull();

    }

    @Test
    @WithClasses(LocalTimeToXmlGregorianCalendar.class)
    public void shouldMapIncompleteLocalTimeToXmlGregorianCalendar() {

        LocalTimeBean  in = new LocalTimeBean();
        LocalTime dt = new LocalTime( 1, 1, 0, 100 );
        in.setLocalTime( dt );
        XmlGregorianCalendarBean res = LocalTimeToXmlGregorianCalendar.INSTANCE.toXmlGregorianCalendarBean( in );

        assertThat( res.getxMLGregorianCalendar().getYear() ).isEqualTo( DatatypeConstants.FIELD_UNDEFINED );
        assertThat( res.getxMLGregorianCalendar().getMonth() ).isEqualTo( DatatypeConstants.FIELD_UNDEFINED );
        assertThat( res.getxMLGregorianCalendar().getDay() ).isEqualTo( DatatypeConstants.FIELD_UNDEFINED );
        assertThat( res.getxMLGregorianCalendar().getHour() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getMinute() ).isEqualTo( 1 );
        assertThat( res.getxMLGregorianCalendar().getSecond() ).isEqualTo( 0 );
        assertThat( res.getxMLGregorianCalendar().getMillisecond() ).isEqualTo( 100 );
        assertThat( res.getxMLGregorianCalendar().getTimezone() ).isEqualTo( DatatypeConstants.FIELD_UNDEFINED );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToLocalTime.class)
    public void shouldMapXmlGregorianCalendarToLocalTime() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal =
            DatatypeFactory.newInstance().newXMLGregorianCalendarTime( 1, 1, 1, 100, 60 );
        in.setxMLGregorianCalendar( xcal );

        LocalTimeBean res = XmlGregorianCalendarToLocalTime.INSTANCE.toLocalTimeBean( in );
        assertThat( res.getLocalTime().getHourOfDay() ).isEqualTo( 1 );
        assertThat( res.getLocalTime().getMinuteOfHour() ).isEqualTo( 1 );
        assertThat( res.getLocalTime().getSecondOfMinute() ).isEqualTo( 1 );
        assertThat( res.getLocalTime().getMillisOfSecond() ).isEqualTo( 100 );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToLocalTime.class)
    public void shouldMapXmlGregorianCalendarWithoutMillisToLocalTime() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xcal.setHour( 23 );
        xcal.setMinute( 34 );
        xcal.setSecond( 45 );
        in.setxMLGregorianCalendar( xcal );

        LocalTimeBean res = XmlGregorianCalendarToLocalTime.INSTANCE.toLocalTimeBean( in );
        assertThat( res.getLocalTime().getHourOfDay() ).isEqualTo( 23 );
        assertThat( res.getLocalTime().getMinuteOfHour() ).isEqualTo( 34 );
        assertThat( res.getLocalTime().getSecondOfMinute() ).isEqualTo( 45 );
        assertThat( res.getLocalTime().getMillisOfSecond() ).isEqualTo( 0 );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToLocalTime.class)
    public void shouldMapXmlGregorianCalendarWithoutSecondsToLocalTime() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xcal.setHour( 23 );
        xcal.setMinute( 34 );
        xcal.setTimezone( 60 );
        in.setxMLGregorianCalendar( xcal );

        LocalTimeBean res = XmlGregorianCalendarToLocalTime.INSTANCE.toLocalTimeBean( in );
        assertThat( res.getLocalTime().getHourOfDay() ).isEqualTo( 23 );
        assertThat( res.getLocalTime().getMinuteOfHour() ).isEqualTo( 34 );
        assertThat( res.getLocalTime().getSecondOfMinute() ).isEqualTo( 0 );
        assertThat( res.getLocalTime().getMillisOfSecond() ).isEqualTo( 0 );
    }

    @Test
    @WithClasses(XmlGregorianCalendarToLocalTime.class)
    public void shouldNotMapXmlGregorianCalendarWithoutMinutesToLocalTime() throws Exception {

        XmlGregorianCalendarBean in = new XmlGregorianCalendarBean();
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xcal.setYear( 1999 );
        xcal.setMonth( 5 );
        xcal.setDay( 25 );
        xcal.setHour( 23 );
        in.setxMLGregorianCalendar( xcal );

        LocalTimeBean res = XmlGregorianCalendarToLocalTime.INSTANCE.toLocalTimeBean( in );
        assertThat( res.getLocalTime() ).isNull();

    }

}
