/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2251;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2251")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Issue2251Mapper.class,
    Source.class,
    Target.class,
})
public class Issue2251Test {

    @Test
    public void shouldGenerateCorrectCode() {

        Target target = Issue2251Mapper.INSTANCE.map( new Source( "source" ), "test" );

        assertThat( target ).isNotNull();
        assertThat( target.getValue1() ).isEqualTo( "source" );
        assertThat( target.getValue2() ).isEqualTo( "test" );
    }
}
