/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3104;

import java.util.Collections;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3104")
@WithClasses(Issue3104Mapper.class)
class Issue3104Test {

    @ProcessorTest
    void shouldCorrectlyMapUpdateMappingWithTargetImmutableCollectionStrategy() {
        Issue3104Mapper.Target target = new Issue3104Mapper.Target();
        Issue3104Mapper.INSTANCE.update( target, new Issue3104Mapper.Source( null ) );

        assertThat( target.getChildren() ).isEmpty();

        Issue3104Mapper.INSTANCE.update(
            target,
            new Issue3104Mapper.Source( Collections.singletonList( new Issue3104Mapper.ChildSource( "tester" ) ) )
        );
        assertThat( target.getChildren() )
            .extracting( Issue3104Mapper.Child::getMyField )
            .containsExactly( "tester" );
    }
}
