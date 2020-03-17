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
    public String getEnumValue(TypeElement enumType, String sourceEnumValue) {
        if ( enumType.getQualifiedName().toString().startsWith( "org.mapstruct.ap.test.value.spi.dto." ) ) {
            return sourceEnumValue.replace( "_CHEESE_TYPE", "" );
        }
        else {
            return sourceEnumValue;


        }
    }
}
