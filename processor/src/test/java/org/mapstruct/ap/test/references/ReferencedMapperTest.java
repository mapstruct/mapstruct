/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.references;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.assertions.MapAssert.entry;

/**
 * @author Andreas Gudian
 *
 */
@IssueKey( "82" )
@WithClasses( { Bar.class, Foo.class, FooMapper.class, ReferencedCustomMapper.class, Source.class,
    SourceTargetMapper.class, Target.class, BaseType.class, SomeType.class, SomeOtherType.class,
    GenericWrapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class ReferencedMapperTest {
    @Test
    public void referencedMappersAreInstatiatedCorrectly() {
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( createSource() );

        assertThat( target ).isNotNull();
        assertThat( target.getProp1() ).isEqualTo( 43 );
        assertThat( target.getProp2() ).isNotNull();
        assertThat( target.getProp2().getProp1() ).isEqualTo( "foo" );
        assertThat( target.getProp3().getValue() ).isEqualTo( "prop3" );
        assertThat( target.getProp4().getWrapped() ).isEqualTo( "prop4" );
    }

    private Source createSource() {
        Source source = new Source();
        source.setProp1( 42 );

        Foo prop2 = new Foo();
        prop2.setProp1( "foo" );
        source.setProp2( prop2 );
        source.setProp3( "prop3" );
        source.setProp4( "prop4" );

        return source;
    }

    @Test
    @IssueKey( "136" )
    public void shouldUseGenericFactoryForIterable() {
        List<SomeType> result = SourceTargetMapper.INSTANCE.fromStringList( Arrays.asList( "foo1", "foo2" ) );

        assertThat( result ).onProperty( "value" ).containsExactly( "foo1", "foo2" );
    }

    @Test
    @IssueKey( "136" )
    public void shouldUseGenericFactoryForMap() {
        Map<String, String> source = new HashMap<String, String>();
        source.put( "foo1", "bar1" );
        source.put( "foo2", "bar2" );
        Map<SomeType, SomeOtherType> result = SourceTargetMapper.INSTANCE.fromStringMap( source );

        assertThat( result ).hasSize( 2 );
        assertThat( result ).includes(
            entry( new SomeType( "foo1" ), new SomeOtherType( "bar1" ) ),
            entry( new SomeType( "foo2" ), new SomeOtherType( "bar2" ) ) );
    }

    @Test
    @IssueKey( "136" )
    @WithClasses({ SourceTargetMapperWithPrimitives.class, SourceWithWrappers.class, TargetWithPrimitives.class })
    public void shouldMapPrimitivesWithCustomMapper() {
        SourceWithWrappers source = new SourceWithWrappers();
        source.setProp1( new SomeType( "42" ) );
        source.setProp2( new SomeType( "1701" ) );
        source.setProp3( new SomeType( "true" ) );
        source.setProp4( new GenericWrapper<SomeType>( new SomeType( "x" ) ) );

        TargetWithPrimitives result = SourceTargetMapperWithPrimitives.INSTANCE.sourceToTarget( source );

        assertThat( result.getProp1() ).isEqualTo( 42 );
        assertThat( result.getProp2() ).isEqualTo( 1701 );
        assertThat( result.isProp3() ).isEqualTo( true );
        assertThat( result.getProp4() ).isEqualTo( 'x' );
    }
}
