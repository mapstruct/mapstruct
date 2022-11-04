/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3057;

import org.mapstruct.ap.test.bugs._3057.Issue3057Mapper.Source;
import org.mapstruct.ap.test.bugs._3057.Issue3057Mapper.Target;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ben Zegveld
 */
@WithClasses(Issue3057Mapper.class)
@IssueKey("3057")
class Issue3057MapperTest {

    @ProcessorTest
    void mapsSelf() {
        Source sourceOuter = new Issue3057Mapper.Source();
        Source sourceInner = new Issue3057Mapper.Source();
        sourceOuter.setSelf( sourceInner );

        Target targetOuter = Issue3057Mapper.INSTANCE.map( sourceOuter );

        assertThat( targetOuter.getValue() ).isEqualTo( "constantValue" );
        assertThat( targetOuter.getSelf().getValue() ).isEqualTo( "constantValue" );
    }
}
