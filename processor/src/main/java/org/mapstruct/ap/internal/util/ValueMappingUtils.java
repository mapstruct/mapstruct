/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import org.mapstruct.ap.spi.EnumValueMappingStrategy;

import javax.lang.model.element.TypeElement;

public class ValueMappingUtils {
    public ValueMappingUtils(EnumValueMappingStrategy enumValueMappingStrategy) {
        this.enumValueMappingStrategy = enumValueMappingStrategy;
    }

    private final EnumValueMappingStrategy enumValueMappingStrategy;

    public String getEnumValue(TypeElement type, String enumValue) {
        return enumValueMappingStrategy.getEnumValue( type, enumValue );
    }

    public boolean isMapToNull(TypeElement type, String enumValue) {
        return enumValueMappingStrategy.isMapToNull( type, enumValue );
    }

    public String getDefaultEnumValue(TypeElement type) {
        return enumValueMappingStrategy.getDefaultEnumValue( type );
    }
}
