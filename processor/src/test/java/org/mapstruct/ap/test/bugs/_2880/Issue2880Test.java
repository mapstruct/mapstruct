/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2880;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey("2880")
@WithClasses({
    Issue2880Mapper.class,
    Outer.class,
    Source.class,
    Target.class,
    TargetData.class
})
public class Issue2880Test {

    @ProcessorTest
    void shouldCompile() {
    }
}
