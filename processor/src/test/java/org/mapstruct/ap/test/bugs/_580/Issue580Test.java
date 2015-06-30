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
package org.mapstruct.ap.test.bugs._580;

import java.time.LocalDate;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Andreas Gudian
 */
@IssueKey("580")
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue580Test {

    @Test
    public void shouldNullCheckOnBuiltinAndConversion() {
        Target target = SourceTargetMapper.INSTANCE.toTarget( new Source() );

        assertThat( target ).isNotNull();
        assertThat( target.getDate() ).isNull();

        Source source = SourceTargetMapper.INSTANCE.toSource( new Target() );

        assertThat( source ).isNotNull();
        assertThat( source.getDate() ).isNull();
    }

    @Test
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
