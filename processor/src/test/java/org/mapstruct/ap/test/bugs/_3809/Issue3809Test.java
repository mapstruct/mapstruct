/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3809;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey("3809")
@WithClasses({ClassMapper.class, ClassA.class, ClassB.class, NestedClassA.class, NestedClassB.class})
public class Issue3809Test {

    @ProcessorTest
    public void shouldCompileNoError() {

    }
}
