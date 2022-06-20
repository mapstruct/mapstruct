/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junitpioneer.jupiter.DefaultTimeZone;
import org.mapstruct.ap.test.builtin.bean.CalendarProperty;
import org.mapstruct.ap.test.builtin.bean.DatatypeFactory;
import org.mapstruct.ap.test.builtin.bean.DateProperty;
import org.mapstruct.ap.test.builtin.bean.XmlGregorianCalendarFactorizedProperty;
import org.mapstruct.ap.test.builtin.mapper.ToXmlGregCalMapper;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses( {
    DatatypeFactory.class,
    XmlGregorianCalendarFactorizedProperty.class,
    ToXmlGregCalMapper.class,
    CalendarProperty.class,
    DateProperty.class

} )
@DefaultTimeZone("Europe/Berlin")
public class DatatypeFactoryTest {

    @ProcessorTest
    public void testNoConflictsWithOwnDatatypeFactory() throws ParseException {

        DateProperty source1 = new DateProperty();
        source1.setProp( createDate( "31-08-1982 10:20:56" ) );

        CalendarProperty source2 = new CalendarProperty();
        source2.setProp( createCalendar( "02.03.1999" ) );

        XmlGregorianCalendarFactorizedProperty target1 = ToXmlGregCalMapper.INSTANCE.map( source1 );
        assertThat( target1 ).isNotNull();
        assertThat( target1.getProp() ).isNotNull();
        assertThat( target1.getProp().toString() ).isEqualTo( "1982-08-31T10:20:56.000+02:00" );

        XmlGregorianCalendarFactorizedProperty target2 = ToXmlGregCalMapper.INSTANCE.map( source2 );
        assertThat( target2 ).isNotNull();
        assertThat( target2.getProp() ).isNotNull();
        assertThat( target2.getProp().toString() ).isEqualTo( "1999-03-02T00:00:00.000+01:00" );

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
}
