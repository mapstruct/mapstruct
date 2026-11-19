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
 * Reproduces the update-method regression of the two-step
 * {@link org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod} path for a primitive target.
 * <p>
 * When the source property is {@code null} the generated helper already decides on the primitive
 * default ({@code 0L}) on its own. In an update method that default must not clobber a value the
 * target already holds: the existing setter null-handling (null check /
 * {@code NullValuePropertyMappingStrategy}) is expected to decide, consistent with a direct
 * {@code String -> long} mapping. This test asserts that the pre-existing value is preserved, which is
 * the behaviour on {@code main}. It is expected to fail on the PR branch, which overwrites the value
 * with {@code 0}.
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
