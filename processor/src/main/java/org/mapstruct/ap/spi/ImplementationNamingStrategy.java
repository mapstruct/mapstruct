/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

/**
 * Naming strategy for mappers' and delegates' implementation names
 */
public interface ImplementationNamingStrategy {
    /**
     * @param className          the name of the interface/abstract class name
     * @param implementationName the implementation name according to
     * {@link  org.mapstruct.MapperConfig#implementationName()}
     * @return the name of the Mapper implementation class
     */
    String generateMapperImplementationName(String className, String implementationName);

    /**
     * @param className          the name of the interface/abstract class name
     * @param implementationName the implementation name according to
     * {@link  org.mapstruct.MapperConfig#implementationName()}
     * @return the name of the Decorator implementation class
     */
    String generateDecoratorImplementationName(String className, String implementationName);

}
