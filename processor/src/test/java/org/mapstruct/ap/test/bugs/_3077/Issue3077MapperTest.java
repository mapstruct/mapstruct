/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3077;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses(Issue3077Mapper.class)
@IssueKey("3057")
class Issue3077MapperTest {

    @ProcessorTest
    void mapsSelf() {
        Issue3077Mapper.Source sourceInner = new Issue3077Mapper.Source( "inner", null );
        Issue3077Mapper.Source sourceOuter = new Issue3077Mapper.Source( "outer", sourceInner );

        Issue3077Mapper.Target targetOuter = Issue3077Mapper.INSTANCE.map( sourceOuter );

        assertThat( targetOuter.getValue() ).isEqualTo( "outer" );
        assertThat( targetOuter.getSelf().getValue() ).isEqualTo( "inner" );
    }
}
