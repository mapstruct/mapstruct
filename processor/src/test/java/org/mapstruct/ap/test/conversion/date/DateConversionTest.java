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
package org.mapstruct.ap.test.conversion.date;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Tests application of format strings for conversions between strings and dates.
 *
 * @author Gunnar Morling
 */
@WithClasses({
    Source.class,
    Target.class,
    SourceTargetMapper.class
})
@IssueKey("43")
@RunWith(AnnotationProcessorTestRunner.class)
public class DateConversionTest {

    @Before
    public void setDefaultLocale() {
        Locale.setDefault( Locale.GERMAN );
    }

    @Test
    public void shouldApplyDateFormatForConversions() {
        Source source = new Source();
        source.setDate( new GregorianCalendar( 2013, 6, 6 ).getTime() );
        source.setAnotherDate( new GregorianCalendar( 2013, 1, 14 ).getTime() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getDate() ).isEqualTo( "06.07.2013" );
        assertThat( target.getAnotherDate() ).isEqualTo( "14.02.13 00:00" );
    }

    @Test
    public void shouldApplyDateFormatForConversionInReverseMapping() {
        Target target = new Target();
        target.setDate( "06.07.2013" );
        target.setAnotherDate( "14.02.13 8:30" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getDate() ).isEqualTo( new GregorianCalendar( 2013, 6, 6 ).getTime() );
        assertThat( source.getAnotherDate() ).isEqualTo( new GregorianCalendar( 2013, 1, 14, 8, 30 ).getTime() );
    }

    @Test
    public void shouldApplyStringConversionForIterableMethod() {
        List<Date> dates = Arrays.asList(
            new GregorianCalendar( 2013, 6, 6 ).getTime(),
            new GregorianCalendar( 2013, 1, 14 ).getTime(),
            new GregorianCalendar( 2013, 3, 11 ).getTime()
        );

        List<String> stringDates = SourceTargetMapper.INSTANCE.stringListToDateList( dates );

        assertThat( stringDates ).isNotNull();
        assertThat( stringDates ).containsExactly( "06.07.2013", "14.02.2013", "11.04.2013" );
    }

    @Test
    public void shouldApplyStringConversionForArrayMethod() {
        List<Date> dates = Arrays.asList(
            new GregorianCalendar( 2013, 6, 6 ).getTime(),
            new GregorianCalendar( 2013, 1, 14 ).getTime(),
            new GregorianCalendar( 2013, 3, 11 ).getTime()
        );

        String[] stringDates = SourceTargetMapper.INSTANCE.stringListToDateArray( dates );

        assertThat( stringDates ).isNotNull();
        assertThat( stringDates ).isEqualTo( new String[]{ "06.07.2013", "14.02.2013", "11.04.2013" } );
    }

    @Test
    public void shouldApplyStringConversionForReverseIterableMethod() {
        List<String> stringDates = Arrays.asList( "06.07.2013", "14.02.2013", "11.04.2013" );

        List<Date> dates = SourceTargetMapper.INSTANCE.dateListToStringList( stringDates );

        assertThat( dates ).isNotNull();
        assertThat( dates ).containsExactly(
            new GregorianCalendar( 2013, 6, 6 ).getTime(),
            new GregorianCalendar( 2013, 1, 14 ).getTime(),
            new GregorianCalendar( 2013, 3, 11 ).getTime()
        );
    }

    @Test
    public void shouldApplyStringConversionForReverseArrayMethod() {
        String[] stringDates = new String[]{ "06.07.2013", "14.02.2013", "11.04.2013" };

        List<Date> dates = SourceTargetMapper.INSTANCE.stringArrayToDateList( stringDates );

        assertThat( dates ).isNotNull();
        assertThat( dates ).containsExactly(
            new GregorianCalendar( 2013, 6, 6 ).getTime(),
            new GregorianCalendar( 2013, 1, 14 ).getTime(),
            new GregorianCalendar( 2013, 3, 11 ).getTime()
        );
    }


    @Test
    public void shouldApplyStringConversionForReverseArrayArrayMethod() {
         Date[] dates = new Date[]{
            new GregorianCalendar( 2013, 6, 6 ).getTime(),
            new GregorianCalendar( 2013, 1, 14 ).getTime(),
            new GregorianCalendar( 2013, 3, 11 ).getTime()
         };
        String[] stringDates = SourceTargetMapper.INSTANCE.dateArrayToStringArray( dates );

        assertThat( stringDates ).isNotNull();
        assertThat( stringDates ).isEqualTo( new String[]{ "06.07.2013", "14.02.2013", "11.04.2013" } );
    }

    @Test
    public void shouldApplyDateConversionForReverseArrayArrayMethod() {

        String[] stringDates = new String[]{ "06.07.2013", "14.02.2013", "11.04.2013" };
        Date[] dates = SourceTargetMapper.INSTANCE.stringArrayToDateArray( stringDates );

        assertThat( dates ).isNotNull();
        assertThat( dates ).isEqualTo( new Date[] {
            new GregorianCalendar( 2013, 6, 6 ).getTime(),
            new GregorianCalendar( 2013, 1, 14 ).getTime(),
            new GregorianCalendar( 2013, 3, 11 ).getTime()
        } );
    }
}
