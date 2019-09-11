/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time;

import java.time.LocalDateTime;
import java.util.Calendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.conversion.java8time.localdatetimetoxmlgregoriancalendarconversion.SourceTargetMapper;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.test.conversion.java8time.localdatetimetoxmlgregoriancalendarconversion.Source;
import org.mapstruct.ap.test.conversion.java8time.localdatetimetoxmlgregoriancalendarconversion.Target;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Andrei Arlou
 */
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class LocalDateTimeToXMLGregorianCalendarConversionTest {

    @Test
    public void shouldNullCheckOnConversionToTarget() {
        Target target = SourceTargetMapper.INSTANCE.toTarget( new Source() );

        assertThat( target ).isNotNull();
        assertThat( target.getLocalDateTime() ).isNull();
    }

    @Test
    public void shouldNullCheckOnConversionToSource() {
        Source source = SourceTargetMapper.INSTANCE.toSource( new Target() );

        assertThat( source ).isNotNull();
        assertThat( source.getXmlGregorianCalendar() ).isNull();
    }

    @Test
    public void shouldMapLocalDateTimeToXmlGregorianCalendarCorrectly() throws DatatypeConfigurationException {
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

    @Test
    public void shouldMapXmlGregorianCalendarToLocalDateTimeCorrectly() throws DatatypeConfigurationException {
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance()
            .newXMLGregorianCalendar( 1994, Calendar.MARCH, 5, 11, 30, 50, 500,
                DatatypeConstants.FIELD_UNDEFINED
            );

        Source source = new Source();
        source.setXmlGregorianCalendar( xmlGregorianCalendar );

        Target target = SourceTargetMapper.INSTANCE.toTarget( source );
        // without milliseconds and nanoseconds
        LocalDateTime expectedLocalDateTime = LocalDateTime.of( 1994, Calendar.MARCH, 5, 11, 30, 50 );

        assertThat( target ).isNotNull();
        assertThat( target.getLocalDateTime() ).isEqualTo( expectedLocalDateTime );
    }
}
