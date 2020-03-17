/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.element.TypeElement;

/**
 * A service provider interface for the mapping between different enum value naming strategies
 *
 * @author Arne Seime
 */
public interface EnumValueMappingStrategy {

    /**
     * Initializes the enum value mapping strategy
     *
     * @param processingEnvironment environment for facilities
     *
     */
    default void init(MapStructProcessingEnvironment processingEnvironment) {

    }

    /*
     * Return a list of valid transformed enum values available for this type
     */
    String getEnumValue(TypeElement enumType, String enumValue);

}
