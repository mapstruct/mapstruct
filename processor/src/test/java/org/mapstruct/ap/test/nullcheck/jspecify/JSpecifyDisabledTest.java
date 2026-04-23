/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that {@code -Amapstruct.disableJSpecify=true} fully opts out of JSpecify-based
 * null-check inference. With the flag on, mappers that would normally be affected by
 * JSpecify fall back to the pre-JSpecify baseline.
 *
 * @author Filip Hrisafov
 */
@IssueKey("1243")
@WithJSpecify
@ProcessorOption(name = "mapstruct.disableJSpecify", value = "true")
class JSpecifyDisabledTest {

    @ProcessorTest
    @WithClasses({ SourceBean.class, TargetBean.class, JSpecifyDisabledMapper.class })
    public void disabledFlagSkipsTargetNonNullInference() {
        // With JSpecify enabled, mapping a @Nullable source onto a @NonNull target setter
        // emits an "if (src != null)" guard. When disabled, the default NullValueCheckStrategy
        // (ON_IMPLICIT_CONVERSION) applies and a direct String->String assignment gets no guard,
        // so the setter is always invoked — even when the source is null.
        SourceBean source = new SourceBean();
        // nullableValue left null

        TargetBean target = JSpecifyDisabledMapper.INSTANCE.map( source );

        assertThat( target.isNonNullTargetFromNullableSet() ).isTrue();
        assertThat( target.getNonNullTargetFromNullable() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        SourceBean.class,
        ConstructorTargetBean.class,
        ErroneousJSpecifyConstructorMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED)
    public void disabledFlagSkipsConstructorNonNullHardError() {
        // The JSpecify-driven hard error for a @Nullable source mapped to a @NonNull constructor
        // parameter is purely a JSpecify signal; with the flag disabled it must not be raised.
    }
}
