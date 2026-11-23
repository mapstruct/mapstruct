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
 * Same scenario as {@link ContextLostNullSafe2StepTest}, but the {@code @Context} parameter of the user method
 * is declared at the first position instead of the last one.
 * </p>
 * <p>
 * The generated helper must declare the context parameter (so that its call of {@code toInt( log, a )}
 * resolves) and the context value must reach the user method, no matter where the context parameter is
 * declared in the user method.
 * </p>
 */
@WithJSpecify
@WithClasses( {
    ContextLostInput.class,
    ContextLostSourceBean.class,
    ContextLostTargetBean.class,
    ContextFirstPositionMapper.class
} )
class ContextFirstPositionNullSafe2StepTest {

    @ProcessorTest
    void nullSafe2StepContextFirstPosition() {
        ContextFirstPositionMapper mapper = ContextFirstPositionMapper.INSTANCE;
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
