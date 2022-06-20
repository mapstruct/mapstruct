/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2251;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2251")
@WithClasses({
    Issue2251Mapper.class,
    Source.class,
    Target.class,
})
public class Issue2251Test {

    @ProcessorTest
    public void shouldGenerateCorrectCode() {

        Target target = Issue2251Mapper.INSTANCE.map( new Source( "source" ), "test" );

        assertThat( target ).isNotNull();
        assertThat( target.getValue1() ).isEqualTo( "source" );
        assertThat( target.getValue2() ).isEqualTo( "test" );
    }
}
