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
