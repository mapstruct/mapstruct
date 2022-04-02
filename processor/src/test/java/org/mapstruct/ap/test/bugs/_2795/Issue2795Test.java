/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2795;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey("2795")
@WithClasses({
    Issue2795Mapper.class,
    Nested.class,
    NestedDto.class,
    Target.class,
    Source.class,
})
public class Issue2795Test {

    @ProcessorTest
    void shouldCompile() {
    }
}
