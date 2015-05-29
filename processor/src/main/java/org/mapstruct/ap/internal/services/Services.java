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
package org.mapstruct.ap.internal.services;

import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A simple locator for SPI implementations.
 *
 * @author Christian Schuster
 */
public class Services {

    private static final ConcurrentMap<Class<?>, Object> SERVICES = new ConcurrentHashMap<Class<?>, Object>();

    private Services() {
    }

    public static <T> T get(Class<T> serviceType, T defaultValue) {
        @SuppressWarnings("unchecked")
        T service = (T) SERVICES.get( serviceType );

        if ( service == null ) {
            service = loadAndCache( serviceType, defaultValue );
        }

        return service;
    }

    private static <T> T loadAndCache(Class<T> serviceType, T defaultValue) {
        T service = find( serviceType );
        if ( service == null ) {
            service = defaultValue;
        }

        @SuppressWarnings("unchecked")
        T cached = (T) SERVICES.putIfAbsent( serviceType, service );
        if ( cached != null ) {
            service = (T) cached;
        }

        return service;
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
                        + implementation.getClass().getCanonicalName() + "."
                );
            }
        }

        return matchingImplementation;
    }
}
