/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.util.ArrayList;
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

    @SafeVarargs
    public static <T> Set<T> asSet(T... elements) {
        Set<T> set = new HashSet<>( elements.length );
        java.util.Collections.addAll( set, elements );
        return set;
    }

    @SafeVarargs
    public static <T> Set<T> asSet(Collection<T> collection, T... elements) {
        Set<T> set = new HashSet<>( collection.size() + elements.length );
        java.util.Collections.addAll( set, elements );
        return set;
    }

    @SafeVarargs
    public static <T> Set<T> asSet(Collection<T> collection, Collection<T>... elements) {
        Set<T> set = new HashSet<>( collection );

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
        List<T> result = new ArrayList<>( a.size() + b.size() );

        result.addAll( a );
        result.addAll( b );

        return result;
    }

}
