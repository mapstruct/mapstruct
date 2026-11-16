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
    date = "2026-06-05T22:27:58+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
public class DefaultOrderMapperImpl implements DefaultOrderMapper {

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

        ExternalOrderType externalOrderType = ExternalOrderType.DEFAULT;

        return externalOrderType;
    }
}