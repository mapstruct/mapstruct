/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3668;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3668")
@WithClasses({
    Child.class,
    ChildDto.class,
    ChildMapper.class,
    Parent.class,
    ParentDto.class,
    ParentMapper.class,
})
class Issue3668Test {

    @ProcessorTest
    void shouldCompile() {

    }
}
