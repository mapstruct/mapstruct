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
