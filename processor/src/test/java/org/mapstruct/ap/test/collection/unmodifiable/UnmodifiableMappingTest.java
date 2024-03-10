/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.unmodifiable;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({Source.class, Target.class, UnmodifiableMapper.class})
public class UnmodifiableMappingTest {

    @ProcessorTest
    @IssueKey("3334")
    public void shouldReturnUnmodifiableCollection() {
        List<Source> sources = new ArrayList<>();
        sources.add( new Source( "dummy" ) );

        assertThat( UnmodifiableMapper.INSTANCE.mapToCollection( sources ).getClass().getCanonicalName() )
                .isEqualTo( "java.util.Collections.UnmodifiableCollection" );
    }

    @ProcessorTest
    @IssueKey("3334")
    public void shouldReturnUnmodifiableList() {
        List<Source> sources = new ArrayList<>();
        sources.add( new Source( "dummy" ) );

        assertThat( UnmodifiableMapper.INSTANCE.mapToList( sources ).getClass().getCanonicalName() )
                .isEqualTo( "java.util.Collections.UnmodifiableRandomAccessList" );
    }

    @ProcessorTest
    @IssueKey("3334")
    public void shouldReturnUnmodifiableSet() {
        List<Source> sources = new ArrayList<>();
        sources.add( new Source( "dummy" ) );

        assertThat( UnmodifiableMapper.INSTANCE.mapToSet( sources ).getClass().getCanonicalName() )
                .isEqualTo( "java.util.Collections.UnmodifiableSet" );
    }

    @ProcessorTest
    @IssueKey("3334")
    public void shouldReturnUnmodifiableNavigableSet() {
        List<Source> sources = new ArrayList<>();
        sources.add( new Source( "dummy" ) );

        assertThat( UnmodifiableMapper.INSTANCE.mapToNavigableSet( sources ).getClass().getCanonicalName() )
                .isEqualTo( "java.util.Collections.UnmodifiableNavigableSet" );
    }

    @ProcessorTest
    @IssueKey("3334")
    public void shouldReturnUnmodifiableSortedSet() {
        List<Source> sources = new ArrayList<>();
        sources.add( new Source( "dummy" ) );

        assertThat( UnmodifiableMapper.INSTANCE.mapToSortedSet( sources ).getClass().getCanonicalName() )
                .isEqualTo( "java.util.Collections.UnmodifiableSortedSet" );
    }

    @ProcessorTest
    @IssueKey("3334")
    public void shouldReturnUnmodifiableMap() {
        Map<String, Source> sources = new HashMap<>();
        sources.put( "dummy", new Source( "dummy" ) );

        assertThat( UnmodifiableMapper.INSTANCE.mapToMap( sources ).getClass().getCanonicalName() )
                .isEqualTo( "java.util.Collections.UnmodifiableMap" );
    }

    @ProcessorTest
    @IssueKey("3334")
    public void shouldReturnUnmodifiableNavigableMap() {
        Map<String, Source> sources = new HashMap<>();
        sources.put( "dummy", new Source( "dummy" ) );

        assertThat( UnmodifiableMapper.INSTANCE.mapToNavigableMap( sources ).getClass().getCanonicalName() )
                .isEqualTo( "java.util.Collections.UnmodifiableNavigableMap" );
    }

    @ProcessorTest
    @IssueKey("3334")
    public void shouldReturnUnmodifiableSortedMap() {
        Map<String, Source> sources = new HashMap<>();
        sources.put( "dummy", new Source( "dummy" ) );

        assertThat( UnmodifiableMapper.INSTANCE.mapToSortedMap( sources ).getClass().getCanonicalName() )
                .isEqualTo( "java.util.Collections.UnmodifiableSortedMap" );
    }
}
