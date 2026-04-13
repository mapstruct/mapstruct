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
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedNote;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.runner.Compiler;

/**
 * Verifies that JSpecify-driven null-check decisions emit verbose diagnostic notes
 * (visible with {@code -Amapstruct.verbose=true}) so they are no longer silent.
 *
 * @author Filip Hrisafov
 */
@IssueKey("1243")
@WithJSpecify
class JSpecifyVerboseNoteTest {

    @ProcessorTest(Compiler.JDK)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses({ SourceBean.class, TargetBean.class, JSpecifyNullCheckMapper.class })
    @ExpectedNote("^-- MapStruct: JSpecify skipping null check for property \"nonNullTarget\": "
        + "source is @NonNull\\.$")
    @ExpectedNote("^-- MapStruct: JSpecify adding null check for property \"nonNullTargetFromNullable\": "
        + "source=NULLABLE, target=NON_NULL\\.$")
    public void emitsSetterDecisionNotes() {
    }

    @ProcessorTest(Compiler.JDK)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses({ SourceBean.class, TargetBean.class, JSpecifyNonNullParamMapper.class })
    @ExpectedNote("^-- MapStruct: JSpecify skipping method-level null guard for property \"source\": "
        + "parameter is @NonNull\\.$")
    public void emitsMethodLevelGuardSkipNote() {
    }
}
