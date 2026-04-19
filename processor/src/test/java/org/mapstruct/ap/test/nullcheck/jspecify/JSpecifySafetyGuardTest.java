/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that the pre-existing safety guards in {@code PropertyMapping} (unboxing,
 * {@code defaultValue}, {@code NullValuePropertyMappingStrategy.SET_TO_DEFAULT}) still
 * generate a null check when the source is {@code @Nullable}.
 *
 * @author Filip Hrisafov
 */
@IssueKey("1243")
@WithClasses({
    SafetyGuardSourceBean.class,
    SafetyGuardTargetBean.class,
    JSpecifySafetyGuardMapper.class
})
@WithJSpecify
class JSpecifySafetyGuardTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void nullableSourceToPrimitiveTargetKeepsUnboxingGuard() {
        // Source @Nullable Integer is null; unboxing guard must prevent an NPE.
        SafetyGuardSourceBean source = new SafetyGuardSourceBean();

        SafetyGuardTargetBean target = JSpecifySafetyGuardMapper.INSTANCE.map( source );

        assertThat( target.getUnboxedNumber() ).isZero();
    }

    @ProcessorTest
    public void nullableSourceWithDefaultValueKeepsDefaultValueGuard() {
        // Source @Nullable String is null; defaultValue must be applied.
        SafetyGuardSourceBean source = new SafetyGuardSourceBean();

        SafetyGuardTargetBean target = JSpecifySafetyGuardMapper.INSTANCE.map( source );

        assertThat( target.getTextWithDefault() ).isEqualTo( "default" );
    }

    @ProcessorTest
    public void nullableSourceWithSetToDefaultKeepsNvpmsGuard() {
        // Source @Nullable String is null; NVPMS=SET_TO_DEFAULT must kick in on an update method
        // (NVPMS is only honored for update methods) and reset the target to the String default.
        SafetyGuardSourceBean source = new SafetyGuardSourceBean();
        SafetyGuardTargetBean target = new SafetyGuardTargetBean();
        target.setTextWithNvpms( "pre-existing" );

        JSpecifySafetyGuardMapper.INSTANCE.update( source, target );

        assertThat( target.getTextWithNvpms() ).isEmpty();
    }
}
