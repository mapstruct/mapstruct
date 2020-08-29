/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.exception;

import javax.annotation.Generated;
import org.mapstruct.ap.test.value.CustomIllegalArgumentException;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-08-29T09:36:57+0200",
    comments = "version: , compiler: javac, environment: Java 11.0.2 (AdoptOpenJDK)"
)
public class CustomDefaultExceptionDefinedInMapperAndEnumMappingImpl implements CustomDefaultExceptionDefinedInMapperAndEnumMapping {

    @Override
    public ExternalOrderType withAnyUnmapped(OrderType orderType) {
        if ( orderType == null ) {
            return null;
        }

        ExternalOrderType externalOrderType;

        switch ( orderType ) {
            default: externalOrderType = ExternalOrderType.DEFAULT;
        }

        return externalOrderType;
    }

    @Override
    public ExternalOrderType withAnyRemaining(OrderType orderType) {
        if ( orderType == null ) {
            return null;
        }

        ExternalOrderType externalOrderType;

        switch ( orderType ) {
            case RETAIL: externalOrderType = ExternalOrderType.RETAIL;
            break;
            case B2B: externalOrderType = ExternalOrderType.B2B;
            break;
            default: externalOrderType = ExternalOrderType.DEFAULT;
        }

        return externalOrderType;
    }

    @Override
    public ExternalOrderType onlyWithMappings(OrderType orderType) {
        if ( orderType == null ) {
            return null;
        }

        ExternalOrderType externalOrderType;

        switch ( orderType ) {
            case EXTRA: externalOrderType = ExternalOrderType.SPECIAL;
            break;
            case STANDARD: externalOrderType = ExternalOrderType.DEFAULT;
            break;
            case NORMAL: externalOrderType = ExternalOrderType.DEFAULT;
            break;
            case RETAIL: externalOrderType = ExternalOrderType.RETAIL;
            break;
            case B2B: externalOrderType = ExternalOrderType.B2B;
            break;
            default: throw new CustomIllegalArgumentException( "Unexpected enum constant: " + orderType );
        }

        return externalOrderType;
    }

    @Override
    public OrderType inverseOnlyWithMappings(ExternalOrderType orderType) {
        if ( orderType == null ) {
            return null;
        }

        OrderType orderType1;

        switch ( orderType ) {
            case SPECIAL: orderType1 = OrderType.EXTRA;
            break;
            case DEFAULT: orderType1 = OrderType.STANDARD;
            break;
            case RETAIL: orderType1 = OrderType.RETAIL;
            break;
            case B2B: orderType1 = OrderType.B2B;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + orderType );
        }

        return orderType1;
    }
}
