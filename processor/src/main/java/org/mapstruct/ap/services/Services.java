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
package org.mapstruct.ap.services;

import java.util.ServiceLoader;

import org.mapstruct.spi.AccessorNamingStrategy;

/**
 * A simple locator for SPI implementations.
 *
 * @author Christian Schuster
 */
public class Services {

    private static final AccessorNamingStrategy ACCESSOR_NAMING_STRATEGY = findAccessorNamingStrategy();

    private Services() {
    }

    /**
     * Obtain an implementation of {@link AccessorNamingStrategy}. If no specialized implementation is found using
     * {@link ServiceLoader}, a JavaBeans-compliant default implementation is returned. The result is cached across
     * invocations.
     *
     * @return The implementation of {@link AccessorNamingStrategy}.
     * @throws IllegalStateException If more than one implementation is found by
     *             {@link ServiceLoader#load(Class, ClassLoader)}.
     */
    public static AccessorNamingStrategy getAccessorNamingStrategy() {
        return ACCESSOR_NAMING_STRATEGY;
    }

    private static AccessorNamingStrategy findAccessorNamingStrategy() {
        AccessorNamingStrategy defaultImpl = new DefaultAccessorNamingStrategy();
        AccessorNamingStrategy impl = find( AccessorNamingStrategy.class );
        if ( impl == null ) {
            impl = defaultImpl;
        }
        impl.setDefaultAccessorNamingStrategy( defaultImpl );
        return impl;
    }

    private static <T> T find(Class<T> spi) {
        T matchingImplementation = null;

        for ( T implementation : ServiceLoader.load( spi, spi.getClassLoader() ) ) {
            if ( matchingImplementation == null ) {
                matchingImplementation = implementation;
            }
            else {
                throw new IllegalStateException(
                    "Multiple implementations have been found for the service provider interface "
                        + spi.getCanonicalName() + ": " + matchingImplementation.getClass().getCanonicalName() + ", "
                        + implementation.getClass().getCanonicalName() + "." );
            }
        }

        return matchingImplementation;
    }
}
