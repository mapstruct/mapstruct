/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1650;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@IssueKey("1650")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    AMapper.class,
    A.class,
    B.class,
    C.class,
    APrime.class,
    CPrime.class
})
public class Issue1650Test {

    @Test
    public void shouldCompile() {

    }
}
