/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Calendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.ap.test.conversion.java8time.localdatetimetoxmlgregoriancalendarconversion.Source;
import org.mapstruct.ap.test.conversion.java8time.localdatetimetoxmlgregoriancalendarconversion.SourceTargetMapper;
import org.mapstruct.ap.test.conversion.java8time.localdatetimetoxmlgregoriancalendarconversion.Target;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Andrei Arlou
 */
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
public class LocalDateTimeToXMLGregorianCalendarConversionTest {

    @ProcessorTest
    public void shouldNullCheckOnConversionToTarget() {
        Target target = SourceTargetMapper.INSTANCE.toTarget( new Source() );

        assertThat( target ).isNotNull();
        assertThat( target.getLocalDateTime() ).isNull();
    }

    @ProcessorTest
    public void shouldNullCheckOnConversionToSource() {
        Source source = SourceTargetMapper.INSTANCE.toSource( new Target() );

        assertThat( source ).isNotNull();
        assertThat( source.getXmlGregorianCalendar() ).isNull();
    }

    @ProcessorTest
    public void shouldMapLocalDateTimeToXmlGregorianCalendarCorrectlyWithNanoseconds()
                                                throws DatatypeConfigurationException {
        LocalDateTime localDateTime = LocalDateTime.of( 1994, Calendar.MARCH, 5, 11, 30, 50, 9000000 );
        Target target = new Target();
        target.setLocalDateTime( localDateTime );

        Source source = SourceTargetMapper.INSTANCE.toSource( target );

        XMLGregorianCalendar expectedCalendar = DatatypeFactory.newInstance()
            .newXMLGregorianCalendar( 1994, Calendar.MARCH, 5, 11, 30, 50, 9,
                DatatypeConstants.FIELD_UNDEFINED
            );

        assertThat( source ).isNotNull();
        assertThat( source.getXmlGregorianCalendar() ).isEqualTo( expectedCalendar );
    }

    @ProcessorTest
    public void shouldMapLocalDateTimeToXmlGregorianCalendarCorrectlyWithSeconds()
                                                throws DatatypeConfigurationException {
        LocalDateTime localDateTime = LocalDateTime.of( 1994, Calendar.MARCH, 5, 11, 30, 50 );
        Target target = new Target();
        target.setLocalDateTime( localDateTime );

        Source source = SourceTargetMapper.INSTANCE.toSource( target );

        XMLGregorianCalendar expectedCalendar = DatatypeFactory.newInstance()
            .newXMLGregorianCalendar( 1994, Calendar.MARCH, 5, 11, 30, 50, 0,
                DatatypeConstants.FIELD_UNDEFINED
            );

        assertThat( source ).isNotNull();
        assertThat( source.getXmlGregorianCalendar() ).isEqualTo( expectedCalendar );
    }

    @ProcessorTest
    public void shouldMapLocalDateTimeToXmlGregorianCalendarCorrectlyWithMinutes()
                                                throws DatatypeConfigurationException {
        LocalDateTime localDateTime = LocalDateTime.of( 1994, Calendar.MARCH, 5, 11, 30 );
        Target target = new Target();
        target.setLocalDateTime( localDateTime );

        Source source = SourceTargetMapper.INSTANCE.toSource( target );

        XMLGregorianCalendar expectedCalendar = DatatypeFactory.newInstance()
            .newXMLGregorianCalendar( 1994, Calendar.MARCH, 5, 11, 30, 0, 0,
                DatatypeConstants.FIELD_UNDEFINED
            );

        assertThat( source ).isNotNull();
        assertThat( source.getXmlGregorianCalendar() ).isEqualTo( expectedCalendar );
    }

    @ProcessorTest
    public void shouldMapXmlGregorianCalendarToLocalDateTimeCorrectly() throws DatatypeConfigurationException {
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance()
            .newXMLGregorianCalendar( 1994, Calendar.MARCH, 5, 11, 30, 50, 500,
                DatatypeConstants.FIELD_UNDEFINED
            );

        Source source = new Source();
        source.setXmlGregorianCalendar( xmlGregorianCalendar );

        Target target = SourceTargetMapper.INSTANCE.toTarget( source );

        LocalDateTime expectedLocalDateTime = LocalDateTime.of( 1994, Calendar.MARCH, 5, 11, 30, 50, 500000000 );

        assertThat( target ).isNotNull();
        assertThat( target.getLocalDateTime() ).isEqualTo( expectedLocalDateTime );
    }
}
