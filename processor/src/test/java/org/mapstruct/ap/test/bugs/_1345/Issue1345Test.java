/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1345;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Sjaak Derksen
 */
@WithClasses({
    Issue1345Mapper.class
})
@IssueKey("1345")
public class Issue1345Test {

    @ProcessorTest
    public void shouldCompile() {
    }
}
