/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2133;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@IssueKey("2133")
@WithClasses( Issue2133Mapper.class )
public class Issue2133Test {

    @ProcessorTest
    public void shouldCompile() {
    }

}
