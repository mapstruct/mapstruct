/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.methodtobuiltIn;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithJSpecify
@WithClasses( {
    MethodBuiltInInput.class,
    MethodBuiltInSourceBean.class,
    MethodBuiltInTargetBean.class,
    MethodBuiltInMapper.class
} )
class MethodBuiltInNullSafe2StepTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void nullSafe2StepMethodToBuiltIn() {
        MethodBuiltInMapper mapper = MethodBuiltInMapper.INSTANCE;

        MethodBuiltInSourceBean nullSource = new MethodBuiltInSourceBean();
        nullSource.setInput( null );

        MethodBuiltInTargetBean nullTarget = mapper.map( nullSource );
        assertThat( nullTarget.getDateTime() ).isNull();

        MethodBuiltInSourceBean filledSource = new MethodBuiltInSourceBean();
        filledSource.setInput( new MethodBuiltInInput( "hello" ) );

        MethodBuiltInTargetBean filledTarget = mapper.map( filledSource );
        assertThat( filledTarget.getDateTime() ).isNotNull();
        assertThat( filledTarget.getDateTime() ).isEqualTo(
            new GregorianCalendar( 2020, Calendar.JANUARY, 15 ).toZonedDateTime()
        );
        assertThat( filledTarget.getDateTime().getZone() ).isEqualTo( ZoneId.systemDefault() );
    }

    @ProcessorTest
    void generatedFixtureMethodToBuiltIn() {
        generatedSource.addComparisonToFixtureFor( MethodBuiltInMapper.class );
    }
}
