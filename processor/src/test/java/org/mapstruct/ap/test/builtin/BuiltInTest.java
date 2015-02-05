/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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

import org.mapstruct.ap.test.builtin.mapper.SourceTargetWithSqlDateMapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.builtin._target.IterableTarget;
import org.mapstruct.ap.test.builtin._target.MapTarget;
import org.mapstruct.ap.test.builtin._target.TargetWithDate;
import org.mapstruct.ap.test.builtin._target.TargetWithSqlDate;
import org.mapstruct.ap.test.builtin.bean.CalendarProperty;
import org.mapstruct.ap.test.builtin.bean.DateProperty;
import org.mapstruct.ap.test.builtin.bean.JaxbElementListProperty;
import org.mapstruct.ap.test.builtin.bean.JaxbElementProperty;
import org.mapstruct.ap.test.builtin.bean.StringListProperty;
import org.mapstruct.ap.test.builtin.bean.StringProperty;
import org.mapstruct.ap.test.builtin.bean.XmlGregorianCalendarProperty;
import org.mapstruct.ap.test.builtin.mapper.CalendarToDateMapper;
import org.mapstruct.ap.test.builtin.mapper.CalendarToStringMapper;
import org.mapstruct.ap.test.builtin.mapper.CalendarToXmlGregCalMapper;
import org.mapstruct.ap.test.builtin.mapper.DateToCalendarMapper;
import org.mapstruct.ap.test.builtin.mapper.DateToXmlGregCalMapper;
import org.mapstruct.ap.test.builtin.mapper.IterableSourceTargetMapper;
import org.mapstruct.ap.test.builtin.mapper.JaxbListMapper;
import org.mapstruct.ap.test.builtin.mapper.JaxbMapper;
import org.mapstruct.ap.test.builtin.mapper.MapSourceTargetMapper;
import org.mapstruct.ap.test.builtin.mapper.SourceTargetWithDateMapper;
import org.mapstruct.ap.test.builtin.mapper.StringToCalendarMapper;
import org.mapstruct.ap.test.builtin.mapper.StringToXmlGregCalMapper;
import org.mapstruct.ap.test.builtin.mapper.XmlGregCalToCalendarMapper;
import org.mapstruct.ap.test.builtin.mapper.XmlGregCalToDateMapper;
import org.mapstruct.ap.test.builtin.mapper.XmlGregCalToStringMapper;
import org.mapstruct.ap.test.builtin.source.IterableSource;
import org.mapstruct.ap.test.builtin.source.MapSource;
import org.mapstruct.ap.test.builtin.source.SourceWithDate;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test for the generation of built-in mapping methods.
 *
 * @author Sjaak Derksen
 */
