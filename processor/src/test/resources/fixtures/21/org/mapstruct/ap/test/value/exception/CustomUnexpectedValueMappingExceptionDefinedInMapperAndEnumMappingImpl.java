/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.exception;

import javax.annotation.processing.Generated;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-12T16:23:39+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
public class CustomUnexpectedValueMappingExceptionDefinedInMapperAndEnumMappingImpl implements CustomUnexpectedValueMappingExceptionDefinedInMapperAndEnumMapping {

    @Override
    public ExternalOrderType withAnyUnmapped(OrderType orderType) {
        if ( orderType == null ) {
            return null;
        }

        ExternalOrderType externalOrderType = ExternalOrderType.DEFAULT;

        return externalOrderType;
    }

    @Override
    public ExternalOrderType withAnyRemaining(OrderType orderType) {
        if ( orderType == null ) {
            return null;
        }

        ExternalOrderType externalOrderType = switch ( orderType ) {
            case RETAIL -> ExternalOrderType.RETAIL;
            case B2B -> ExternalOrderType.B2B;
            default -> ExternalOrderType.DEFAULT;
        };

        return externalOrderType;
    }

    @Override
    public ExternalOrderType onlyWithMappings(OrderType orderType) {
        if ( orderType == null ) {
            return null;
        }

        ExternalOrderType externalOrderType = switch ( orderType ) {
            case EXTRA -> ExternalOrderType.SPECIAL;
            case STANDARD -> ExternalOrderType.DEFAULT;
            case NORMAL -> ExternalOrderType.DEFAULT;
            case RETAIL -> ExternalOrderType.RETAIL;
            case B2B -> ExternalOrderType.B2B;
        };

        return externalOrderType;
    }

    @Override
    public OrderType inverseOnlyWithMappings(ExternalOrderType orderType) {
        if ( orderType == null ) {
            return null;
        }

        OrderType orderType1 = switch ( orderType ) {
            case SPECIAL -> OrderType.EXTRA;
            case DEFAULT -> OrderType.STANDARD;
            case RETAIL -> OrderType.RETAIL;
            case B2B -> OrderType.B2B;
        };

        return orderType1;
    }
}
