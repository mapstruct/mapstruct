/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1899;

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
@IssueKey("1899")
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue1899Test {

    @Test
    @WithClasses({
        Issue1899Mapper.class,
        SourceWithTwoLists.class,
        TargetWithTwoLists.class
    })
    public void shouldNotUseWrongAdder() {
        SourceWithTwoLists sourceWithListWithTwoLists = new SourceWithTwoLists();
        sourceWithListWithTwoLists.setElements( Collections.singletonList( "ElementList" ) );
        sourceWithListWithTwoLists.setSecondElements( Collections.singletonList( "SecondElementList" ) );

        TargetWithTwoLists targetWithTwoLists = Issue1899Mapper.STM.map( sourceWithListWithTwoLists );

        assertThat( targetWithTwoLists ).isNotNull();
        assertThat( targetWithTwoLists.getSecondElements() )
            .containsExactlyInAnyOrder( "SecondElementList" );

        assertThat( targetWithTwoLists.getElements() )
            .containsExactlyInAnyOrder( "ElementList" );
    }
}
