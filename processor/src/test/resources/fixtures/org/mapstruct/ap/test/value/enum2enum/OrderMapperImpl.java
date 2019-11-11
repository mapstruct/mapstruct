/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.enum2enum;

import javax.annotation.Generated;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-02-20T21:25:45+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
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
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + orderType );
        }

        return externalOrderType;
    }

    @Override
    public OrderType externalOrderTypeToOrderType(ExternalOrderType orderType) {
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
