/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1719;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1719")
@WithClasses({
    Source.class,
    SourceElement.class,
    Target.class,
    TargetElement.class
})
public class Issue1719Test {

    /**
     * For adder methods MapStuct cannot generate an update method. MapStruct would cannot know how to remove objects
     * from the child-parent relation. It cannot even assume that the the collection can be cleared at forehand.
     * Therefore the only sensible choice is for MapStruct to create a create method for the target elements.
     */
    @Test
    @WithClasses(Issue1719Mapper.class)
    public void testShouldGiveNoErrorMessage() {
        Source source = new Source();
        source.getSourceElements().add( new SourceElement( 1, "jim" ) );
        source.getSourceElements().add( new SourceElement( 2, "alice" ) );

        Target target = new Target();
        TargetElement bob = new TargetElement( 1, "bob" );
        target.addTargetElement( bob );
        TargetElement louise = new TargetElement( 3, "louise" );
        target.addTargetElement( louise );

        Issue1719Mapper.INSTANCE.map( source, target );

        assertThat( target.getTargetElements() ).hasSize( 3 );
        assertThat( target.getTargetElements() )
            .extracting( TargetElement::getId, TargetElement::getName )
            .containsOnly(
                tuple( 1, "bob" ),
                tuple( 2, "alice" ),
                tuple( 3, "louise" )
            );
    }

}
