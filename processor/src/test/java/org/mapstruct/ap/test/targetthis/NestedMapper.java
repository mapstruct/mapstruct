/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NestedMapper {

    NestedMapper INSTANCE = Mappers.getMapper( NestedMapper.class );

    @Mapping( target = ".", source = "customer.item" )
    OrderItem map(OrderDTO order);

}
