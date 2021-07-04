/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1901;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ruslan Mikhalev
 */
@IssueKey("1901")
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue1901Test {

    @Test
    @WithClasses({
        Issue1901Mapper.class,
        SourceWithTwoLists.class,
        TargetWithTwoListsAndAdders.class
    })
    public void shouldFindAdder() {

        SourceWithTwoLists sourceWithTwoLists = new SourceWithTwoLists();
        sourceWithTwoLists.setElementList( Collections.singletonList( "ElementList" ) );
        sourceWithTwoLists.setSecondElementList( Collections.singletonList( "SecondElementList" ) );

        TargetWithTwoListsAndAdders targetWithTwoListsAndAdders = Issue1901Mapper.STM.map( sourceWithTwoLists );

        assertThat( targetWithTwoListsAndAdders ).isNotNull();
        assertThat( targetWithTwoListsAndAdders.getElementList() )
            .containsExactlyInAnyOrder( "ElementList" );

        assertThat( targetWithTwoListsAndAdders ).isNotNull();
        assertThat( targetWithTwoListsAndAdders.getSecondElementList() )
            .containsExactlyInAnyOrder( "SecondElementList" );
    }
}
