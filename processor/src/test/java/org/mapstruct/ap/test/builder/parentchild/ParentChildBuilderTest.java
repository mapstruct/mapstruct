/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.parentchild;

import java.util.ArrayList;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    MutableParent.class,
    MutableChild.class,
    ImmutableChild.class,
    ImmutableParent.class,
    ParentChildMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class ParentChildBuilderTest {

    @Test
    public void testParentChildBuilderMapper() {
        final MutableParent parent = new MutableParent();
        parent.setCount( 4 );
        parent.setChildren( new ArrayList<MutableChild>() );
        parent.getChildren().add( new MutableChild( "Phineas" ) );
        parent.getChildren().add( new MutableChild( "Ferb" ) );

        // transform
        ImmutableParent immutableParent = ParentChildMapper.INSTANCE.toParent( parent );

        assertThat( immutableParent.getCount() ).isEqualTo( 4 );
        assertThat( immutableParent.getChildren() ).hasSize( 2 );
        assertThat( immutableParent.getChildren() )
            .hasSize( 2 )
            .areExactly( 1, hasMatchingName( "Phineas" ) )
            .areExactly( 1, hasMatchingName( "Ferb" ) );
    }

    private Condition<ImmutableChild> hasMatchingName(final String name) {
        return new Condition<ImmutableChild>( "Matching name" ) {
            @Override
            public boolean matches(ImmutableChild value) {
                return name.equals( value.getName() );
            }
        };
    }
}
