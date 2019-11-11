/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1788;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@IssueKey( "1788" )
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses(
    Issue1788Mapper.class
)
public class Issue1788Test {

    @Test
    public void shouldCompile() {
    }
}
