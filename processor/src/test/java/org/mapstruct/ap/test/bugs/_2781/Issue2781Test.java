/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2781;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Mengxing Yuan
 */
@IssueKey("2781")
@WithClasses({
    Issue2781Mapper.class
})
class Issue2781Test {

    @ProcessorTest
    void shouldCompileWithoutErrors() {
    }
}
