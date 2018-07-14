/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.jaxb;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.selection.jaxb.test1.ObjectFactory;
import org.mapstruct.ap.test.selection.jaxb.test1.OrderType;
import org.mapstruct.ap.test.selection.jaxb.test2.OrderShippingDetailsType;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(uses = {
    ObjectFactory.class,
    org.mapstruct.ap.test.selection.jaxb.test2.ObjectFactory.class
})
public abstract class OrderMapper {

    public static final OrderMapper INSTANCE = Mappers.getMapper( OrderMapper.class );

    // target 2 source methods
    public abstract OrderType targetToSource(OrderDto target);

    public abstract OrderShippingDetailsType dtoToOrderShippingDetailsType(OrderShippingDetailsDto target);

}
