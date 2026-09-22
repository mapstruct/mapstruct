/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;

import static org.assertj.core.api.Assertions.assertThat;

@WithJSpecify
@WithClasses({
    JSpecifyNullSafe2StepSourceBean.class,
    JSpecifyNullSafe2StepPrimitiveTargetBean.class
})
class JSpecifyNullSafe2StepPrimitiveTest {

    @ProcessorTest
    @WithClasses(JSpecifyNullSafe2StepPrimitiveMapper.class)
    void nullSafe2StepWithNullableIntermediateAndPrimitiveTarget() {
        // Nullable intermediate (Integer) -> built-in conversion to primitive (long).
        // When the intermediate is null the generated helper must default to 0L,
        // never the illegal `return null;`.
        JSpecifyNullSafe2StepSourceBean source = new JSpecifyNullSafe2StepSourceBean();
        source.setName( null );

        JSpecifyNullSafe2StepPrimitiveTargetBean target =
            JSpecifyNullSafe2StepPrimitiveMapper.INSTANCE.map( source );

        assertThat( target.getNumber() ).isZero();

        source.setName( "42" );
        target = JSpecifyNullSafe2StepPrimitiveMapper.INSTANCE.map( source );

        assertThat( target.getNumber() ).isEqualTo( 42L );
    }
}
