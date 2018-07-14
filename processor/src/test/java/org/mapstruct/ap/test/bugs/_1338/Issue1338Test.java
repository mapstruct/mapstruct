/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1338;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1338")
@WithClasses({
    Issue1338Mapper.class,
    Source.class,
    Target.class
})
public class Issue1338Test {

    @Test
    public void shouldCorrectlyUseAdder() {
        Source source = new Source();
        source.setProperties( Arrays.asList( "first", "second" ) );
        Target target = Issue1338Mapper.INSTANCE.map( source );

        assertThat( target )
            .extracting( "properties" )
            .contains( Arrays.asList( "first", "second" ), atIndex( 0 ) );

        Source mapped = Issue1338Mapper.INSTANCE.map( target );

        assertThat( mapped.getProperties() )
            .containsExactly( "first", "second" );
    }
}
