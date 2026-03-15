/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3949;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests if overloaded targets are correctly cast when set to null
 *
 * @author hduelme
 */
@IssueKey("3949")
@WithClasses({
    ParentSource.class,
    ParentTargetInterface.class,
    ParentTarget.class,
    StringSource.class,
    TargetStringInterface.class,
    TargetString.class,
    DateSource.class,
    TargetDateInterface.class,
    TargetDate.class
})
public class Issue3949Test {

    @ProcessorTest
    @WithClasses({
            Issue3949ClassMapper.class
    })
    void shouldCompileAndSetCorrectlyToNullForClass() {
        TargetDate shouldSetDateToNull = new TargetDate();
        Issue3949ClassMapper.INSTANCE.overwriteDate(shouldSetDateToNull, new DateSource());
        assertThat(shouldSetDateToNull.getString()).isNotNull();
        assertThat(shouldSetDateToNull.getDate()).isNull();

        shouldSetDateToNull = new TargetDate();
        Issue3949ClassMapper.INSTANCE.overwriteDateWithConversion(shouldSetDateToNull, new StringSource());
        assertThat(shouldSetDateToNull.getString()).isNotNull();
        assertThat(shouldSetDateToNull.getDate()).isNull();

        TargetString shouldSetStringToNull = new TargetString();
        Issue3949ClassMapper.INSTANCE.overwriteString(shouldSetStringToNull, new StringSource());
        assertThat(shouldSetStringToNull.getDate()).isNull();
        assertThat(shouldSetStringToNull.getDateValue()).isNotNull();

        shouldSetStringToNull = new TargetString();
        Issue3949ClassMapper.INSTANCE.overwriteStringWithConversion(shouldSetStringToNull,
                new DateSource());
        assertThat(shouldSetStringToNull.getDate()).isNull();
        assertThat(shouldSetStringToNull.getDateValue()).isNotNull();

        ParentTarget parentTarget = new ParentTarget();
        parentTarget.setChild(new ParentTarget());
        Issue3949ClassMapper.INSTANCE.updateParent(parentTarget, new ParentSource());
        assertThat(parentTarget.getChild()).isNull();
    }

    @ProcessorTest
    @WithClasses({
            Issue3949InterfaceMapper.class
    })
    void shouldCompileAndSetCorrectlyToNullForInterface() {
        TargetDateInterface shouldSetDateToNull = new TargetDate();
        Issue3949InterfaceMapper.INSTANCE.overwriteDate(shouldSetDateToNull, new DateSource());
        assertThat(shouldSetDateToNull.getString()).isNotNull();
        assertThat(shouldSetDateToNull.getDate()).isNull();

        shouldSetDateToNull = new TargetDate();
        Issue3949InterfaceMapper.INSTANCE.overwriteDateWithConversion(shouldSetDateToNull, new StringSource());
        assertThat(shouldSetDateToNull.getString()).isNotNull();
        assertThat(shouldSetDateToNull.getDate()).isNull();

        TargetStringInterface shouldSetStringToNull = new TargetString();
        Issue3949InterfaceMapper.INSTANCE.overwriteString(shouldSetStringToNull, new StringSource());
        assertThat(shouldSetStringToNull.getDate()).isNull();
        assertThat(shouldSetStringToNull.getDateValue()).isNotNull();

        shouldSetStringToNull = new TargetString();
        Issue3949InterfaceMapper.INSTANCE.overwriteStringWithConversion(shouldSetStringToNull,
                new DateSource());
        assertThat(shouldSetStringToNull.getDate()).isNull();
        assertThat(shouldSetStringToNull.getDateValue()).isNotNull();

        ParentTargetInterface parentTarget = new ParentTarget();
        parentTarget.setChild(new ParentTarget());
        Issue3949InterfaceMapper.INSTANCE.updateParent(parentTarget, new ParentSource());
        assertThat(parentTarget.getChild()).isNull();
    }
}
