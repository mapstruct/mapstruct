/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

public class DefaultImplementationNamingStrategy implements ImplementationNamingStrategy {
    @Override
    public String generateImplementationName(String className, String implementationName) {
        return implementationName;
    }
}
