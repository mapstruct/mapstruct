/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * A simple locator for SPI implementations.
 *
 * @author Christian Schuster
 */
public class Services {

    private Services() {
    }

    public static <T> Iterable<T> all(Class<T> serviceType) {
        return ServiceLoader.load( serviceType, Services.class.getClassLoader() );
    }

    public static <T> T get(Class<T> serviceType, T defaultValue) {

        Iterator<T> services = ServiceLoader.load( serviceType, Services.class.getClassLoader() ).iterator();

        T result;
        if ( services.hasNext() ) {
            result = services.next();
        }
        else {
            result = defaultValue;
        }
        if ( services.hasNext() ) {
           throw new IllegalStateException(
               "Multiple implementations have been found for the service provider interface" );
        }
        return result;
    }
}
