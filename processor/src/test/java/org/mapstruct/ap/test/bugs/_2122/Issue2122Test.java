/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2122;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christian Bandowski
 */
@IssueKey("2122")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Issue2122Method2MethodMapper.class
})
public class Issue2122Test {

    @Test
    public void shouldCompile() {
        Issue2122Method2MethodMapper.Source source = new Issue2122Method2MethodMapper.Source();
        source.setValue( "value" );

        Issue2122Method2MethodMapper.Target target = Issue2122Method2MethodMapper.INSTANCE.toTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getEmbeddedTarget() ).isNotNull();
        assertThat( target.getEmbeddedTarget() ).hasSize( 1 )
            .element( 0 )
            .extracting( Issue2122Method2MethodMapper.EmbeddedTarget::getValue ).isEqualTo( "value" );
        assertThat( target.getEmbeddedMapTarget() ).isNotNull();
        assertThat( target.getEmbeddedMapTarget() ).hasSize( 1 );
        assertThat( target.getEmbeddedMapTarget().get( "test" ) )
            .extracting( Issue2122Method2MethodMapper.EmbeddedTarget::getValue ).isEqualTo( "value" );
        assertThat( target.getEmbeddedListListTarget() ).isNotNull();
        assertThat( target.getEmbeddedListListTarget() ).hasSize( 1 );
        assertThat( target.getEmbeddedListListTarget().get( 0 ) ).hasSize( 1 )
            .element( 0 )
            .extracting( Issue2122Method2MethodMapper.EmbeddedTarget::getValue ).isEqualTo( "value" );
    }
}
