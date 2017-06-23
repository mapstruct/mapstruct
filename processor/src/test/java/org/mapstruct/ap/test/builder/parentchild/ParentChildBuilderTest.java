package org.mapstruct.ap.test.builder.parentchild;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.builder.BuilderTests;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

@WithClasses({MutableParent.class, MutableChild.class, ImmutableChild.class, ImmutableParent.class})
@RunWith(AnnotationProcessorTestRunner.class)
@Category(BuilderTests.class)
public class ParentChildBuilderTest {

    @Test
    @WithClasses({ParentChildMapper.class})
    public void testParentChildBuilder_HappyPath() {
        final ParentChildMapper mapper = Mappers.getMapper( ParentChildMapper.class );
        final MutableParent parent = new MutableParent();
        parent.setCount( 4 );
        parent.setChildren( new ArrayList<MutableChild>() );
        parent.getChildren().add( new MutableChild( "Phineas" ) );
        parent.getChildren().add( new MutableChild( "Ferb" ) );

        // transform
        final ImmutableParent immutableParent = mapper.toParent( parent );

        assertThat( immutableParent.getCount() ).isEqualTo( 4 );
        assertThat( immutableParent.getChildren() ).hasSize( 2 );
        assertThat( immutableParent.getChildren() )
                .hasSize( 2 )
                .areExactly( 1, hasMatchingName( "Phineas" ) )
                .areExactly( 1, hasMatchingName( "Ferb" ) );
    }

    private Condition<ImmutableChild> hasMatchingName(final String name) {
        return new Condition<ImmutableChild>() {
            @Override
            public boolean matches(ImmutableChild value) {
                return name.equals( value.getBar() );
            }
        };
    }
}
