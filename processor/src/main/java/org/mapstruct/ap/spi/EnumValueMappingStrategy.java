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
     */
    default void init(MapStructProcessingEnvironment processingEnvironment) {

    }

    /**
     * Check if this enum value should be mapped to null
     *
     * @param enumType     type of enum
     * @param enumConstant enum constant
     * @return true if this should be mapped to null in all cases
     */
    boolean isMapToNull(TypeElement enumType, String enumConstant);

    /**
     * Return enum value to use if source is null
     *
     * @param enumType the enum
     * @return enum value or null if there is no designated enum constant
     */
    String getDefaultEnumValue(TypeElement enumType);

    /**
     * Map enum constant to the value use for matching
     *
     * @param enumType     the enum this constant belongs to
     * @param enumConstant constant to transform
     * @return the transformed constant - or or original value from parameter is no transformation is needed. Never return null
     */
    String getEnumValue(TypeElement enumType, String enumConstant);

}
