/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.element.TypeElement;

import org.mapstruct.util.Experimental;

/**
 * A service provider interface for the mapping between different enum constants
 *
 * @author Arne Seime
 * @author Filip Hrisafov
 *
 * @since 1.4
 */
@Experimental("This SPI can have its signature changed in subsequent releases")
public interface EnumMappingStrategy {

    /**
     * Initializes the enum value mapping strategy
     *
     * @param processingEnvironment environment for facilities
     */
    default void init(MapStructProcessingEnvironment processingEnvironment) {

    }

    /**
     * Return the default enum constant to use if the source is null.
     *
     * @param enumType the enum
     * @return enum value or null if there is no designated enum constant
     */
    String getDefaultNullEnumConstant(TypeElement enumType);

    /**
     * Map the enum constant to the value use for matching.
     * In case you want this enum constant to match to null return {@link org.mapstruct.MappingConstants#NULL}
     *
     * @param enumType the enum this constant belongs to
     * @param enumConstant constant to transform
     *
     * @return the transformed constant - or the original value from the parameter if no transformation is needed.
     * never return null
     */
    String getEnumConstant(TypeElement enumType, String enumConstant);

    /**
     * Return the type element of the exception that should be used in the generated code
     * for an unexpected enum constant.
     *
     * @return the type element of the exception that should be used, never {@code null}
     */
    TypeElement getUnexpectedValueMappingExceptionType();
}
