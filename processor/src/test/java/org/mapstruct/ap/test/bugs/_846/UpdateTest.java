/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._846;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("846")
@WithClasses({
    Mapper846.class
})
public class UpdateTest {

    @Test
    public void shouldProduceMapper() {

    }

}
