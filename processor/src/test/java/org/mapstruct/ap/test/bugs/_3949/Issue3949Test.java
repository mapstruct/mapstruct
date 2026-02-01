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
        Issue3949Mapper.class
})
public class Issue3949Test {

    @ProcessorTest
    void shouldCompileAndSetCorrectlyToNull() {
        Issue3949Mapper.TargetDate shouldSetDateToNull = new Issue3949Mapper.TargetDate();
        Issue3949Mapper.INSTANCE.overwriteDate( shouldSetDateToNull, new Issue3949Mapper.DateSource() );
        assertThat( shouldSetDateToNull.getString() ).isNotNull();
        assertThat( shouldSetDateToNull.getDate() ).isNull();

        Issue3949Mapper.TargetString shouldSetStringToNull = new Issue3949Mapper.TargetString();
        Issue3949Mapper.INSTANCE.overwriteString( shouldSetStringToNull, new Issue3949Mapper.StringSource() );
        assertThat( shouldSetStringToNull.getDate() ).isNull();
        assertThat( shouldSetStringToNull.getDateValue() ).isNotNull();
    }
}
