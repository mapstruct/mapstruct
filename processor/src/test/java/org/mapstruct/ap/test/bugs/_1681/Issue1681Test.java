/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1681;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1681")
@WithClasses({
    Issue1681Mapper.class,
    Source.class,
    Target.class
})
public class Issue1681Test {

    @Test
    public void shouldCompile() {
        Target target = new Target( "before" );
        Source source = new Source();
        source.setValue( "after" );

        Target updatedTarget = Issue1681Mapper.INSTANCE.update( target, source );


        assertThat( updatedTarget ).isSameAs( target );
        assertThat( updatedTarget.getValue() ).isEqualTo( "after" );
    }
}
