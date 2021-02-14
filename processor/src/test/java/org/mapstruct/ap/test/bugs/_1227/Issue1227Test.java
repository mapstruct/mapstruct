/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1227;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1227")
@WithClasses({
    Issue1227Mapper.class,
    ThreadDto.class
})
public class Issue1227Test {

    @ProcessorTest
    public void shouldCompile() {
    }
}
