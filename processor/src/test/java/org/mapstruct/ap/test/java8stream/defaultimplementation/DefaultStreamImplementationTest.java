/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.defaultimplementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Source.class,
    Target.class,
    SourceFoo.class,
    TargetFoo.class,
    SourceTargetMapper.class
})
@IssueKey("962")
public class DefaultStreamImplementationTest {

    @ProcessorTest
    public void shouldUseDefaultImplementationForNavigableSet() {
        NavigableSet<TargetFoo> target =
            SourceTargetMapper.INSTANCE.streamToNavigableSet( createSourceFooStream() );

        assertResultList( target );
        assertThat( target ).isInstanceOf( TreeSet.class );
    }

    @ProcessorTest
    public void shouldUseDefaultImplementationForCollection() {
        Collection<TargetFoo> target =
            SourceTargetMapper.INSTANCE.streamToCollection( createSourceFooStream() );

        assertResultList( target );
        assertThat( target ).isInstanceOf( ArrayList.class );
    }

    @ProcessorTest
    public void shouldUseDefaultImplementationForIterable() {
        Iterable<TargetFoo> target =
            SourceTargetMapper.INSTANCE.streamToIterable( createSourceFooStream() );

        assertResultList( target );
        assertThat( target ).isInstanceOf( ArrayList.class );
    }

    @ProcessorTest
    public void shouldUseDefaultImplementationForList() {
        List<TargetFoo> target = SourceTargetMapper.INSTANCE.streamToList( createSourceFooStream() );

        assertResultList( target );
        assertThat( target ).isInstanceOf( ArrayList.class );
    }

    @ProcessorTest
    public void shouldUseDefaultImplementationForSet() {
        Set<TargetFoo> target =
            SourceTargetMapper.INSTANCE.streamToSet( createSourceFooStream() );

        assertResultList( target );
        assertThat( target ).isInstanceOf( HashSet.class );
    }

    @ProcessorTest
    public void shouldUseDefaultImplementationForSortedSet() {
        SortedSet<TargetFoo> target =
            SourceTargetMapper.INSTANCE.streamToSortedSet( createSourceFooStream() );

        assertResultList( target );
        assertThat( target ).isInstanceOf( TreeSet.class );
    }

    @ProcessorTest
    public void shouldUseTargetParameterForMapping() {
        List<TargetFoo> target = new ArrayList<>();
        SourceTargetMapper.INSTANCE.sourceFoosToTargetFoosUsingTargetParameter(
            target,
            createSourceFooStream()
        );

        assertResultList( target );
    }

    @ProcessorTest
    public void shouldUseTargetParameterForArrayMapping() {
        TargetFoo[] target = new TargetFoo[3];
        SourceTargetMapper.INSTANCE.streamToArrayUsingTargetParameter(
            target,
            createSourceFooStream()
        );

        assertThat( target ).isNotNull();
        assertThat( target ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ), null );
    }

    @ProcessorTest
    public void shouldUseTargetParameterForArrayMappingAndSmallerArray() {
        TargetFoo[] target = new TargetFoo[1];
        SourceTargetMapper.INSTANCE.streamToArrayUsingTargetParameter(
            target,
            createSourceFooStream()
        );

        assertThat( target ).isNotNull();
        assertThat( target ).containsOnly( new TargetFoo( "Bob" ) );
    }

    @ProcessorTest
    public void shouldUseAndReturnTargetParameterForArrayMapping() {
        TargetFoo[] target = new TargetFoo[3];
        TargetFoo[] result =
            SourceTargetMapper.INSTANCE.streamToArrayUsingTargetParameterAndReturn( createSourceFooStream(), target );

        assertThat( result ).isSameAs( target );
        assertThat( target ).isNotNull();
        assertThat( target ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ), null );
    }

    @ProcessorTest
    public void shouldUseAndReturnTargetParameterForArrayMappingAndSmallerArray() {
        TargetFoo[] target = new TargetFoo[1];
        TargetFoo[] result =
            SourceTargetMapper.INSTANCE.streamToArrayUsingTargetParameterAndReturn( createSourceFooStream(), target );

        assertThat( result ).isSameAs( target );
        assertThat( target ).isNotNull();
        assertThat( target ).containsOnly( new TargetFoo( "Bob" ) );
    }

    @ProcessorTest
    public void shouldUseAndReturnTargetParameterForMapping() {
        List<TargetFoo> target = new ArrayList<>();
        Iterable<TargetFoo> result =
            SourceTargetMapper.INSTANCE
                .sourceFoosToTargetFoosUsingTargetParameterAndReturn( createSourceFooStream(), target );

        assertThat( result ).isSameAs( target );
        assertResultList( target );
    }

    @ProcessorTest
    public void shouldUseDefaultImplementationForListWithoutSetter() {
        Source source = new Source();
        source.setFooStream( createSourceFooStream() );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFooListNoSetter() ).containsExactly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    private void assertResultList(Iterable<TargetFoo> fooIterable) {
        assertThat( fooIterable ).isNotNull();
        assertThat( fooIterable ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    private Stream<SourceFoo> createSourceFooStream() {
        return Stream.of( new SourceFoo( "Bob" ), new SourceFoo( "Alice" ) );
    }
}
