/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1164;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses( {
    Source.class,
    Target.class,
    GenericHolder.class,
    SourceTargetMapper.class
} )
@IssueKey( "1164" )
public class Issue1164Test {

    @ProcessorTest
    public void shouldCompile() {
    }
}
