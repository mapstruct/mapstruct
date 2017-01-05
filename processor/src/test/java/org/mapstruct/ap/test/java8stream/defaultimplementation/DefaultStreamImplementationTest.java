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
package org.mapstruct.ap.test.java8stream.defaultimplementation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@WithClasses({
    Source.class,
    Target.class,
    SourceFoo.class,
    TargetFoo.class,
    SourceTargetMapper.class
})
@IssueKey("962")
@RunWith(AnnotationProcessorTestRunner.class)
public class DefaultStreamImplementationTest {

    @Test
    public void shouldUseDefaultImplementationForNavigableSet() {
        NavigableSet<TargetFoo> target =
            SourceTargetMapper.INSTANCE.streamToNavigableSet( createSourceFooStream() );

        assertResultList( target );
        assertThat( target ).isInstanceOf( TreeSet.class );
    }

    @Test
    public void shouldUseDefaultImplementationForCollection() {
        Collection<TargetFoo> target =
            SourceTargetMapper.INSTANCE.streamToCollection( createSourceFooStream() );

        assertResultList( target );
        assertThat( target ).isInstanceOf( ArrayList.class );
    }

    @Test
    public void shouldUseDefaultImplementationForIterable() {
        Iterable<TargetFoo> target =
            SourceTargetMapper.INSTANCE.streamToIterable( createSourceFooStream() );

        assertResultList( target );
        assertThat( target ).isInstanceOf( ArrayList.class );
    }

    @Test
    public void shouldUseDefaultImplementationForList() {
        List<TargetFoo> target = SourceTargetMapper.INSTANCE.streamToList( createSourceFooStream() );

        assertResultList( target );
        assertThat( target ).isInstanceOf( ArrayList.class );
    }

    @Test
    public void shouldUseDefaultImplementationForSet() {
        Set<TargetFoo> target =
            SourceTargetMapper.INSTANCE.streamToSet( createSourceFooStream() );

        assertResultList( target );
        assertThat( target ).isInstanceOf( HashSet.class );
    }

    @Test
    public void shouldUseDefaultImplementationForSortedSet() {
        SortedSet<TargetFoo> target =
            SourceTargetMapper.INSTANCE.streamToSortedSet( createSourceFooStream() );

        assertResultList( target );
        assertThat( target ).isInstanceOf( TreeSet.class );
    }

    @Test
    public void shouldUseTargetParameterForMapping() {
        List<TargetFoo> target = new ArrayList<TargetFoo>();
        SourceTargetMapper.INSTANCE.sourceFoosToTargetFoosUsingTargetParameter(
            target,
            createSourceFooStream()
        );

        assertResultList( target );
    }

    @Test
    public void shouldUseTargetParameterForArrayMapping() {
        TargetFoo[] target = new TargetFoo[3];
        SourceTargetMapper.INSTANCE.streamToArrayUsingTargetParameter(
            target,
            createSourceFooStream()
        );

        assertThat( target ).isNotNull();
        assertThat( target ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ), null );
    }

    @Test
    public void shouldUseTargetParameterForArrayMappingAndSmallerArray() {
        TargetFoo[] target = new TargetFoo[1];
        SourceTargetMapper.INSTANCE.streamToArrayUsingTargetParameter(
            target,
            createSourceFooStream()
        );

        assertThat( target ).isNotNull();
        assertThat( target ).containsOnly( new TargetFoo( "Bob" ) );
    }

    @Test
    public void shouldUseAndReturnTargetParameterForArrayMapping() {
        TargetFoo[] target = new TargetFoo[3];
        TargetFoo[] result =
            SourceTargetMapper.INSTANCE.streamToArrayUsingTargetParameterAndReturn( createSourceFooStream(), target );

        assertThat( result ).isSameAs( target );
        assertThat( target ).isNotNull();
        assertThat( target ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ), null );
    }

    @Test
    public void shouldUseAndReturnTargetParameterForArrayMappingAndSmallerArray() {
        TargetFoo[] target = new TargetFoo[1];
        TargetFoo[] result =
            SourceTargetMapper.INSTANCE.streamToArrayUsingTargetParameterAndReturn( createSourceFooStream(), target );

        assertThat( result ).isSameAs( target );
        assertThat( target ).isNotNull();
        assertThat( target ).containsOnly( new TargetFoo( "Bob" ) );
    }

    @Test
    public void shouldUseAndReturnTargetParameterForMapping() {
        List<TargetFoo> target = new ArrayList<TargetFoo>();
        Iterable<TargetFoo> result =
            SourceTargetMapper.INSTANCE
                .sourceFoosToTargetFoosUsingTargetParameterAndReturn( createSourceFooStream(), target );

        assertThat( result ).isSameAs( target );
        assertResultList( target );
    }

    @Test
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
        return Arrays.asList( new SourceFoo( "Bob" ), new SourceFoo( "Alice" ) ).stream();
    }
}
