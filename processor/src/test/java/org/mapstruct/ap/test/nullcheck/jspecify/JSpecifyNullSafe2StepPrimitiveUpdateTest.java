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

/**
 * Update method behaviour of the two-step {@link org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod}
 * path for a primitive target.
 * <p>
 * The generated helper returns the boxed type ({@code Long}) and does not pick a primitive default on its own.
 * The existing setter null handling (null check / {@code NullValuePropertyMappingStrategy}) decides what
 * happens with a {@code null} result, consistent with a direct {@code String -> long} mapping. In an update
 * method a {@code null} source therefore keeps the value that the target already holds.
 */
@WithJSpecify
@WithClasses({
    JSpecifyNullSafe2StepSourceBean.class,
    JSpecifyNullSafe2StepPrimitiveTargetBean.class
})
class JSpecifyNullSafe2StepPrimitiveUpdateTest {

    @ProcessorTest
    @WithClasses(JSpecifyNullSafe2StepPrimitiveUpdateMapper.class)
    void updateMethodShouldKeepExistingValueWhenSourceIsNull() {
        JSpecifyNullSafe2StepSourceBean source = new JSpecifyNullSafe2StepSourceBean();
        source.setName( null );

        JSpecifyNullSafe2StepPrimitiveTargetBean target = new JSpecifyNullSafe2StepPrimitiveTargetBean();
        target.setNumber( 99L );

        JSpecifyNullSafe2StepPrimitiveUpdateMapper.INSTANCE.update( source, target );

        // A null source must not clobber a value the target already holds.
        assertThat( target.getNumber() ).isEqualTo( 99L );
    }

    @ProcessorTest
    @WithClasses(JSpecifyNullSafe2StepPrimitiveUpdateMapper.class)
    void updateMethodShouldOverwriteWhenSourceIsPresent() {
        JSpecifyNullSafe2StepSourceBean source = new JSpecifyNullSafe2StepSourceBean();
        source.setName( "42" );

        JSpecifyNullSafe2StepPrimitiveTargetBean target = new JSpecifyNullSafe2StepPrimitiveTargetBean();
        target.setNumber( 99L );

        JSpecifyNullSafe2StepPrimitiveUpdateMapper.INSTANCE.update( source, target );

        assertThat( target.getNumber() ).isEqualTo( 42L );
    }
}
