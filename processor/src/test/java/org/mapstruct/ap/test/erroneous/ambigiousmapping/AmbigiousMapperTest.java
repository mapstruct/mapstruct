/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambigiousmapping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@IssueKey("2156")
@RunWith(AnnotationProcessorTestRunner.class)
public class AmbigiousMapperTest {

    @Test
    @WithClasses(ErroneousMapper.class)
    public void testNestedAmbigious() {
    }
}
