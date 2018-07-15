/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.mappingTarget.simple;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    MutableTarget.class,
    SimpleMutableSource.class,
    SimpleImmutableTarget.class,
    SimpleBuilderMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class BuilderInfoTargetTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    public void testSimpleImmutableBuilderHappyPath() {
        SimpleMutableSource source = new SimpleMutableSource();
        source.setAge( 3 );
        source.setFullName( "Bob" );
        SimpleImmutableTarget targetObject = SimpleBuilderMapper.INSTANCE.toImmutable(
            source,
            SimpleImmutableTarget.builder()
        );
        assertThat( targetObject.getAge() ).isEqualTo( 3 );
        assertThat( targetObject.getName() ).isEqualTo( "Bob" );
    }

    @Test
    public void testMutableTargetWithBuilder() {
        SimpleMutableSource source = new SimpleMutableSource();
        source.setAge( 20 );
        source.setFullName( "Filip" );
        MutableTarget target = SimpleBuilderMapper.INSTANCE.toMutableTarget( source );
        assertThat( target.getAge() ).isEqualTo( 20 );
        assertThat( target.getName() ).isEqualTo( "Filip" );
        assertThat( target.getSource() ).isEqualTo( "Builder" );
    }

    @Test
    public void testUpdateMutableWithBuilder() {
        SimpleMutableSource source = new SimpleMutableSource();
        source.setAge( 20 );
        source.setFullName( "Filip" );
        MutableTarget target = new MutableTarget();
        target.setAge( 10 );
        target.setName( "Fil" );

        assertThat( target.getAge() ).isEqualTo( 10 );
        assertThat( target.getName() ).isEqualTo( "Fil" );
        assertThat( target.getSource() ).isEqualTo( "Empty constructor" );

        SimpleBuilderMapper.INSTANCE.updateMutableTarget( source, target );
        assertThat( target.getAge() ).isEqualTo( 20 );
        assertThat( target.getName() ).isEqualTo( "Filip" );
        assertThat( target.getSource() ).isEqualTo( "Empty constructor" );
    }

    @Test
    public void updatingTargetWithNoSettersShouldNotFail() {

        SimpleMutableSource source = new SimpleMutableSource();
        source.setAge( 10 );

        SimpleImmutableTarget target = SimpleImmutableTarget.builder()
            .age( 20 )
            .build();

        assertThat( target.getAge() ).isEqualTo( 20 );
        SimpleBuilderMapper.INSTANCE.toImmutable( source, target );
        assertThat( target.getAge() ).isEqualTo( 20 );
    }
}
