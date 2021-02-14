/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1395;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses( {
    Issue1395Mapper.class,
    NotUsedService.class,
    Source.class,
    Target.class
} )
@IssueKey( "1395" )
public class Issue1395Test {

    @ProcessorTest
    public void shouldGenerateValidCode() {

    }
}
