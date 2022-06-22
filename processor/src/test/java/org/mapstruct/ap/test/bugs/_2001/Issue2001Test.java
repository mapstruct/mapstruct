/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2001;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2001")
@WithClasses( {
    Entity.class,
    EntityExtra.class,
    Form.class,
    FormExtra.class,
    Issue2001Mapper.class
} )
public class Issue2001Test {

    @ProcessorTest
    public void shouldCompile() {

    }
}
