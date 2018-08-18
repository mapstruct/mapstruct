/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1552;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Issue1552Mapper.class,
    Target.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1552")
public class Issue1552Test {

    @Test
    public void shouldCompile() {
        Target target = Issue1552Mapper.INSTANCE.twoArgsWithConstant( "first", "second" );

        assertThat( target ).isNotNull();
        assertThat( target.getFirst() ).isNotNull();
        assertThat( target.getFirst().getValue() ).isEqualTo( "constant" );
        assertThat( target.getSecond() ).isNotNull();
        assertThat( target.getSecond().getValue() ).isEqualTo( "second" );


        target = Issue1552Mapper.INSTANCE.twoArgsWithExpression( "third", "fourth" );

        assertThat( target ).isNotNull();
        assertThat( target.getFirst() ).isNotNull();
        assertThat( target.getFirst().getValue() ).isEqualTo( "expression" );
        assertThat( target.getSecond() ).isNotNull();
        assertThat( target.getSecond().getValue() ).isEqualTo( "fourth" );
    }
}
