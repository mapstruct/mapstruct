/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1359;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.ITERABLE;

/**
 * @author Filip Hrisafov
 */
@WithClasses( {
    Issue1359Mapper.class,
    Source.class,
    Target.class
} )
@RunWith( AnnotationProcessorTestRunner.class )
@IssueKey( "1359" )
public class Issue1359Test {

    @Test
    public void shouldCompile() {

        Target target = new Target();
        assertThat( target ).extracting( "properties" ).isNull();

        Set<String> properties = new HashSet<>();
        properties.add( "first" );
        Source source = new Source( properties );
        Issue1359Mapper.INSTANCE.map( target, source );

        assertThat( target ).extracting( "properties", ITERABLE ).containsExactly( "first" );

    }
}
