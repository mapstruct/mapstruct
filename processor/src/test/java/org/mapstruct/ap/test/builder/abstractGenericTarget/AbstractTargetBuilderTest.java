package org.mapstruct.ap.test.builder.abstractGenericTarget;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.builder.BuilderTests;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

@WithClasses({AbstractChildTarget.class, AbstractParentTarget.class, ChildSource.class, ImmutableChildTargetImpl.class,
        ImmutableParentTargetImpl.class, MutableChildTargetImpl.class, MutableParentTargetImpl.class, ParentSource.class})
@RunWith(AnnotationProcessorTestRunner.class)
@Category(BuilderTests.class)
public class AbstractTargetBuilderTest {

    @Test
    @WithClasses({AbstractTargetMapper.class})
    public void testAbstractTargetMapper_HappyPath() {
        final AbstractTargetMapper mapper = Mappers.getMapper( AbstractTargetMapper.class );
        final ParentSource parent = new ParentSource();
        parent.setCount( 4 );
        parent.setNested( new ChildSource("Phineas") );

        // transform
        final AbstractParentTarget immutableTarget = mapper.toImmutable( parent );
        final AbstractParentTarget mutableTarget = mapper.toMutable( parent );

        assertThat( mutableTarget.getCount() ).isEqualTo( 4 );
        assertThat( mutableTarget.getNested().getBar() ).isEqualTo( "Phineas" );

        assertThat( immutableTarget.getCount() ).isEqualTo( 4 );
        assertThat( immutableTarget.getNested().getBar() ).isEqualTo( "Phineas" );
    }
}
