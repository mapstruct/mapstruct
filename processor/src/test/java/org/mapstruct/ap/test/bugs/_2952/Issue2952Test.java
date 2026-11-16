/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2952;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2952")
@WithClasses({
    Issue2952Mapper.class
})
class Issue2952Test {

    @ProcessorTest
    void shouldCorrectIgnoreImmutableIterable() {
        Issue2952Mapper.Target target = Issue2952Mapper.INSTANCE.map( new Issue2952Mapper.Source( "test" ) );

        assertThat( target.getValue() ).isEqualTo( "test" );
    }
}
