/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.defaultimplementation;

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

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@WithClasses({
    Source.class,
    Target.class,
    SourceFoo.class,
    TargetFoo.class,
    SourceTargetMapper.class
})
public class DefaultCollectionImplementationTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( SourceTargetMapper.class );

    @ProcessorTest
    @IssueKey("6")
    public void shouldUseDefaultImplementationForConcurrentMap() {
        ConcurrentMap<String, TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFooMapToTargetFooConcurrentMap( createSourceFooMap() );

        assertResultMap( target );
    }

    @ProcessorTest
    @IssueKey("6")
    public void shouldUseDefaultImplementationForConcurrentNavigableMap() {
        ConcurrentNavigableMap<String, TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFooMapToTargetFooConcurrentNavigableMap( createSourceFooMap() );

        assertResultMap( target );
    }

    @ProcessorTest
    @IssueKey("6")
    public void shouldUseDefaultImplementationForMap() {
        Map<String, TargetFoo> target = SourceTargetMapper.INSTANCE.sourceFooMapToTargetFooMap( createSourceFooMap() );

        assertResultMap( target );
    }

    @ProcessorTest
    @IssueKey("6")
    public void shouldUseDefaultImplementationForNavigableMap() {
        NavigableMap<String, TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFooMapToTargetFooNavigableMap( createSourceFooMap() );

        assertResultMap( target );
    }

    @ProcessorTest
    @IssueKey("6")
    public void shouldUseDefaultImplementationForSortedMap() {
        SortedMap<String, TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFooMapToTargetFooSortedMap( createSourceFooMap() );

        assertResultMap( target );
    }

    @ProcessorTest
    @IssueKey("6")
    public void shouldUseDefaultImplementationForNaviableSet() {
        NavigableSet<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFooNavigableSet( createSourceFooList() );

        assertResultList( target );
    }

    @ProcessorTest
    @IssueKey("6")
    public void shouldUseDefaultImplementationForCollection() {
        Collection<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos( (Collection<SourceFoo>) createSourceFooList() );

        assertResultList( target );
    }

    @ProcessorTest
    @IssueKey("6")
    public void shouldUseDefaultImplementationForIterable() {
        Iterable<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos( (Iterable<SourceFoo>) createSourceFooList() );

        assertResultList( target );
    }

    @ProcessorTest
    @IssueKey("6")
    public void shouldUseDefaultImplementationForList() {
        List<TargetFoo> target = SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos( createSourceFooList() );

        assertResultList( target );
    }

    @ProcessorTest
    @IssueKey("6")
    public void shouldUseDefaultImplementationForSet() {
        Set<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos( new HashSet<>( createSourceFooList() ) );

        assertResultList( target );
    }

    @ProcessorTest
    @IssueKey("6")
    public void shouldUseDefaultImplementationForSortedSet() {
        SortedSet<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFooSortedSet( createSourceFooList() );

        assertResultList( target );
    }

    @ProcessorTest
    @IssueKey("19")
    public void shouldUseTargetParameterForMapping() {
        List<TargetFoo> target = new ArrayList<>();
        SourceTargetMapper.INSTANCE.sourceFoosToTargetFoosUsingTargetParameter(
            target,
            createSourceFooList()
        );

        assertResultList( target );
    }

    @ProcessorTest
    @IssueKey("19")
    public void shouldUseAndReturnTargetParameterForMapping() {
        List<TargetFoo> target = new ArrayList<>();
        Iterable<TargetFoo> result =
            SourceTargetMapper.INSTANCE
                .sourceFoosToTargetFoosUsingTargetParameterAndReturn( createSourceFooList(), target );

        assertThat( result ).isSameAs( target );
        assertResultList( target );
    }

    @ProcessorTest
    @IssueKey("1752")
    public void shouldUseAndReturnTargetParameterForNullMapping() {
        List<TargetFoo> target = new ArrayList<>();
        target.add( new TargetFoo( "Bob" ) );
        target.add( new TargetFoo( "Alice" ) );
        Iterable<TargetFoo> result =
            SourceTargetMapper.INSTANCE
                .sourceFoosToTargetFoosUsingTargetParameterAndReturn( null, target );

        assertThat( result ).isSameAs( target );
        assertResultList( target );
    }

    @ProcessorTest
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
        assertThat( result ).contains( entry( "1", new TargetFoo( "Bob" ) ), entry( "2", new TargetFoo( "Alice" ) ) );
    }

    private Map<Long, SourceFoo> createSourceFooMap() {
        Map<Long, SourceFoo> map = new HashMap<>();
        map.put( 1L, new SourceFoo( "Bob" ) );
        map.put( 2L, new SourceFoo( "Alice" ) );

        return map;
    }

    private List<SourceFoo> createSourceFooList() {
        return Arrays.asList( new SourceFoo( "Bob" ), new SourceFoo( "Alice" ) );
    }
}
