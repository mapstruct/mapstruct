/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.adder;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The built-in methods do not accept {@code null}. The elements which are passed to an adder have to be null checked
 * before a built-in method is invoked for them.
 */
@WithClasses( AdderBuiltInMapper.class )
class AdderBuiltInTest {

    @ProcessorTest
    void shouldNullCheckElementsBeforeInvokingBuiltInMethod() {
        Calendar calendar = new GregorianCalendar( TimeZone.getTimeZone( "UTC" ) );
        AdderBuiltInMapper.Source source = new AdderBuiltInMapper.Source();
        source.setDates( Arrays.asList( calendar, null ) );

        AdderBuiltInMapper.Target target = AdderBuiltInMapper.INSTANCE.map( source );

        assertThat( target.getDates() ).hasSize( 2 );
        assertThat( target.getDates().get( 0 ) ).isNotNull();
        assertThat( target.getDates().get( 1 ) ).isNull();
    }
}
