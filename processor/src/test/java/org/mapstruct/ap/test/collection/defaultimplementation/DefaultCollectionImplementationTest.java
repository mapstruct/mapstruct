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
package org.mapstruct.ap.test.collection.defaultimplementation;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;

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
@RunWith(AnnotationProcessorTestRunner.class)
public class DefaultCollectionImplementationTest {

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForConcurrentMap() {
        ConcurrentMap<String, TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFooMapToTargetFooConcurrentMap( createSourceFooMap() );

        assertResultMap( target );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForConcurrentNavigableMap() {
        ConcurrentNavigableMap<String, TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFooMapToTargetFooConcurrentNavigableMap( createSourceFooMap() );

        assertResultMap( target );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForMap() {
        Map<String, TargetFoo> target = SourceTargetMapper.INSTANCE.sourceFooMapToTargetFooMap( createSourceFooMap() );

        assertResultMap( target );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForNavigableMap() {
        NavigableMap<String, TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFooMapToTargetFooNavigableMap( createSourceFooMap() );

        assertResultMap( target );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForSortedMap() {
        SortedMap<String, TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFooMapToTargetFooSortedMap( createSourceFooMap() );

        assertResultMap( target );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForNaviableSet() {
        NavigableSet<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFooNavigableSet( createSourceFooList() );

        assertResultList( target );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForCollection() {
        Collection<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos( (Collection<SourceFoo>) createSourceFooList() );

        assertResultList( target );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForIterable() {
        Iterable<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos( (Iterable<SourceFoo>) createSourceFooList() );

        assertResultList( target );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForList() {
        List<TargetFoo> target = SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos( createSourceFooList() );

        assertResultList( target );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForSet() {
        Set<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos( new HashSet<SourceFoo>( createSourceFooList() ) );

        assertResultList( target );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForSortedSet() {
        SortedSet<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFooSortedSet( createSourceFooList() );

        assertResultList( target );
    }

    @Test
    @IssueKey("19")
    public void shouldUseTargetParameterForMapping() {
        List<TargetFoo> target = new ArrayList<TargetFoo>();
        SourceTargetMapper.INSTANCE.sourceFoosToTargetFoosUsingTargetParameter(
            target,
            createSourceFooList()
        );

        assertResultList( target );
    }

    @Test
    @IssueKey("19")
    public void shouldUseAndReturnTargetParameterForMapping() {
        List<TargetFoo> target = new ArrayList<TargetFoo>();
        Iterable<TargetFoo> result =
            SourceTargetMapper.INSTANCE
                .sourceFoosToTargetFoosUsingTargetParameterAndReturn( createSourceFooList(), target );

        assertThat( target == result ).isTrue();
        assertResultList( target );
    }

    @Test
    @IssueKey("92")
    public void shouldUseDefaultImplementationForListWithoutSetter() {
        Source source = new Source();
        source.setFooList( createSourceFooList() );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFooListNoSetter() ).containsExactly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    private void assertResultList(Iterable<TargetFoo> fooIterable) {
        assertThat( fooIterable ).isNotNull();
        assertThat( fooIterable ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    private void assertResultMap(Map<String, TargetFoo> result) {
        assertThat( result ).isNotNull();
        assertThat( result ).hasSize( 2 );
        assertThat( result ).includes( entry( "1", new TargetFoo( "Bob" ) ), entry( "2", new TargetFoo( "Alice" ) ) );
    }

    private Map<Long, SourceFoo> createSourceFooMap() {
        Map<Long, SourceFoo> map = new HashMap<Long, SourceFoo>();
        map.put( 1L, new SourceFoo( "Bob" ) );
        map.put( 2L, new SourceFoo( "Alice" ) );

        return map;
    }

    private List<SourceFoo> createSourceFooList() {
        return Arrays.asList( new SourceFoo( "Bob" ), new SourceFoo( "Alice" ) );
    }
}
