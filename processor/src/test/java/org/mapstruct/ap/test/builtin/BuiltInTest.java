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
package org.mapstruct.ap.test.builtin;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mapstruct.ap.test.builtin._target.IterableTarget;
import org.mapstruct.ap.test.builtin._target.MapTarget;
import org.mapstruct.ap.test.builtin.bean.CalendarProperty;
import org.mapstruct.ap.test.builtin.bean.DateProperty;
import org.mapstruct.ap.test.builtin.bean.JaxbElementListProperty;
import org.mapstruct.ap.test.builtin.bean.JaxbElementProperty;
import org.mapstruct.ap.test.builtin.bean.StringListProperty;
import org.mapstruct.ap.test.builtin.bean.StringProperty;
import org.mapstruct.ap.test.builtin.bean.XmlGregorianCalendarProperty;
import org.mapstruct.ap.test.builtin.java8time.bean.ZonedDateTimeProperty;
import org.mapstruct.ap.test.builtin.java8time.mapper.CalendarToZonedDateTimeMapper;
import org.mapstruct.ap.test.builtin.java8time.mapper.ZonedDateTimeToCalendarMapper;
import org.mapstruct.ap.test.builtin.mapper.CalendarToDateMapper;
import org.mapstruct.ap.test.builtin.mapper.CalendarToStringMapper;
import org.mapstruct.ap.test.builtin.mapper.CalendarToXmlGregCalMapper;
import org.mapstruct.ap.test.builtin.mapper.DateToCalendarMapper;
import org.mapstruct.ap.test.builtin.mapper.DateToXmlGregCalMapper;
import org.mapstruct.ap.test.builtin.mapper.IterableSourceTargetMapper;
import org.mapstruct.ap.test.builtin.mapper.JaxbListMapper;
import org.mapstruct.ap.test.builtin.mapper.JaxbMapper;
import org.mapstruct.ap.test.builtin.mapper.MapSourceTargetMapper;
import org.mapstruct.ap.test.builtin.mapper.StringToCalendarMapper;
import org.mapstruct.ap.test.builtin.mapper.StringToXmlGregCalMapper;
import org.mapstruct.ap.test.builtin.mapper.XmlGregCalToCalendarMapper;
import org.mapstruct.ap.test.builtin.mapper.XmlGregCalToDateMapper;
import org.mapstruct.ap.test.builtin.mapper.XmlGregCalToStringMapper;
import org.mapstruct.ap.test.builtin.source.IterableSource;
import org.mapstruct.ap.test.builtin.source.MapSource;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for the generation of built-in mapping methods.
 *
 * @author Sjaak Derksen
 */
