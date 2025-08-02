/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;


/**
 * Naming strategy for mapper's implementation name
 */
public interface ImplementationNamingStrategy {


    /**
     * @param className          the name of the interface/abstract class name
     * @param implementationName the implementation name according to {@link  org.mapstruct.MapperConfig#implementationName()}
     * @return the name of the implementation class
     */
    String generateImplementationName(String className, String implementationName);
}
