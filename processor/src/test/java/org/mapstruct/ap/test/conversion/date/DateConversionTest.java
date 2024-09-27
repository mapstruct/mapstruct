/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.date;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junitpioneer.jupiter.DefaultLocale;
import org.junitpioneer.jupiter.ReadsDefaultTimeZone;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

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
@DefaultLocale("de")
@ReadsDefaultTimeZone
public class DateConversionTest {

    @ProcessorTest
    @EnabledOnJre( JRE.JAVA_8 )
    // See https://bugs.openjdk.java.net/browse/JDK-8211262, there is a difference in the default formats on Java 9+
    public void shouldApplyDateFormatForConversions() {
        Source source = new Source();
        source.setDate( new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime() );
        source.setAnotherDate( new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getDate() ).isEqualTo( "06.07.2013" );
        assertThat( target.getAnotherDate() ).isEqualTo( "14.02.13 00:00" );
    }

    @ProcessorTest
    @EnabledOnJre( JRE.JAVA_8 )
    // See https://bugs.openjdk.java.net/browse/JDK-8211262, there is a difference in the default formats on Java 9+
    public void shouldApplyDateFormatForConversionsWithCustomLocale() {
        Source source = new Source();
        source.setDate( new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime() );
        source.setAnotherDate( new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTargetWithCustomLocale( source );

        assertThat( target ).isNotNull();
        assertThat( target.getDate() ).isEqualTo( "juillet 06, 2013" );
        assertThat( target.getAnotherDate() ).isEqualTo( "14.02.13, 00:00" );
    }

    @ProcessorTest
    @EnabledForJreRange(min = JRE.JAVA_11)
    // See https://bugs.openjdk.java.net/browse/JDK-8211262, there is a difference in the default formats on Java 9+
    public void shouldApplyDateFormatForConversionsJdk11() {
        Source source = new Source();
        source.setDate( new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime() );
        source.setAnotherDate( new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getDate() ).isEqualTo( "06.07.2013" );
        assertThat( target.getAnotherDate() ).isEqualTo( "14.02.13, 00:00" );
    }

    @ProcessorTest
    @EnabledForJreRange(min = JRE.JAVA_11)
    // See https://bugs.openjdk.java.net/browse/JDK-8211262, there is a difference in the default formats on Java 9+
    public void shouldApplyDateFormatForConversionsJdk11WithCustomLocale() {
        Source source = new Source();
        source.setDate( new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime() );
        source.setAnotherDate( new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTargetWithCustomLocale( source );

        assertThat( target ).isNotNull();
        assertThat( target.getDate() ).isEqualTo( "juillet 06, 2013" );
        assertThat( target.getAnotherDate() ).isEqualTo( "14.02.13, 00:00" );
    }

    @ProcessorTest
    @EnabledOnJre(JRE.JAVA_8)
    // See https://bugs.openjdk.java.net/browse/JDK-8211262, there is a difference in the default formats on Java 9+
    public void shouldApplyDateFormatForConversionInReverseMapping() {
        Target target = new Target();
        target.setDate( "06.07.2013" );
        target.setAnotherDate( "14.02.13 8:30" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getDate() ).isEqualTo( new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime() );
        assertThat( source.getAnotherDate() ).isEqualTo(
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14, 8, 30 ).getTime()
        );
    }

    @ProcessorTest
    @EnabledOnJre(JRE.JAVA_8)
    // See https://bugs.openjdk.java.net/browse/JDK-8211262, there is a difference in the default formats on Java 9+
    public void shouldApplyDateFormatForConversionInReverseMappingWithCustomLocale() {
        Target target = new Target();
        target.setDate( "juillet 06, 2013" );
        target.setAnotherDate( "14.02.13 8:30" );

        Source source = SourceTargetMapper.INSTANCE.targetToSourceWithCustomLocale( target );

        assertThat( source ).isNotNull();
        assertThat( source.getDate() ).isEqualTo( new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime() );
        assertThat( source.getAnotherDate() ).isEqualTo(
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14, 8, 30 ).getTime()
        );
    }

