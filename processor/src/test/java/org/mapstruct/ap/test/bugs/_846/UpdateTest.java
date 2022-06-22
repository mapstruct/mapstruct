/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._846;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@IssueKey("846")
@WithClasses({
    Mapper846.class
})
public class UpdateTest {

    @ProcessorTest
    public void shouldProduceMapper() {

    }

}
