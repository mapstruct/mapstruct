/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2001;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2001")
@RunWith( AnnotationProcessorTestRunner.class )
@WithClasses( {
    Entity.class,
    EntityExtra.class,
    Form.class,
    FormExtra.class,
    Issue2001Mapper.class
} )
public class Issue2001Test {

    @Test
    public void shouldCompile() {

    }
}
