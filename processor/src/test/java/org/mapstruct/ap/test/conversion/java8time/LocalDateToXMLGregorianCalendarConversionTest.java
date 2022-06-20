/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time;

import java.time.LocalDate;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.ap.test.conversion.java8time.localdatetoxmlgregoriancalendarconversion.Source;
import org.mapstruct.ap.test.conversion.java8time.localdatetoxmlgregoriancalendarconversion.SourceTargetMapper;
import org.mapstruct.ap.test.conversion.java8time.localdatetoxmlgregoriancalendarconversion.Target;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Andreas Gudian
 */
@IssueKey("580")
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
public class LocalDateToXMLGregorianCalendarConversionTest {

    @ProcessorTest
    public void shouldNullCheckOnBuiltinAndConversion() {
        Target target = SourceTargetMapper.INSTANCE.toTarget( new Source() );

        assertThat( target ).isNotNull();
        assertThat( target.getDate() ).isNull();

        Source source = SourceTargetMapper.INSTANCE.toSource( new Target() );

        assertThat( source ).isNotNull();
        assertThat( source.getDate() ).isNull();
    }

    @ProcessorTest
    public void shouldMapCorrectlyOnBuiltinAndConversion() throws Exception {
        XMLGregorianCalendar calendarDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
            2007,
            11,
            14,
            DatatypeConstants.FIELD_UNDEFINED );

        LocalDate localDate = LocalDate.of( 2007, 11, 14 );

        Source s1 = new Source();
        s1.setDate( calendarDate );
        Target target = SourceTargetMapper.INSTANCE.toTarget( s1 );

        assertThat( target ).isNotNull();
        assertThat( target.getDate() ).isEqualTo( localDate );


        Target t1 = new Target();
        t1.setDate( localDate );
        Source source = SourceTargetMapper.INSTANCE.toSource( t1 );

        assertThat( source ).isNotNull();
        assertThat( source.getDate() ).isEqualTo( calendarDate );
    }
}
