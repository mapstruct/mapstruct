package org.mapstruct.ap.test.builder.genericBuilder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.builder.BuilderTests;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

@WithClasses({AbstractThing.class, AbstractThingBuilder.class, ThingOne.class, ThingTwo.class})
@RunWith(AnnotationProcessorTestRunner.class)
@Category(BuilderTests.class)
public class AbstractBuilderTest {

    @Test
    @WithClasses( ThingOneMapper.class )
    public void testAbstractBuilder_HappyPath() {

        final ThingOneMapper mapper = Mappers.getMapper( ThingOneMapper.class );
        final ThingOne thingOne = mapper.fromThingTwo( new ThingTwo("foo", 31) );

        assertThat( thingOne.getBar() ).isEqualTo( 31 );
        assertThat( thingOne.getFoo() ).isEqualTo( "foo" );
    }
}
