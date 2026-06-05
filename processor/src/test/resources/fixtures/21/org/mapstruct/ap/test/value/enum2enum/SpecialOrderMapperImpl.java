/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.enum2enum;

import javax.annotation.processing.Generated;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-12T16:23:42+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
public class SpecialOrderMapperImpl implements SpecialOrderMapper {

    @Override
    public OrderDto orderEntityToDto(OrderEntity order) {
        if ( order == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        orderDto.setOrderType( orderTypeToExternalOrderType( order.getOrderType() ) );

        return orderDto;
    }

    @Override
    public ExternalOrderType orderTypeToExternalOrderType(OrderType orderType) {
        if ( orderType == null ) {
            return ExternalOrderType.DEFAULT;
        }

        ExternalOrderType externalOrderType = switch ( orderType ) {
            case STANDARD -> null;
            case RETAIL -> ExternalOrderType.RETAIL;
            case B2B -> ExternalOrderType.B2B;
            default -> ExternalOrderType.SPECIAL;
        };

        return externalOrderType;
    }

    @Override
    public OrderType externalOrderTypeToOrderType(ExternalOrderType orderType) {
        if ( orderType == null ) {
            return OrderType.STANDARD;
        }

        OrderType orderType1 = switch ( orderType ) {
            case SPECIAL -> OrderType.EXTRA;
            case DEFAULT -> null;
            case RETAIL -> OrderType.RETAIL;
            case B2B -> OrderType.B2B;
        };

        return orderType1;
    }

    @Override
    public ExternalOrderType anyRemainingToNull(OrderType orderType) {
        if ( orderType == null ) {
            return ExternalOrderType.DEFAULT;
        }

        ExternalOrderType externalOrderType = switch ( orderType ) {
            case STANDARD -> null;
            case RETAIL -> ExternalOrderType.RETAIL;
            case B2B -> ExternalOrderType.B2B;
            default -> null;
        };

        return externalOrderType;
    }
}
