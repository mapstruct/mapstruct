/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.spi.EnumConstantNamingStrategy;

public class CustomEnumConstantNamingStrategy implements EnumConstantNamingStrategy {
    @Override
    public boolean isMapEnumConstantToNull(TypeElement enumType, String enumConstant) {
        // If enum is of a type where transformation should happen, check if this value
        // is listed as a null mapping
        if ( shouldHandle( enumType ) && "DEFAULT_CHEESE_TYPE".equals( enumConstant ) ) {
            return true;
        }
        return false;
    }

    @Override
    public String getDefaultEnumConstant(TypeElement enumType) {
        if ( shouldHandle( enumType ) ) {
            return "DEFAULT_CHEESE_TYPE";
        }
        else {
            return null;
        }
    }

    @Override
    public String getEnumConstant(TypeElement enumType, String enumConstant) {
        if ( shouldHandle( enumType ) ) {
            return enumConstant.replace( "_CHEESE_TYPE", "" );
        }
        else {
            return enumConstant;
        }
    }

    /**
     * Determine if enum constants must be transformed for this enumType
     * @param enumType
     * @return
     */
    private boolean shouldHandle(TypeElement enumType) {
        // Enum put in another package for demo purposes only
        return enumType.getQualifiedName().toString().startsWith( "org.mapstruct.ap.test.value.spi.dto." );
    }
}
