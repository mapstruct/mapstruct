/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import org.mapstruct.ap.spi.EnumConstantNamingStrategy;

import javax.lang.model.element.TypeElement;

/**
 * Wrapper class for the EnumConstantNamingStrategy
 */
public class ValueMappingUtils {
    private final EnumConstantNamingStrategy enumConstantNamingStrategy;

    public ValueMappingUtils(EnumConstantNamingStrategy enumConstantNamingStrategy) {
        this.enumConstantNamingStrategy = enumConstantNamingStrategy;
    }

    public String getEnumConstant(TypeElement enumType, String enumConstant) {
        return enumConstantNamingStrategy.getEnumConstant( enumType, enumConstant );
    }

    public boolean isMapEnumConstantToNull(TypeElement enumType, String enumConstant) {
        return enumConstantNamingStrategy.isMapEnumConstantToNull( enumType, enumConstant );
    }

    public String getDefaultEnumConstant(TypeElement enumType) {
        return enumConstantNamingStrategy.getDefaultEnumConstant( enumType );
    }
}
