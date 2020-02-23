/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.date;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.Compiler;
import org.mapstruct.ap.testutil.runner.DisabledOnCompiler;
import org.mapstruct.ap.testutil.runner.EnabledOnCompiler;

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

    private Locale originalLocale;

    @Before
    public void setDefaultLocale() {
        originalLocale = Locale.getDefault();
        Locale.setDefault( Locale.GERMAN );
    }

    @After
    public void tearDown() {
        Locale.setDefault( originalLocale );
    }

    @Test
    @DisabledOnCompiler(Compiler.JDK11)
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

    @Test
    @EnabledOnCompiler(Compiler.JDK11)
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

    @Test
    @DisabledOnCompiler(Compiler.JDK11)
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

    @Test
    @EnabledOnCompiler(Compiler.JDK11)
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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
    @Test
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
    @Test
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
