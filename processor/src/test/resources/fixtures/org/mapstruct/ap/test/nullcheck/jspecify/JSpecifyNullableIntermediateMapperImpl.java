/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-16T23:54:17+0200",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
public class JSpecifyNullableIntermediateMapperImpl implements JSpecifyNullableIntermediateMapper {

    @Override
    public FlatTargetBean map(NestedSourceBean source) {
        if ( source == null ) {
            return null;
        }

        FlatTargetBean flatTargetBean = new FlatTargetBean();

        String street = sourceNullableAddressStreet( source );
        if ( street != null ) {
            flatTargetBean.setStreet( street );
        }

        return flatTargetBean;
    }

    private String sourceNullableAddressStreet(NestedSourceBean nestedSourceBean) {
        AddressBean nullableAddress = nestedSourceBean.getNullableAddress();
        if ( nullableAddress == null ) {
            return null;
        }
        return nullableAddress.getStreet();
    }
}