@WithClasses({
    IterableTarget.class,
    MapTarget.class,
    CalendarProperty.class,
    DateProperty.class,
    JaxbElementListProperty.class,
    JaxbElementProperty.class,
    StringListProperty.class,
    StringProperty.class,
    XmlGregorianCalendarProperty.class,
    ZonedDateTimeProperty.class,
    CalendarToZonedDateTimeMapper.class,
    ZonedDateTimeToCalendarMapper.class,
    CalendarToDateMapper.class,
    CalendarToStringMapper.class,
    CalendarToXmlGregCalMapper.class,
    DateToCalendarMapper.class,
    DateToXmlGregCalMapper.class,
    IterableSourceTargetMapper.class,
    JaxbListMapper.class,
    JaxbMapper.class,
    MapSourceTargetMapper.class,
    StringToCalendarMapper.class,
    StringToXmlGregCalMapper.class,
    XmlGregCalToCalendarMapper.class,
    XmlGregCalToDateMapper.class,
    XmlGregCalToStringMapper.class,
    IterableSource.class,
    MapSource.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BuiltInTest {

    private static TimeZone originalTimeZone;

    @BeforeClass
    public static void setDefaultTimeZoneToCet() {
        originalTimeZone = TimeZone.getDefault();
        TimeZone.setDefault( TimeZone.getTimeZone( "Europe/Berlin" ) );
    }

    @AfterClass
    public static void restoreOriginalTimeZone() {
        TimeZone.setDefault( originalTimeZone );
    }

    @Test
    public void shouldApplyBuiltInOnJAXBElement() throws ParseException, DatatypeConfigurationException {

        JaxbElementProperty source = new JaxbElementProperty();
        source.setProp( createJaxb( "TEST" ) );
        source.publicProp = createJaxb( "PUBLIC TEST" );

        StringProperty target = JaxbMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( "TEST" );
        assertThat( target.publicProp ).isEqualTo( "PUBLIC TEST" );
    }

    @Test
    @IssueKey( "141" )
    public void shouldApplyBuiltInOnJAXBElementList() throws ParseException, DatatypeConfigurationException {

        JaxbElementListProperty source = new JaxbElementListProperty();
        source.setProp( createJaxbList( "TEST2" ) );
        source.publicProp = createJaxbList( "PUBLIC TEST2" );

        StringListProperty target = JaxbListMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp().get( 0 ) ).isEqualTo( "TEST2" );
        assertThat( target.publicProp.get( 0 ) ).isEqualTo( "PUBLIC TEST2" );
    }

    @Test
    public void shouldApplyBuiltInOnDateToXmlGregCal() throws ParseException, DatatypeConfigurationException {

        DateProperty source = new DateProperty();
        source.setProp( createDate( "31-08-1982 10:20:56" ) );
        source.publicProp = createDate( "31-08-2016 10:20:56" );

        XmlGregorianCalendarProperty target = DateToXmlGregCalMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().toString() ).isEqualTo( "1982-08-31T10:20:56.000+02:00" );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp.toString() ).isEqualTo( "2016-08-31T10:20:56.000+02:00" );
    }

    @Test
    public void shouldApplyBuiltInOnXmlGregCalToDate() throws ParseException, DatatypeConfigurationException {

        XmlGregorianCalendarProperty source = new XmlGregorianCalendarProperty();
        source.setProp( createXmlCal( 1999, 3, 2, 60 ) );
        source.publicProp = createXmlCal( 2016, 3, 2, 60 );

        DateProperty target = XmlGregCalToDateMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().toString() ).isEqualTo( "Tue Mar 02 00:00:00 CET 1999" );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp.toString() ).isEqualTo( "Wed Mar 02 00:00:00 CET 2016" );

    }

    @Test
    public void shouldApplyBuiltInStringToXmlGregCal() throws ParseException, DatatypeConfigurationException {

        StringProperty source = new StringProperty();
        source.setProp( "05.07.1999" );
        source.publicProp = "05.07.2016";

        XmlGregorianCalendarProperty target = StringToXmlGregCalMapper.INSTANCE.mapAndFormat( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().toString() ).isEqualTo( "1999-07-05T00:00:00.000+02:00" );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp.toString() ).isEqualTo( "2016-07-05T00:00:00.000+02:00" );

        // direct,via lexical representation
        source.setProp( "2000-03-04T23:00:00+03:00" );
        source.publicProp = "2016-03-04T23:00:00+03:00";
        target = StringToXmlGregCalMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().toString() ).isEqualTo( "2000-03-04T23:00:00+03:00" );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp.toString() ).isEqualTo( "2016-03-04T23:00:00+03:00" );

        // null string
        source.setProp( null );
        source.publicProp = null;
        target = StringToXmlGregCalMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNull();
        assertThat( target.publicProp ).isNull();

    }

    @Test
    public void shouldApplyBuiltInXmlGregCalToString() throws ParseException, DatatypeConfigurationException {

        XmlGregorianCalendarProperty source = new XmlGregorianCalendarProperty();
        source.setProp( createXmlCal( 1999, 3, 2, 60 ) );
        source.publicProp = createXmlCal( 2016, 3, 2, 60 );

        StringProperty target = XmlGregCalToStringMapper.INSTANCE.mapAndFormat( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( "02.03.1999" );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp ).isEqualTo( "02.03.2016" );

        source.setProp( createXmlCal( 1999, 3, 2, 60 ) );
        source.publicProp = createXmlCal( 2016, 3, 2, 60 );

        target = XmlGregCalToStringMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( "1999-03-02+01:00" );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp ).isEqualTo( "2016-03-02+01:00" );

    }

    @Test
    public void shouldApplyBuiltInOnCalendarToXmlGregCal() throws ParseException, DatatypeConfigurationException {

        CalendarProperty source = new CalendarProperty();
        source.setProp( createCalendar( "02.03.1999" ) );
        source.publicProp = createCalendar( "02.03.2016" );

        XmlGregorianCalendarProperty target = CalendarToXmlGregCalMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().toString() ).isEqualTo( "1999-03-02T00:00:00.000+01:00" );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp.toString() ).isEqualTo( "2016-03-02T00:00:00.000+01:00" );
    }

    @Test
    public void shouldApplyBuiltInOnXmlGregCalToCalendar() throws ParseException, DatatypeConfigurationException {

        XmlGregorianCalendarProperty source = new XmlGregorianCalendarProperty();
        source.setProp( createXmlCal( 1999, 3, 2, 60 ) );
        source.publicProp = createXmlCal( 2016, 3, 2, 60 );

        CalendarProperty target = XmlGregCalToCalendarMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().getTimeInMillis() ).isEqualTo( 920329200000L );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp.getTimeInMillis() ).isEqualTo( 1456873200000L );
        assertThat( target.publicProp.getTimeInMillis() ).isEqualTo( 1456873200000L );

    }

    @Test
    public void shouldApplyBuiltInOnCalendarToDate() throws ParseException, DatatypeConfigurationException {

        CalendarProperty source = new CalendarProperty();
        source.setProp( createCalendar( "02.03.1999" ) );
        source.publicProp = createCalendar( "02.03.2016" );

        DateProperty target = CalendarToDateMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( createCalendar( "02.03.1999" ).getTime() );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp ).isEqualTo( createCalendar( "02.03.2016" ).getTime() );
    }

    @Test
    public void shouldApplyBuiltInOnDateToCalendar() throws ParseException, DatatypeConfigurationException {

        DateProperty source = new DateProperty();
        source.setProp( new SimpleDateFormat( "dd.MM.yyyy" ).parse( "02.03.1999" ) );
        source.publicProp = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "02.03.2016" );

        CalendarProperty target = DateToCalendarMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( createCalendar( "02.03.1999" ) );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp ).isEqualTo( createCalendar( "02.03.2016" ) );

    }

    @Test
    public void shouldApplyBuiltInOnCalendarToString() throws ParseException, DatatypeConfigurationException {

        CalendarProperty source = new CalendarProperty();
        source.setProp( createCalendar( "02.03.1999" ) );
        source.publicProp = createCalendar( "02.03.2016" );

        StringProperty target = CalendarToStringMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( "02.03.1999" );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp ).isEqualTo( "02.03.2016" );
    }

    @Test
    public void shouldApplyBuiltInOnStringToCalendar() throws ParseException, DatatypeConfigurationException {

        StringProperty source = new StringProperty();
        source.setProp( "02.03.1999" );
        source.publicProp = "02.03.2016";

        CalendarProperty target = StringToCalendarMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( createCalendar( "02.03.1999" ) );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp ).isEqualTo( createCalendar( "02.03.2016" ) );

    }

    @Test
    public void shouldApplyBuiltInOnIterable() throws ParseException, DatatypeConfigurationException {

        IterableSource source = new IterableSource();
        source.setDates( Arrays.asList( new XMLGregorianCalendar[] { createXmlCal( 1999, 3, 2, 60 ) } ) );
        source.publicDates = Arrays.asList( new XMLGregorianCalendar[] { createXmlCal( 2016, 3, 2, 60 ) } );

        IterableTarget target = IterableSourceTargetMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getDates() ).containsExactly( "02.03.1999" );
        assertThat( target.publicDates ).containsExactly( "02.03.2016" );
    }

    @Test
    public void shouldApplyBuiltInOnMap() throws ParseException, DatatypeConfigurationException {

        MapSource source = new MapSource();
        source.setExample( new HashMap<JAXBElement<String>, XMLGregorianCalendar>() );
        source.getExample().put( createJaxb( "TEST" ), createXmlCal( 1999, 3, 2, 60 ) );
        source.publicExample = new HashMap<JAXBElement<String>, XMLGregorianCalendar>();
        source.publicExample.put( createJaxb( "TEST" ), createXmlCal( 2016, 3, 2, 60 ) );

        MapTarget target = MapSourceTargetMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getExample().get( "TEST" ) ).isEqualTo( "1999-03-02+01:00" );
        assertThat( target.publicExample.get( "TEST" ) ).isEqualTo( "2016-03-02+01:00" );
    }

    @Test
    public void shouldApplyBuiltInOnCalendarToZonedDateTime() throws ParseException {
        assertThat( CalendarToZonedDateTimeMapper.INSTANCE.map( null ) ).isNull();

        CalendarProperty source = new CalendarProperty();
        source.setProp( createCalendar( "02.03.1999" ) );
        source.publicProp = createCalendar( "02.03.2016" );

        ZonedDateTimeProperty target = CalendarToZonedDateTimeMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( ZonedDateTime.of( 1999, 3, 2, 0, 0, 0, 0, ZoneId.systemDefault() ) );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp ).isEqualTo( ZonedDateTime.of( 2016, 3, 2, 0, 0, 0, 0, ZoneId.systemDefault() ) );
    }

    @Test
    public void shouldApplyBuiltInOnZonedDateTimeToCalendar() throws ParseException {
        assertThat( ZonedDateTimeToCalendarMapper.INSTANCE.map( null ) ).isNull();

        ZonedDateTimeProperty source = new ZonedDateTimeProperty();
        source.setProp( ZonedDateTime.of( 1999, 3, 2, 0, 0, 0, 0, ZoneId.systemDefault() ) );
        source.publicProp = ZonedDateTime.of( 2016, 3, 2, 0, 0, 0, 0, ZoneId.systemDefault() );

        CalendarProperty target = ZonedDateTimeToCalendarMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( createCalendar( "02.03.1999" ) );
        assertThat( target.publicProp ).isNotNull();
        assertThat( target.publicProp ).isEqualTo( createCalendar( "02.03.2016" ) );
    }

    private JAXBElement<String> createJaxb(String test) {
        return new JAXBElement<String>( new QName( "www.mapstruct.org", "test" ), String.class, test );
    }

    private List<JAXBElement<String>> createJaxbList(String test) {
        List<JAXBElement<String>> result = new ArrayList<JAXBElement<String>>();
        result.add( createJaxb( test ) );
        return result;
    }

    private Date createDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-M-yyyy hh:mm:ss" );
        return sdf.parse( date );
    }

    private Calendar createCalendar(String cal) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd.MM.yyyy" );
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.setTime( sdf.parse( cal ) );
        return gcal;
    }

    private XMLGregorianCalendar createXmlCal(int year, int month, int day, int tz)
        throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendarDate( year, month, day, tz );
    }
}
