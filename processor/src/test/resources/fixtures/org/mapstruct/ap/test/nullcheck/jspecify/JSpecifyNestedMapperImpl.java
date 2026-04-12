/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-12T21:28:54+0200",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
public class JSpecifyNestedMapperImpl implements JSpecifyNestedMapper {

    @Override
    public FlatTargetBean map(NestedSourceBean source) {
        if ( source == null ) {
            return null;
        }

        FlatTargetBean flatTargetBean = new FlatTargetBean();

        flatTargetBean.setStreet( sourceNonNullAddressStreet( source ) );
        String city = sourceNonNullAddressCity( source );
        if ( city != null ) {
            flatTargetBean.setCity( city );
        }
        flatTargetBean.setNullableStreet( sourceNonNullAddressStreet( source ) );

        return flatTargetBean;
    }

    private String sourceNonNullAddressStreet(NestedSourceBean nestedSourceBean) {
        AddressBean nonNullAddress = nestedSourceBean.getNonNullAddress();
        return nonNullAddress.getStreet();
    }

    private String sourceNonNullAddressCity(NestedSourceBean nestedSourceBean) {
        AddressBean nonNullAddress = nestedSourceBean.getNonNullAddress();
        return nonNullAddress.getCity();
    }
}
