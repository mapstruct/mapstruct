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
    date = "2021-02-19T21:20:19+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
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

        ExternalOrderType externalOrderType;

        switch ( orderType ) {
            case STANDARD: externalOrderType = null;
            break;
            case RETAIL: externalOrderType = ExternalOrderType.RETAIL;
            break;
            case B2B: externalOrderType = ExternalOrderType.B2B;
            break;
            default: externalOrderType = ExternalOrderType.SPECIAL;
        }

        return externalOrderType;
    }

    @Override
    public ExternalOrderType orderTypeToExternalOrderTypeWithException(OrderType orderType) {
        if ( orderType == null ) {
            return null;
        }

        ExternalOrderType externalOrderType;

        switch ( orderType ) {
            case RETAIL: externalOrderType = ExternalOrderType.RETAIL;
            break;
            case B2B: externalOrderType = ExternalOrderType.B2B;
            break;
            case STANDARD: externalOrderType = ExternalOrderType.SPECIAL;
            break;
            case NORMAL: externalOrderType = ExternalOrderType.DEFAULT;
            break;
            case EXTRA: throw new IllegalArgumentException( "Unexpected enum constant: " + orderType );
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + orderType );
        }

        return externalOrderType;
    }

    @Override
    public OrderType externalOrderTypeToOrderType(ExternalOrderType orderType) {
        if ( orderType == null ) {
            return OrderType.STANDARD;
        }

        OrderType orderType1;

        switch ( orderType ) {
            case SPECIAL: orderType1 = OrderType.EXTRA;
            break;
            case DEFAULT: orderType1 = null;
            break;
            case RETAIL: orderType1 = OrderType.RETAIL;
            break;
            case B2B: orderType1 = OrderType.B2B;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + orderType );
        }

        return orderType1;
    }

    @Override
    public ExternalOrderType anyRemainingToNull(OrderType orderType) {
        if ( orderType == null ) {
            return ExternalOrderType.DEFAULT;
        }

        ExternalOrderType externalOrderType;

        switch ( orderType ) {
            case STANDARD: externalOrderType = null;
            break;
            case RETAIL: externalOrderType = ExternalOrderType.RETAIL;
            break;
            case B2B: externalOrderType = ExternalOrderType.B2B;
            break;
            default: externalOrderType = null;
        }

        return externalOrderType;
    }
}
