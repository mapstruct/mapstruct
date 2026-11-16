/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3159;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3159")
@WithClasses(Issue3159Mapper.class)
class Issue3159Test {

    @ProcessorTest
    void shouldUseDefaultExpressionForCollection() {
        Issue3159Mapper.Target target = Issue3159Mapper.INSTANCE.map( new Issue3159Mapper.Source( null ) );

        assertThat( target.getElements() ).isEmpty();
    }
}
