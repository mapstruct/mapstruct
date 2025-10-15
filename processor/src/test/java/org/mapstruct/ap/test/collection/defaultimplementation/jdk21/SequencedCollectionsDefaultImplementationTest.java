/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.defaultimplementation.jdk21;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;
import java.util.SequencedSet;

import org.mapstruct.ap.test.collection.defaultimplementation.Source;
import org.mapstruct.ap.test.collection.defaultimplementation.SourceFoo;
import org.mapstruct.ap.test.collection.defaultimplementation.Target;
import org.mapstruct.ap.test.collection.defaultimplementation.TargetFoo;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@WithClasses({
    Source.class,
    Target.class,
    SourceFoo.class,
    TargetFoo.class,
    SequencedCollectionsMapper.class
})
@IssueKey("3420")
class SequencedCollectionsDefaultImplementationTest {

    @ProcessorTest
    public void shouldUseDefaultImplementationForSequencedMap() {
        SequencedMap<String, TargetFoo> target =
            SequencedCollectionsMapper.INSTANCE.sourceFooMapToTargetFooSequencedMap( createSourceFooMap() );

        assertResultMap( target );
    }

    @ProcessorTest
    public void shouldUseDefaultImplementationForSequencedSet() {
        SequencedSet<TargetFoo> target =
            SequencedCollectionsMapper.INSTANCE.sourceFoosToTargetFooSequencedSet( createSourceFooList() );

        assertResultList( target );
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
