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
    date = "2026-06-12T14:19:13+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
public class OrderMapperImpl implements OrderMapper {

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
    public OrderType externalOrderTypeToOrderType(ExternalOrderType orderType) {
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
