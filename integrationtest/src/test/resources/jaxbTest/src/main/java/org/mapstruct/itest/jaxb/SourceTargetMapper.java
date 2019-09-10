/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.jaxb;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.itest.jaxb.xsd.test1.OrderDetailsType;
import org.mapstruct.itest.jaxb.xsd.test1.OrderType;
import org.mapstruct.itest.jaxb.xsd.test2.OrderStatusType;
import org.mapstruct.itest.jaxb.xsd.test2.ShippingAddressType;
import org.mapstruct.itest.jaxb.xsd.underscores.SubType;


/**
 * @author Sjaak Derksen
 */
@Mapper(uses = {
    org.mapstruct.itest.jaxb.xsd.test1.ObjectFactory.class,
    org.mapstruct.itest.jaxb.xsd.test2.ObjectFactory.class,
    org.mapstruct.itest.jaxb.xsd.underscores.ObjectFactory.class
})
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    // source 2 target methods
    OrderDto sourceToTarget(OrderType source);

    OrderDetailsDto detailsToDto(OrderDetailsType source);

    OrderStatusDto statusToDto(OrderStatusType source);

    ShippingAddressDto shippingAddressToDto(ShippingAddressType source);

    SubTypeDto subTypeToDto(SubType source);

    // target 2 source methods
    OrderType targetToSource(OrderDto target);

    OrderDetailsType dtoToDetails(OrderDetailsDto target);

    OrderStatusType dtoToStatus(OrderStatusDto target);

    ShippingAddressType dtoToShippingAddress(ShippingAddressDto source);

    SubType dtoToSubType(SubTypeDto source);
}
