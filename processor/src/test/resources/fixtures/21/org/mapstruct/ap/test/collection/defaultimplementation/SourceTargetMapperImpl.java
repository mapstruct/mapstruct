/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.defaultimplementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-18T14:48:39+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    @Override
    public Target sourceToTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( target.getFooListNoSetter() != null ) {
            List<TargetFoo> list = sourceFoosToTargetFoos( source.getFooList() );
            if ( list != null ) {
                target.getFooListNoSetter().addAll( list );
            }
        }

        return target;
    }

    @Override
    public TargetFoo sourceFooToTargetFoo(SourceFoo sourceFoo) {
        if ( sourceFoo == null ) {
            return null;
        }

        TargetFoo targetFoo = new TargetFoo();

        targetFoo.setName( sourceFoo.getName() );

        return targetFoo;
    }

    @Override
    public List<TargetFoo> sourceFoosToTargetFoos(List<SourceFoo> foos) {
        if ( foos == null ) {
            return null;
        }

        List<TargetFoo> list = new ArrayList<TargetFoo>( foos.size() );
        for ( SourceFoo sourceFoo : foos ) {
            list.add( sourceFooToTargetFoo( sourceFoo ) );
        }

        return list;
    }

    @Override
    public Set<TargetFoo> sourceFoosToTargetFoos(Set<SourceFoo> foos) {
        if ( foos == null ) {
            return null;
        }

        Set<TargetFoo> set = LinkedHashSet.newLinkedHashSet( foos.size() );
        for ( SourceFoo sourceFoo : foos ) {
            set.add( sourceFooToTargetFoo( sourceFoo ) );
        }

        return set;
    }

    @Override
    public Collection<TargetFoo> sourceFoosToTargetFoos(Collection<SourceFoo> foos) {
        if ( foos == null ) {
            return null;
        }

        Collection<TargetFoo> collection = new ArrayList<TargetFoo>( foos.size() );
        for ( SourceFoo sourceFoo : foos ) {
            collection.add( sourceFooToTargetFoo( sourceFoo ) );
        }

        return collection;
    }

    @Override
    public Iterable<TargetFoo> sourceFoosToTargetFoos(Iterable<SourceFoo> foos) {
        if ( foos == null ) {
            return null;
        }

        ArrayList<TargetFoo> iterable = new ArrayList<TargetFoo>();
        for ( SourceFoo sourceFoo : foos ) {
            iterable.add( sourceFooToTargetFoo( sourceFoo ) );
        }

        return iterable;
    }

    @Override
    public void sourceFoosToTargetFoosUsingTargetParameter(List<TargetFoo> targetFoos, Iterable<SourceFoo> sourceFoos) {
        if ( sourceFoos == null ) {
            return;
        }

        targetFoos.clear();
        for ( SourceFoo sourceFoo : sourceFoos ) {
            targetFoos.add( sourceFooToTargetFoo( sourceFoo ) );
        }
    }

    @Override
    public Iterable<TargetFoo> sourceFoosToTargetFoosUsingTargetParameterAndReturn(Iterable<SourceFoo> sourceFoos, List<TargetFoo> targetFoos) {
        if ( sourceFoos == null ) {
            return targetFoos;
        }

        targetFoos.clear();
        for ( SourceFoo sourceFoo : sourceFoos ) {
            targetFoos.add( sourceFooToTargetFoo( sourceFoo ) );
        }

        return targetFoos;
    }

    @Override
    public SortedSet<TargetFoo> sourceFoosToTargetFooSortedSet(Collection<SourceFoo> foos) {
        if ( foos == null ) {
            return null;
        }

        SortedSet<TargetFoo> sortedSet = new TreeSet<TargetFoo>();
        for ( SourceFoo sourceFoo : foos ) {
            sortedSet.add( sourceFooToTargetFoo( sourceFoo ) );
        }

        return sortedSet;
    }

    @Override
    public NavigableSet<TargetFoo> sourceFoosToTargetFooNavigableSet(Collection<SourceFoo> foos) {
        if ( foos == null ) {
            return null;
        }

        NavigableSet<TargetFoo> navigableSet = new TreeSet<TargetFoo>();
        for ( SourceFoo sourceFoo : foos ) {
            navigableSet.add( sourceFooToTargetFoo( sourceFoo ) );
        }

        return navigableSet;
    }

    @Override
    public Map<String, TargetFoo> sourceFooMapToTargetFooMap(Map<Long, SourceFoo> foos) {
        if ( foos == null ) {
            return null;
        }

        Map<String, TargetFoo> map = LinkedHashMap.newLinkedHashMap( foos.size() );

        for ( java.util.Map.Entry<Long, SourceFoo> entry : foos.entrySet() ) {
            String key = String.valueOf( entry.getKey() );
            TargetFoo value = sourceFooToTargetFoo( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }

    @Override
    public SortedMap<String, TargetFoo> sourceFooMapToTargetFooSortedMap(Map<Long, SourceFoo> foos) {
        if ( foos == null ) {
            return null;
        }

        SortedMap<String, TargetFoo> sortedMap = new TreeMap<String, TargetFoo>();

        for ( java.util.Map.Entry<Long, SourceFoo> entry : foos.entrySet() ) {
            String key = String.valueOf( entry.getKey() );
            TargetFoo value = sourceFooToTargetFoo( entry.getValue() );
            sortedMap.put( key, value );
        }

        return sortedMap;
    }

    @Override
    public NavigableMap<String, TargetFoo> sourceFooMapToTargetFooNavigableMap(Map<Long, SourceFoo> foos) {
        if ( foos == null ) {
            return null;
        }

        NavigableMap<String, TargetFoo> navigableMap = new TreeMap<String, TargetFoo>();

        for ( java.util.Map.Entry<Long, SourceFoo> entry : foos.entrySet() ) {
            String key = String.valueOf( entry.getKey() );
            TargetFoo value = sourceFooToTargetFoo( entry.getValue() );
            navigableMap.put( key, value );
        }

        return navigableMap;
    }

    @Override
    public ConcurrentMap<String, TargetFoo> sourceFooMapToTargetFooConcurrentMap(Map<Long, SourceFoo> foos) {
        if ( foos == null ) {
            return null;
        }

        ConcurrentMap<String, TargetFoo> concurrentMap = new ConcurrentHashMap<String, TargetFoo>( Math.max( (int) ( foos.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<Long, SourceFoo> entry : foos.entrySet() ) {
            String key = String.valueOf( entry.getKey() );
            TargetFoo value = sourceFooToTargetFoo( entry.getValue() );
            concurrentMap.put( key, value );
        }

        return concurrentMap;
    }

    @Override
    public ConcurrentNavigableMap<String, TargetFoo> sourceFooMapToTargetFooConcurrentNavigableMap(Map<Long, SourceFoo> foos) {
        if ( foos == null ) {
            return null;
        }

        ConcurrentNavigableMap<String, TargetFoo> concurrentNavigableMap = new ConcurrentSkipListMap<String, TargetFoo>();

        for ( java.util.Map.Entry<Long, SourceFoo> entry : foos.entrySet() ) {
            String key = String.valueOf( entry.getKey() );
            TargetFoo value = sourceFooToTargetFoo( entry.getValue() );
            concurrentNavigableMap.put( key, value );
        }

        return concurrentNavigableMap;
    }
}