@RunWith(AnnotationProcessorTestRunner.class)
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
    @WithClasses({ JaxbElementProperty.class, StringProperty.class, JaxbMapper.class })
    public void shoulApplyBuiltInOnJAXBElement() throws ParseException, DatatypeConfigurationException {

        JaxbElementProperty source = new JaxbElementProperty();
        source.setProp( createJaxb( "TEST" ) );

        StringProperty target = JaxbMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( "TEST" );
    }

    @Test
    @IssueKey( "141" )
    @WithClasses({ JaxbElementListProperty.class, StringListProperty.class, JaxbListMapper.class })
    public void shouldApplyBuiltInOnJAXBElementList() throws ParseException, DatatypeConfigurationException {

        JaxbElementListProperty source = new JaxbElementListProperty();
        source.setProp( createJaxbList( "TEST2" ) );

        StringListProperty target = JaxbListMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp().get( 0 ) ).isEqualTo( "TEST2" );
    }

    @Test
    @WithClasses({ DateProperty.class, XmlGregorianCalendarProperty.class, DateToXmlGregCalMapper.class })
    public void shouldApplyBuiltInOnDateToXmlGregCal() throws ParseException, DatatypeConfigurationException {

        DateProperty source = new DateProperty();
        source.setProp( createDate( "31-08-1982 10:20:56" ) );

        XmlGregorianCalendarProperty target = DateToXmlGregCalMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().toString() ).isEqualTo( "1982-08-31T10:20:56.000+02:00" );
    }

    @Test
    @WithClasses({ DateProperty.class, XmlGregorianCalendarProperty.class, XmlGregCalToDateMapper.class })
    public void shouldApplyBuiltInOnXmlGregCalToDate() throws ParseException, DatatypeConfigurationException {

        XmlGregorianCalendarProperty source = new XmlGregorianCalendarProperty();
        source.setProp( createXmlCal( 1999, 3, 2, 60 ) );

        DateProperty target = XmlGregCalToDateMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().toString() ).isEqualTo( "Tue Mar 02 00:00:00 CET 1999" );

    }

    @Test
    @WithClasses({ StringProperty.class, XmlGregorianCalendarProperty.class, StringToXmlGregCalMapper.class })
    public void shouldApplyBuiltInStringToXmlGregCal() throws ParseException, DatatypeConfigurationException {

        StringProperty source = new StringProperty();
        source.setProp( "05.07.1999" );

        XmlGregorianCalendarProperty target = StringToXmlGregCalMapper.INSTANCE.mapAndFormat( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().toString() ).isEqualTo( "1999-07-05T00:00:00.000+02:00" );

        // direct,via lexical representation
        source.setProp( "2000-03-04T23:00:00+03:00" );
        target = StringToXmlGregCalMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().toString() ).isEqualTo( "2000-03-04T23:00:00+03:00" );

        // null string
        source.setProp( null );
        target = StringToXmlGregCalMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNull();

    }

    @Test
    @WithClasses({ StringProperty.class, XmlGregorianCalendarProperty.class, XmlGregCalToStringMapper.class })
    public void shouldApplyBuiltInXmlGregCalToString() throws ParseException, DatatypeConfigurationException {

        XmlGregorianCalendarProperty source = new XmlGregorianCalendarProperty();
        source.setProp( createXmlCal( 1999, 3, 2, 60 ) );

        StringProperty target = XmlGregCalToStringMapper.INSTANCE.mapAndFormat( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( "02.03.1999" );

        source.setProp( createXmlCal( 1999, 3, 2, 60 ) );

        target = XmlGregCalToStringMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( "1999-03-02+01:00" );

    }

    @Test
    @WithClasses({ CalendarProperty.class, XmlGregorianCalendarProperty.class, CalendarToXmlGregCalMapper.class })
    public void shouldApplyBuiltInOnCalendarToXmlGregCal() throws ParseException, DatatypeConfigurationException {

        CalendarProperty source = new CalendarProperty();
        source.setProp( createCalendar( "02.03.1999" ) );

        XmlGregorianCalendarProperty target = CalendarToXmlGregCalMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().toString() ).isEqualTo( "1999-03-02T00:00:00.000+01:00" );
    }

    @Test
    @WithClasses({ CalendarProperty.class, XmlGregorianCalendarProperty.class, XmlGregCalToCalendarMapper.class })
    public void shouldApplyBuiltInOnXmlGregCalToCalendar() throws ParseException, DatatypeConfigurationException {

        XmlGregorianCalendarProperty source = new XmlGregorianCalendarProperty();
        source.setProp( createXmlCal( 1999, 3, 2, 60 ) );

        CalendarProperty target = XmlGregCalToCalendarMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().getTimeInMillis() ).isEqualTo( 920329200000L );

    }

    @Test
    @WithClasses({ DateProperty.class, CalendarProperty.class, CalendarToDateMapper.class })
    public void shouldApplyBuiltInOnCalendarToDate() throws ParseException, DatatypeConfigurationException {

        CalendarProperty source = new CalendarProperty();
        source.setProp( createCalendar( "02.03.1999" ) );

        DateProperty target = CalendarToDateMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp()).isEqualTo( createCalendar( "02.03.1999" ).getTime());
    }

    @Test
    @WithClasses({ CalendarProperty.class, DateProperty.class, DateToCalendarMapper.class })
    public void shouldApplyBuiltInOnDateToCalendar() throws ParseException, DatatypeConfigurationException {

        DateProperty source = new DateProperty();
        source.setProp( new SimpleDateFormat( "dd.MM.yyyy" ).parse( "02.03.1999" ) );

        CalendarProperty target = DateToCalendarMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp()).isEqualTo( createCalendar( "02.03.1999" ));

    }

    @Test
    @WithClasses({ StringProperty.class, CalendarProperty.class, CalendarToStringMapper.class })
    public void shouldApplyBuiltInOnCalendarToString() throws ParseException, DatatypeConfigurationException {

        CalendarProperty source = new CalendarProperty();
        source.setProp( createCalendar( "02.03.1999" ) );

        StringProperty target = CalendarToStringMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp()).isEqualTo( "02.03.1999" );
    }

    @Test
    @WithClasses({ CalendarProperty.class, StringProperty.class, StringToCalendarMapper.class })
    public void shouldApplyBuiltInOnStringToCalendar() throws ParseException, DatatypeConfigurationException {

        StringProperty source = new StringProperty();
        source.setProp( "02.03.1999" );

        CalendarProperty target = StringToCalendarMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp()).isEqualTo( createCalendar( "02.03.1999" ) );

    }

    @Test
    @WithClasses({ IterableSource.class, IterableTarget.class, IterableSourceTargetMapper.class })
    public void shouldApplyBuiltInOnIterable() throws ParseException, DatatypeConfigurationException {

        IterableSource source = new IterableSource();
        source.setDates( Arrays.asList( new XMLGregorianCalendar[] { createXmlCal( 1999, 3, 2, 60 ) } ) );

        IterableTarget target = IterableSourceTargetMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getDates() ).containsExactly( "02.03.1999" );
    }

    @Test
    @WithClasses({ MapSource.class, MapTarget.class, MapSourceTargetMapper.class })
    public void shouldApplyBuiltInOnMap() throws ParseException, DatatypeConfigurationException {

        MapSource source = new MapSource();
        source.setExample( new HashMap<JAXBElement<String>, XMLGregorianCalendar>() );
        source.getExample().put( createJaxb( "TEST" ), createXmlCal( 1999, 3, 2, 60 ) );

        MapTarget target = MapSourceTargetMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getExample().get( "TEST" ) ).isEqualTo( "1999-03-02+01:00" );
    }

    @Test
    @IssueKey( "248" )
    @WithClasses( { SourceWithDate.class, TargetWithDate.class, SourceTargetWithDateMapper.class } )
    public void dateToXmlGregorianCalenderHasCorrectImports() {
        assertThat( SourceTargetWithDateMapper.INSTANCE.toTargetWithDate( null ) ).isNull();

        TargetWithDate targetWithDate = SourceTargetWithDateMapper.INSTANCE.toTargetWithDate( new SourceWithDate() );
        assertThat( targetWithDate ).isNotNull();
        assertThat( targetWithDate.getDate() ).isNull();
    }

    @Test
    @IssueKey( "277" )
    @WithClasses( { SourceWithDate.class, TargetWithSqlDate.class, SourceTargetWithSqlDateMapper.class } )
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = SourceTargetWithSqlDateMapper.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 35,
                        messageRegExp = "Can't map property \"java\\.util\\.Date date\" to "
                                + "\"java\\.sql\\.Date date\"" )
            }
    )
    public void shouldNotMapJavaUtilDateToJavaSqlDate() {
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

    private String createLocaleDate(String date) throws ParseException {
        Date d = createDate( date );
        DateFormat df = SimpleDateFormat.getInstance();
        return df.format( d );
    }
}
