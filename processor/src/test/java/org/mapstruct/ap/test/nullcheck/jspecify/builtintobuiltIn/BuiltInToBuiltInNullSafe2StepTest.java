/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtintobuiltIn;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.WithJavaxJaxb;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithJSpecify
@WithJavaxJaxb
@WithClasses( {
    BuiltInToBuiltInSourceBean.class,
    BuiltInToBuiltInTargetBean.class,
    BuiltInToBuiltInMapper.class
} )
class BuiltInToBuiltInNullSafe2StepTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void nullSafe2StepBuiltInToBuiltIn() {
        BuiltInToBuiltInMapper mapper = BuiltInToBuiltInMapper.INSTANCE;

        BuiltInToBuiltInSourceBean nullSource = new BuiltInToBuiltInSourceBean();
        nullSource.setElem( null );

        BuiltInToBuiltInTargetBean nullTarget = mapper.map( nullSource );
        assertThat( nullTarget ).isNotNull();
        assertThat( nullTarget.getDateTime() ).isNull();

        BuiltInToBuiltInSourceBean filledSource = new BuiltInToBuiltInSourceBean();
        filledSource.setElem( BuiltInToBuiltInSourceBean.buildDefault() );

        BuiltInToBuiltInTargetBean filledTarget = mapper.map( filledSource );
        assertThat( filledTarget ).isNotNull();
        assertThat( filledTarget.getDateTime() ).isNotNull();
        assertThat( filledTarget.getDateTime() ).isEqualTo(
            new GregorianCalendar( 2020, Calendar.JANUARY, 15 ).toZonedDateTime()
        );
        assertThat( filledTarget.getDateTime().getZone() ).isEqualTo( ZoneId.systemDefault() );
    }

    @ProcessorTest
    void generatedFixtureBuiltInToBuiltIn() {
        generatedSource.addComparisonToFixtureFor( BuiltInToBuiltInMapper.class );
    }
}
