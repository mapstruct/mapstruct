/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3931;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that {@code @DeepClone} creates independent copies of mutable JDK types such as
 * {@link Date} and {@link Calendar}, instead of copying references.
 */
@IssueKey("3931")
@WithClasses({
    BeanA.class,
    Issue3931Mapper.class
})
class Issue3931Test {

    @ProcessorTest
    void shouldCloneDateInsteadOfCopyingReference() {
        Date originalDate = new Date( 1_700_000_000_000L );

        BeanA source = new BeanA();
        source.setDate( originalDate );
        source.setName( "test" );

        BeanA clone = Issue3931Mapper.INSTANCE.clone( source );

        assertThat( clone ).isNotNull();
        assertThat( clone ).isNotSameAs( source );
        assertThat( clone.getDate() ).isNotNull();
        assertThat( clone.getDate() ).isEqualTo( originalDate );
        assertThat( clone.getDate() ).isNotSameAs( originalDate );
        assertThat( clone.getName() ).isEqualTo( "test" );

        // Mutating the source date must not affect the clone
        originalDate.setTime( 0L );
        assertThat( clone.getDate().getTime() ).isEqualTo( 1_700_000_000_000L );
    }

    @ProcessorTest
    void shouldCloneCalendarInsteadOfCopyingReference() {
        Calendar originalCalendar = new GregorianCalendar( 2024, Calendar.JANUARY, 15, 10, 30, 0 );

        BeanA source = new BeanA();
        source.setCalendar( originalCalendar );

        BeanA clone = Issue3931Mapper.INSTANCE.clone( source );

        assertThat( clone ).isNotNull();
        assertThat( clone.getCalendar() ).isNotNull();
        assertThat( clone.getCalendar() ).isEqualTo( originalCalendar );
        assertThat( clone.getCalendar() ).isNotSameAs( originalCalendar );

        // Mutating the source calendar must not affect the clone
        originalCalendar.set( Calendar.YEAR, 1999 );
        assertThat( clone.getCalendar().get( Calendar.YEAR ) ).isEqualTo( 2024 );
    }

    @ProcessorTest
    void shouldMapNullDateAndCalendar() {
        BeanA source = new BeanA();
        source.setDate( null );
        source.setCalendar( null );

        BeanA clone = Issue3931Mapper.INSTANCE.clone( source );

        assertThat( clone ).isNotNull();
        assertThat( clone.getDate() ).isNull();
        assertThat( clone.getCalendar() ).isNull();
    }

    @ProcessorTest
    void shouldReturnNullForNullSource() {
        assertThat( Issue3931Mapper.INSTANCE.clone( null ) ).isNull();
    }
}
