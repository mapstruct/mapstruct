/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.contextlost;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The null-safe two-step helper has to work when the context parameter of the mapping method has a different name
 * than the context parameter of the method used in the first step.
 */
@WithJSpecify
@WithClasses( {
    ContextLostInput.class,
    ContextLostSourceBean.class,
    ContextLostTargetBean.class
} )
class ContextNameMismatchNullSafe2StepTest {

    @ProcessorTest
    @WithClasses( ContextNameMismatchMapper.class )
    void nullSafe2StepWithDifferentContextParameterName() {
        StringBuilder log = new StringBuilder();
        ContextLostSourceBean source = new ContextLostSourceBean();
        source.setInput( new ContextLostInput() );

        ContextLostTargetBean target = ContextNameMismatchMapper.INSTANCE.map( source, log );

        assertThat( target.getNumber() ).isEqualTo( "42" );
        assertThat( log ).hasToString( "t" );

        assertThat( ContextNameMismatchMapper.INSTANCE.map( new ContextLostSourceBean(), log ).getNumber() ).isNull();

        log.setLength( 0 );
        target = ContextNameMismatchMapper.INSTANCE.mapOther( source, log );

        assertThat( target.getNumber() ).isEqualTo( "42" );
        assertThat( log ).hasToString( "t" );
    }

    @ProcessorTest
    @WithClasses( ContextNamedAsSourceParameterMapper.class )
    void nullSafe2StepWithContextParameterNamedAsSourceParameter() {
        StringBuilder log = new StringBuilder();
        ContextLostSourceBean source = new ContextLostSourceBean();
        source.setInput( new ContextLostInput() );

        ContextLostTargetBean target = ContextNamedAsSourceParameterMapper.INSTANCE.map( source, log );

        assertThat( target.getNumber() ).isEqualTo( "42" );
        assertThat( log ).hasToString( "t" );
    }
}
