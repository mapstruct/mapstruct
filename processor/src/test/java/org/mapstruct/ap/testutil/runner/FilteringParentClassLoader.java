/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * A ClassLoader that can be used to hide packages or classes from its default parent ClassLoader so that the
 * ClassLoader using this as its parent can load the class on its own.
 *
 * @author Andreas Gudian
 */
final class FilteringParentClassLoader extends ClassLoader {
    private Collection<String> excludedPrefixes;

    /**
     * @param excludedPrefixes class name prefixes to exclude
     */
    FilteringParentClassLoader(String... excludedPrefixes) {
        this.excludedPrefixes = new ArrayList<String>( Arrays.asList( excludedPrefixes ) );
    }

    /**
     * @param classes The classes to hide (inner classes are hidden as well)
     * @return {@code this}
     */
    FilteringParentClassLoader hidingClasses(Collection<Class<?>> classes) {
        for ( Class<?> clazz : classes ) {
            hidingClass( clazz );
        }

        return this;
    }

    /**
     * @param clazz The class to hide (inner classes are hidden as well)
     * @return {@code this}
     */
    FilteringParentClassLoader hidingClass(Class<?> clazz) {
        excludedPrefixes.add( clazz.getName() );

        return this;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        for ( String excluded : excludedPrefixes ) {
            if ( name.startsWith( excluded ) ) {
                return null;
            }
        }

        return super.loadClass( name, resolve );
    }
}
