/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1375;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses( {
    Target.class,
    Source.class,
    Issue1375Mapper.class
} )
@RunWith( AnnotationProcessorTestRunner.class )
@IssueKey( "1375" )
public class Issue1375Test {

    @Test
    public void shouldGenerateCorrectMapperWhenIntermediaryReadAccessorIsMissing() {

        Target target = Issue1375Mapper.INSTANCE.map( new Source( "test value" ) );

        assertThat( target ).isNotNull();
        assertThat( target.nested ).isNotNull();
        assertThat( target.nested.getValue() ).isEqualTo( "test value" );
    }
}
