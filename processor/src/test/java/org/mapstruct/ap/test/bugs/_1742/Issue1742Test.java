/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1742;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1742")
@WithClasses( {
    Issue1742Mapper.class,
    NestedSource.class,
    NestedTarget.class,
    Source.class,
    Target.class,
} )
public class Issue1742Test {

    @ProcessorTest
    public void shouldCompile() {

    }
}
