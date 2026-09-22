/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.methodtomethod;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithJSpecify
@WithClasses( {
    MethodToMethodSourceBean.class,
    MethodToMethodTargetBean.class,
    MyWrapper.class,
    MyTargetValue.class,
    MethodToMethodMapper.class
} )
class MethodToMethodNullSafe2StepTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void nullSafe2StepMethodToMethod() {
        MethodToMethodMapper mapper = MethodToMethodMapper.INSTANCE;

        MethodToMethodSourceBean nullSource = new MethodToMethodSourceBean();
        nullSource.setName( null );

        MethodToMethodTargetBean nullTarget = mapper.map( nullSource );
        assertThat( nullTarget.getValue() ).isNull();

        MethodToMethodSourceBean filledSource = new MethodToMethodSourceBean();
        filledSource.setName( "hello" );

        MethodToMethodTargetBean filledTarget = mapper.map( filledSource );
        assertThat( filledTarget.getValue() ).isNotNull();
        assertThat( filledTarget.getValue().getValue() ).isEqualTo( "hello" );
        assertThat( filledTarget.getValue().getFactor() ).isEqualTo( 42 );
    }

    @ProcessorTest
    void generatedFixtureMethodToMethod() {
        generatedSource.addComparisonToFixtureFor( MethodToMethodMapper.class );
    }
}
