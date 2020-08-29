/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.gem.MappingConstantsGem;
import org.mapstruct.ap.spi.DefaultEnumMappingStrategy;
import org.mapstruct.ap.spi.EnumMappingStrategy;
import org.mapstruct.ap.test.value.CustomIllegalArgumentException;

/**
 * @author Filip Hrisafov
 */
public class CustomEnumMappingStrategy extends DefaultEnumMappingStrategy implements EnumMappingStrategy {

    @Override
    public String getDefaultNullEnumConstant(TypeElement enumType) {
        if ( isCustomEnum( enumType ) ) {
            return "UNSPECIFIED";
        }

        return super.getDefaultNullEnumConstant( enumType );
    }

    @Override
    public String getEnumConstant(TypeElement enumType, String enumConstant) {
        if ( isCustomEnum( enumType ) ) {
            return getCustomEnumConstant( enumConstant );
        }
        return super.getEnumConstant( enumType, enumConstant );
    }

    protected String getCustomEnumConstant(String enumConstant) {
        if ( "UNRECOGNIZED".equals( enumConstant ) || "UNSPECIFIED".equals( enumConstant ) ) {
            return MappingConstantsGem.NULL;
        }

        return enumConstant.replace( "CUSTOM_", "" );
    }

    protected boolean isCustomEnum(TypeElement enumType) {
        for ( TypeMirror enumTypeInterface : enumType.getInterfaces() ) {
            if ( typeUtils.asElement( enumTypeInterface ).getSimpleName().contentEquals( "CustomEnumMarker" ) ) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected Class<? extends Exception> getDefaultExceptionClass() {
        return CustomIllegalArgumentException.class;
    }
}
