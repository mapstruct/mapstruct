/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtintomethod;

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
    BuiltInMethodSourceBean.class,
    BuiltInMethodTargetBean.class,
    BuiltInMethodMapper.class
} )
class BuiltInMethodNullSafe2StepTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void nullSafe2StepBuiltInToMethod() {
        BuiltInMethodMapper mapper = BuiltInMethodMapper.INSTANCE;

        BuiltInMethodSourceBean nullSource = new BuiltInMethodSourceBean();
        nullSource.setElem( null );

        BuiltInMethodTargetBean nullTarget = mapper.map( nullSource );
        assertThat( nullTarget ).isNotNull();
        assertThat( nullTarget.getLength() ).isZero();

        BuiltInMethodSourceBean filledSource = new BuiltInMethodSourceBean();
        filledSource.setElem( BuiltInMethodSourceBean.build( "hello" ) );

        BuiltInMethodTargetBean filledTarget = mapper.map( filledSource );
        assertThat( filledTarget ).isNotNull();
        assertThat( filledTarget.getLength() ).isEqualTo( 5 );
    }

    @ProcessorTest
    void generatedFixtureBuiltInToMethod() {
        generatedSource.addComparisonToFixtureFor( BuiltInMethodMapper.class );
    }
}
