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
package org.mapstruct.ap.internal.util;

/**
 * Utilities for working with classes. It is mainly needed because using the {@link javax.lang.model.util.Elements}
 * is not always correct. For example when compiling with JDK 9 and source version 8 classes from different modules
 * are available by {@link javax.lang.model.util.Elements#getTypeElement(CharSequence)} but they are actually not
 * if those modules are not added during compilation.
 *
 * @author Filip Hrisafov
 */
class ClassUtils {

    private ClassUtils() {
    }

    /**
     * Determine whether the {@link Class} identified by the supplied name is present
     * and can be loaded. Will return {@code false} if either the class or
     * one of its dependencies is not present or cannot be loaded.
     *
     * @param className the name of the class to check
     * @param classLoader the class loader to use
     * (may be {@code null}, which indicates the default class loader)
     *
     * @return whether the specified class is present
     */
    static boolean isPresent(String className, ClassLoader classLoader) {
        try {
            ClassLoader classLoaderToUse = classLoader;
            if ( classLoaderToUse == null ) {
                classLoaderToUse = getDefaultClassLoader();
            }
            classLoaderToUse.loadClass( className );
            return true;
        }
        catch ( ClassNotFoundException ex ) {
            // Class or one of its dependencies is not present...
            return false;
        }
    }

    /**
     * Return the default ClassLoader to use: typically the thread context
     * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
     * class will be used as fallback.
     * <p>Call this method if you intend to use the thread context ClassLoader
     * in a scenario where you absolutely need a non-null ClassLoader reference:
     * for example, for class path resource loading (but not necessarily for
     * {@code Class.forName}, which accepts a {@code null} ClassLoader
     * reference as well).
     *
     * @return the default ClassLoader (never {@code null})
     *
     * @see Thread#getContextClassLoader()
     */
    private static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch ( Throwable ex ) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if ( cl == null ) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
        }
        return cl;
    }

}
