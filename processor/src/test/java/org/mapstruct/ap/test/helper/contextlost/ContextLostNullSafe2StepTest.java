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
 * <p>
 * Reproduces the {@link org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod} bug where
 * {@code @Context} parameters of the user method are lost in the generated helper (in contrast
 * to {@link LocalVarClashNullSafe2StepTest} and {@link ConversionClashNullSafe2StepTest}
 * which cover the local variable clash of the same helper).
 * </p>
 * <p>
 * The helper's body calls {@code toInt( a, log )} using {@code log}, the {@code @Context}
 * parameter of the user method, but the helper itself neither declares nor receives that
 * parameter. The generated code therefore does not compile under either javac or eclipse:
 * <i>cannot find symbol variable log</i>.
 * </p>
 */
@WithJSpecify
@WithClasses( {
    ContextLostInput.class,
    ContextLostSourceBean.class,
    ContextLostTargetBean.class,
    ContextLostMapper.class
} )
class ContextLostNullSafe2StepTest {

    @ProcessorTest
    void nullSafe2StepContextLost() {
        ContextLostMapper mapper = ContextLostMapper.INSTANCE;
        StringBuilder log = new StringBuilder();

        // null input -> null target
        ContextLostSourceBean nullSource = new ContextLostSourceBean();
        ContextLostTargetBean nullTarget = mapper.map( nullSource, log );
        assertThat( nullTarget.getNumber() ).isNull();

        // filled input -> the fixed number, and the context parameter must reach the user method
        log.setLength( 0 );
        ContextLostSourceBean filledSource = new ContextLostSourceBean();
        filledSource.setInput( new ContextLostInput() );
        ContextLostTargetBean filledTarget = mapper.map( filledSource, log );
        assertThat( filledTarget.getNumber() ).isEqualTo( "42" );
        assertThat( log ).hasToString( "t" );
    }
}
