/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2901;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Ben Zegveld
 */
@IssueKey( "2901" )
class Issue2901Test {

    @ProcessorTest
    @WithClasses( { Source.class, Target.class, ConditionWithTargetTypeOnCollectionMapper.class } )
    void shouldCompile() {
    }
}
