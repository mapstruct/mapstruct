/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.value;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-02-20T21:25:45+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
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
