/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3905;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3905")
@WithClasses({
    Issue3905Mapper.class,
    Override.class,
    OverrideDto.class
})
class Issue3905Test {

    @ProcessorTest
    void shouldCompile() {
    }
}
