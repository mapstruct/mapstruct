/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.spi.EnumValueMappingStrategy;

public class CustomEnumValueMappingStrategy implements EnumValueMappingStrategy {
    @Override
    public boolean isMapToNull(TypeElement enumType, String enumConstant) {
        // If enum is of a type where transformation should happen, check if this value
        // is listed as a null mapping
        if ( shoudHandle( enumType ) && "DEFAULT_CHEESE_TYPE".equals( enumConstant ) ) {
            return true;
        }
        return false;
    }

    @Override
    public String getDefaultEnumValue(TypeElement enumType) {
        if ( shoudHandle( enumType ) ) {
            return "DEFAULT_CHEESE_TYPE";
        }
        else {
            return null;
        }
    }

    @Override
    public String getEnumValue(TypeElement enumType, String enumConstant) {
        if ( shoudHandle( enumType ) ) {
            return enumConstant.replace( "_CHEESE_TYPE", "" );
        }
        else {
            return enumConstant;
        }
    }

    private boolean shoudHandle(TypeElement enumType) {
        return enumType.getQualifiedName().toString().startsWith( "org.mapstruct.ap.test.value.spi.dto." );
    }
}
