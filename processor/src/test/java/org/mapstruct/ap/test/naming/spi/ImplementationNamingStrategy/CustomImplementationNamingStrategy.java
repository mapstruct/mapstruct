/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming.spi.ImplementationNamingStrategy;

import org.mapstruct.ap.spi.ImplementationNamingStrategy;

public class CustomImplementationNamingStrategy implements ImplementationNamingStrategy {

    @Override
    public String generateMapperImplementationName(String className, String implementationName) {
        return implementationName + "MapperImpl";
    }

    @Override
    public String generateDecoratorImplementationName(String className, String implementationName) {
        return implementationName + "DecoratorImpl";
    }

}
