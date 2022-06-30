/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1552;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Issue1552Mapper.class,
    Target.class
})
@IssueKey("1552")
public class Issue1552Test {

    @ProcessorTest
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
