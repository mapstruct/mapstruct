/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provides utility methods around collections.
 *
 * @author Gunnar Morling
 */
public class Collections {

    private Collections() {
    }

    public static <T> Set<T> asSet(T... elements) {
        Set<T> set = new HashSet<T>();

        for ( T element : elements ) {
            set.add( element );
        }

        return set;
    }

    public static <T> List<T> newArrayList(T... elements) {
        List<T> list = new ArrayList<T>();

        list.addAll( Arrays.asList( elements ) );

        return list;
    }

    public static <T> Set<T> asSet(Collection<T> collection, T... elements) {
        Set<T> set = new HashSet<T>( collection );

        for ( T element : elements ) {
            set.add( element );
        }

        return set;
    }

    public static <T> Set<T> asSet(Collection<T> collection, Collection<T>... elements) {
        Set<T> set = new HashSet<T>( collection );

        for ( Collection<T> element : elements ) {
            set.addAll( element );
        }

        return set;
    }

    public static <T> T first(Collection<T> collection) {
        return collection.iterator().next();
    }

    public static <T> T last(List<T> list) {
        return list.get( list.size() - 1 );
    }

    public static <T> List<T> join(List<T> a, List<T> b) {
        List<T> result = new ArrayList<T>( a.size() + b.size() );

        result.addAll( a );
        result.addAll( b );

        return result;
    }

    public static <E> boolean hasNonNullElements(Iterable<E> elements) {
        if ( elements != null ) {
            for ( E e : elements ) {
                if ( e != null ) {
                    return true;
                }
            }
        }
        return false;
    }
}
