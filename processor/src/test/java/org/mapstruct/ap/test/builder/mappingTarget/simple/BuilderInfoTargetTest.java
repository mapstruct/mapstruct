/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
