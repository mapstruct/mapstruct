/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Andreas Gudian
 *
 */
@IssueKey( "82" )
@WithClasses( { Bar.class, Foo.class, FooMapper.class, ReferencedCustomMapper.class, Source.class,
    SourceTargetMapper.class, Target.class, BaseType.class, SomeType.class, SomeOtherType.class,
    GenericWrapper.class
})
public class ReferencedMapperTest {
    @ProcessorTest
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

    @ProcessorTest
    @IssueKey( "136" )
    public void shouldUseGenericFactoryForIterable() {
        List<SomeType> result = SourceTargetMapper.INSTANCE.fromStringList( Arrays.asList( "foo1", "foo2" ) );

        assertThat( result ).extracting( "value" ).containsExactly( "foo1", "foo2" );
    }

    @ProcessorTest
    @IssueKey( "136" )
    public void shouldUseGenericFactoryForMap() {
        Map<String, String> source = new HashMap<>();
        source.put( "foo1", "bar1" );
        source.put( "foo2", "bar2" );
        Map<SomeType, SomeOtherType> result = SourceTargetMapper.INSTANCE.fromStringMap( source );

        assertThat( result ).hasSize( 2 );
        assertThat( result ).contains(
            entry( new SomeType( "foo1" ), new SomeOtherType( "bar1" ) ),
            entry( new SomeType( "foo2" ), new SomeOtherType( "bar2" ) ) );
    }

    @ProcessorTest
    @IssueKey( "136" )
    @WithClasses({ SourceTargetMapperWithPrimitives.class, SourceWithWrappers.class, TargetWithPrimitives.class })
    public void shouldMapPrimitivesWithCustomMapper() {
        SourceWithWrappers source = new SourceWithWrappers();
        source.setProp1( new SomeType( "42" ) );
        source.setProp2( new SomeType( "1701" ) );
        source.setProp3( new SomeType( "true" ) );
        source.setProp4( new GenericWrapper<>( new SomeType( "x" ) ) );

        TargetWithPrimitives result = SourceTargetMapperWithPrimitives.INSTANCE.sourceToTarget( source );

        assertThat( result.getProp1() ).isEqualTo( 42 );
        assertThat( result.getProp2() ).isEqualTo( 1701 );
        assertThat( result.isProp3() ).isEqualTo( true );
        assertThat( result.getProp4() ).isEqualTo( 'x' );
    }
}
