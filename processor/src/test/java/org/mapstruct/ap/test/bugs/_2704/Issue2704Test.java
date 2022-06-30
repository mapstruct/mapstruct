/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2704;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Valentin Kulesh
 */
@IssueKey("2704")
@WithClasses({ TestMapper.class, TopLevel.class })
public class Issue2704Test {
    @ProcessorTest
    public void shouldCompile() {
    }
}
