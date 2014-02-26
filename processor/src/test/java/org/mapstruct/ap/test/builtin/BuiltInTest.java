/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class BuiltInTest extends MapperTestBase {

    @Test
    @WithClasses( { Source.class, Target.class, SourceTargetMapper.class } )
    public void shouldApplyBuiltIn() throws ParseException, DatatypeConfigurationException {
        Source source = new Source();
        source.setProp1( createJaxb( "TEST" ) );
        source.setProp2( createJaxbList( "TEST2" ) );
        source.setProp3( createDate( "31-08-1982 10:20:56" ) );
        source.setProp4( createXmlCal( 1999, 3, 2, 1 ) );
        source.setProp5( "05.07.1999" );
        source.setProp5NoFormat( createLocaleDate( "31-08-1982 10:20:56" ) );
        source.setProp6( createXmlCal( 1999, 3, 2, 1 ) );
        source.setProp6NoFormat( createXmlCal( 1999, 3, 2, 1 ) );
        source.setProp7( createCalendar( "02.03.1999" ) );
        source.setProp8( createXmlCal( 1999, 3, 2, 1 ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getProp1() ).isEqualTo( "TEST" );
        assertThat( target.getProp2().get( 0 ) ).isEqualTo( "TEST2" );
        assertThat( target.getProp3() ).isNotNull();
        assertThat( target.getProp3().toString() ).isEqualTo( "1982-08-31T10:20:56.000+02:00" );
        assertThat( target.getProp4().toString() ).isEqualTo( "Tue Mar 02 00:00:00 CET 1999" );
        assertThat( target.getProp5().toString() ).isEqualTo( "1999-07-05T00:00:00.000+02:00" );
        assertThat( target.getProp5NoFormat().toString() ).isEqualTo( "1982-08-31T10:20:00.000+02:00" );
        assertThat( target.getProp6().toString() ).isEqualTo( "02.03.1999" );
        assertThat( target.getProp6NoFormat().toString() ).isEqualTo( "1999-03-02+00:01" );
        assertThat( target.getProp7().toString() ).isEqualTo( "1999-03-02T00:00:00.000+01:00" );
        assertThat( target.getProp8().getTimeInMillis() ).isEqualTo( 920329200000L );
    }

    @Test
    @WithClasses( { IterableSource.class, IterableTarget.class, IterableSourceTargetMapper.class } )
    public void shouldApplyBuiltInOnIterable() throws ParseException, DatatypeConfigurationException {

        IterableSource source = new IterableSource();
        source.setDates( Arrays.asList( new XMLGregorianCalendar[] { createXmlCal( 1999, 3, 2, 1 ) } ) );

        IterableTarget target = IterableSourceTargetMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getDates().size() ).isEqualTo( 1 );
        assertThat( target.getDates().get( 0 ) ).isEqualTo( "02.03.1999" );

    }

    @Test
    @WithClasses( { MapSource.class, MapTarget.class, MapSourceTargetMapper.class } )
    public void shouldApplyBuiltInOnMap() throws ParseException, DatatypeConfigurationException {

        MapSource source = new MapSource();
        source.setExample( new HashMap<JAXBElement<String>, XMLGregorianCalendar>() );
        source.getExample().put( createJaxb( "TEST" ), createXmlCal( 1999, 3, 2, 1 ) );

        MapTarget target = MapSourceTargetMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getExample().get( "TEST" ) ).isEqualTo( "1999-03-02+00:01" );
    }

    private JAXBElement<String> createJaxb( String test ) {
        return new JAXBElement<String>( new QName( "www.mapstruct.org", "test" ), String.class, test );
    }

    private List<JAXBElement<String>> createJaxbList( String test ) {
        List<JAXBElement<String>> result = new ArrayList<JAXBElement<String>>();
        result.add( createJaxb( test ) );
        return result;
    }

    private Date createDate( String date ) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-M-yyyy hh:mm:ss" );
        return sdf.parse( date );
    }

    private Calendar createCalendar( String cal ) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd.MM.yyyy" );
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.setTime( sdf.parse( cal ) );
        return gcal;
    }

    private XMLGregorianCalendar createXmlCal( int year, int month, int day, int tz )
            throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendarDate( year, month, day, tz );
    }

    private String createLocaleDate( String date ) throws ParseException {
        Date d = createDate( date );
        DateFormat df = SimpleDateFormat.getInstance();
        return df.format( d );
    }
}
