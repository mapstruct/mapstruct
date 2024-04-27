/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.deepstructure;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey( "3575" )
public class DeepNestedMapperTest {

    @ProcessorTest
    @WithClasses( { DeepNestedMapper.class, Source.class, Target.class, SourceContainer.class, TargetContainer.class,
        TargetCollectionItem.class, SourceCollectionItem.class, SourceInnerChild.class, SourceCollectionContainer.class,
        TargetChild.class, SourceSecondSubChild.class, TargetSubChild.class, SourceChild.class, SourceSubChild.class } )
    void test() {
    }
}
