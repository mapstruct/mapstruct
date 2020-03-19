/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.element.TypeElement;

public class DefaultEnumConstantNamingStrategy implements EnumConstantNamingStrategy {

    @Override
    public String getEnumConstant(TypeElement enumType, String enumConstant) {
        return enumConstant;
    }

    @Override
    public boolean isMapEnumConstantToNull(TypeElement enumType, String enumConstant) {
        return false;
    }

    @Override
    public String getDefaultEnumConstant(TypeElement enumType) {
        return null;
    }
}
