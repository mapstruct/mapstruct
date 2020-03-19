/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.element.TypeElement;

public class DefaultEnumValueMappingStrategy implements EnumValueMappingStrategy {

    @Override
    public String getEnumValue(TypeElement enumType, String enumConstant) {
        return enumConstant;
    }

    @Override
    public boolean isMapToNull(TypeElement enumType, String enumConstant) {
        return false;
    }

    @Override
    public String getDefaultEnumValue(TypeElement enumType) {
        return null;
    }
}
