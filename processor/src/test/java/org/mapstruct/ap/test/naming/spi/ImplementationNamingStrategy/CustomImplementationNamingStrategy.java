/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming.spi.ImplementationNamingStrategy;

import org.mapstruct.ap.spi.ImplementationNamingStrategy;

public class CustomImplementationNamingStrategy implements ImplementationNamingStrategy {

    public static final String PREFIX = "PREFIX";
    public static final String SUFFIX = "SUFFIX";

    @Override
    public String generateImplementationName(String className, String implementationName) {
        return PREFIX + className + SUFFIX;
    }
}
