/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1720;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1720")
@WithClasses({
    Source.class,
    Target.class,
    SharedConfig.class
})
public class Issue1720Test {

    @Test
    @WithClasses(Issue1720Mapper.class)
    public void testShouldGiveNoErrorMessage() {
        Source source = new Source( 1, "jim" );
        int value = 1;

        Target target = Issue1720Mapper.INSTANCE.map( source, value );

        assertThat( target.getId() ).isEqualTo( 1 );
        assertThat( target.getFullName() ).isEqualTo( "jim" );
        assertThat( target.getValue() ).isEqualTo( 1 );
    }

}