    @ProcessorTest
    @EnabledForJreRange(min = JRE.JAVA_11)
    // See https://bugs.openjdk.java.net/browse/JDK-8211262, there is a difference in the default formats on Java 9+
    public void shouldApplyDateFormatForConversionInReverseMappingJdk11() {
        Target target = new Target();
        target.setDate( "06.07.2013" );
        target.setAnotherDate( "14.02.13, 8:30" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getDate() ).isEqualTo( new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime() );
        assertThat( source.getAnotherDate() ).isEqualTo(
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14, 8, 30 ).getTime()
        );
    }

    @ProcessorTest
    @EnabledForJreRange(min = JRE.JAVA_11)
    // See https://bugs.openjdk.java.net/browse/JDK-8211262, there is a difference in the default formats on Java 9+
    public void shouldApplyDateFormatForConversionInReverseMappingJdk11WithCustomLocale() {
        Target target = new Target();
        target.setDate( "juillet 06, 2013" );
        target.setAnotherDate( "14.02.13, 8:30" );

        Source source = SourceTargetMapper.INSTANCE.targetToSourceWithCustomLocale( target );

        assertThat( source ).isNotNull();
        assertThat( source.getDate() ).isEqualTo( new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime() );
        assertThat( source.getAnotherDate() ).isEqualTo(
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14, 8, 30 ).getTime()
        );
    }

    @ProcessorTest
    public void shouldApplyStringConversionForIterableMethod() {
        List<Date> dates = Arrays.asList(
            new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime(),
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime(),
            new GregorianCalendar( 2013, Calendar.APRIL, 11 ).getTime()
        );

        List<String> stringDates = SourceTargetMapper.INSTANCE.stringListToDateList( dates );

        assertThat( stringDates ).isNotNull();
        assertThat( stringDates ).containsExactly( "06.07.2013", "14.02.2013", "11.04.2013" );
    }

    @ProcessorTest
    public void shouldApplyStringConversionForIterableMethodWithCustomLocale() {
        List<Date> dates = Arrays.asList(
            new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime(),
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime(),
            new GregorianCalendar( 2013, Calendar.APRIL, 11 ).getTime()
        );

        List<String> stringDates = SourceTargetMapper.INSTANCE.stringListToDateListWithCustomLocale( dates );

        assertThat( stringDates ).isNotNull();
        assertThat( stringDates ).containsExactly( "juillet 06, 2013", "février 14, 2013", "avril 11, 2013" );
    }

    @ProcessorTest
    public void shouldApplyStringConversionForArrayMethod() {
        List<Date> dates = Arrays.asList(
            new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime(),
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime(),
            new GregorianCalendar( 2013, Calendar.APRIL, 11 ).getTime()
        );

        String[] stringDates = SourceTargetMapper.INSTANCE.stringListToDateArray( dates );

        assertThat( stringDates ).isNotNull();
        assertThat( stringDates ).isEqualTo( new String[]{ "06.07.2013", "14.02.2013", "11.04.2013" } );
    }

    @ProcessorTest
    public void shouldApplyStringConversionForArrayMethodWithCustomLocale() {
        List<Date> dates = Arrays.asList(
            new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime(),
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime(),
            new GregorianCalendar( 2013, Calendar.APRIL, 11 ).getTime()
        );

        String[] stringDates = SourceTargetMapper.INSTANCE.stringListToDateArrayWithCustomLocale( dates );

        assertThat( stringDates ).isNotNull();
        assertThat( stringDates ).isEqualTo( new String[]{ "juillet 06, 2013", "février 14, 2013", "avril 11, 2013" } );
    }

    @ProcessorTest
    public void shouldApplyStringConversionForReverseIterableMethod() {
        List<String> stringDates = Arrays.asList( "06.07.2013", "14.02.2013", "11.04.2013" );

        List<Date> dates = SourceTargetMapper.INSTANCE.dateListToStringList( stringDates );

        assertThat( dates ).isNotNull();
        assertThat( dates ).containsExactly(
            new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime(),
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime(),
            new GregorianCalendar( 2013, Calendar.APRIL, 11 ).getTime()
        );
    }

    @ProcessorTest
    public void shouldApplyStringConversionForReverseIterableMethodWithCustomLocale() {
        List<String> stringDates = Arrays.asList( "juillet 06, 2013", "février 14, 2013", "avril 11, 2013" );

        List<Date> dates = SourceTargetMapper.INSTANCE.dateListToStringListWithCustomLocale( stringDates );

        assertThat( dates ).isNotNull();
        assertThat( dates ).containsExactly(
            new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime(),
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime(),
            new GregorianCalendar( 2013, Calendar.APRIL, 11 ).getTime()
        );
    }

    @ProcessorTest
    public void shouldApplyStringConversionForReverseArrayMethod() {
        String[] stringDates = new String[]{ "06.07.2013", "14.02.2013", "11.04.2013" };

        List<Date> dates = SourceTargetMapper.INSTANCE.stringArrayToDateList( stringDates );

        assertThat( dates ).isNotNull();
        assertThat( dates ).containsExactly(
            new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime(),
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime(),
            new GregorianCalendar( 2013, Calendar.APRIL, 11 ).getTime()
        );
    }

    @ProcessorTest
    public void shouldApplyStringConversionForReverseArrayMethodWithCustomLocale() {
        String[] stringDates = new String[]{ "juillet 06, 2013", "février 14, 2013", "avril 11, 2013" };

        List<Date> dates = SourceTargetMapper.INSTANCE.stringArrayToDateListWithCustomLocale( stringDates );

        assertThat( dates ).isNotNull();
        assertThat( dates ).containsExactly(
            new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime(),
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime(),
            new GregorianCalendar( 2013, Calendar.APRIL, 11 ).getTime()
        );
    }

    @ProcessorTest
    public void shouldApplyStringConversionForReverseArrayArrayMethod() {
         Date[] dates = new Date[]{
            new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime(),
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime(),
            new GregorianCalendar( 2013, Calendar.APRIL, 11 ).getTime()
         };
        String[] stringDates = SourceTargetMapper.INSTANCE.dateArrayToStringArray( dates );

        assertThat( stringDates ).isNotNull();
        assertThat( stringDates ).isEqualTo( new String[]{ "06.07.2013", "14.02.2013", "11.04.2013" } );
    }

    @ProcessorTest
    public void shouldApplyDateConversionForReverseArrayArrayMethod() {

        String[] stringDates = new String[]{ "06.07.2013", "14.02.2013", "11.04.2013" };
        Date[] dates = SourceTargetMapper.INSTANCE.stringArrayToDateArray( stringDates );

        assertThat( dates ).isNotNull();
        assertThat( dates ).isEqualTo( new Date[] {
            new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime(),
            new GregorianCalendar( 2013, Calendar.FEBRUARY, 14 ).getTime(),
            new GregorianCalendar( 2013, Calendar.APRIL, 11 ).getTime()
        } );
    }

    @IssueKey("858")
    @ProcessorTest
    public void shouldApplyDateToSqlConversion() {
        GregorianCalendar time = new GregorianCalendar( 2016, Calendar.AUGUST, 24, 20, 30, 30 );
        GregorianCalendar sqlDate = new GregorianCalendar( 2016, Calendar.AUGUST, 23, 21, 35, 35 );
        GregorianCalendar timestamp = new GregorianCalendar( 2016, Calendar.AUGUST, 22, 21, 35, 35 );
        Source source = new Source();
        source.setTime( time.getTime() );
        source.setSqlDate( sqlDate.getTime() );
        source.setTimestamp( timestamp.getTime() );


        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        Time expectedTime = new Time( time.getTime().getTime() );
        java.sql.Date expectedSqlDate = new java.sql.Date( sqlDate.getTime().getTime() );
        Timestamp expectedTimestamp = new Timestamp( timestamp.getTime().getTime() );

        assertThat( target.getTime() ).isEqualTo( expectedTime );
        assertThat( target.getSqlDate() ).isEqualTo( expectedSqlDate );
        assertThat( target.getTimestamp() ).isEqualTo( expectedTimestamp );
    }

    @IssueKey("858")
    @ProcessorTest
    public void shouldApplySqlToDateConversion() {
        Target target = new Target();
        GregorianCalendar time = new GregorianCalendar( 2016, Calendar.AUGUST, 24, 20, 30, 30 );
        GregorianCalendar sqlDate = new GregorianCalendar( 2016, Calendar.AUGUST, 23, 21, 35, 35 );
        GregorianCalendar timestamp = new GregorianCalendar( 2016, Calendar.AUGUST, 22, 21, 35, 35 );
        target.setTime( new Time( time.getTime().getTime() ) );
        target.setSqlDate( new java.sql.Date( sqlDate.getTime().getTime() ) );
        target.setTimestamp( new Timestamp( timestamp.getTime().getTime() ) );


        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getTime() ).isEqualTo( target.getTime() );
        assertThat( source.getSqlDate() ).isEqualTo( target.getSqlDate() );
        assertThat( source.getTimestamp() ).isEqualTo( target.getTimestamp() );
    }
}
