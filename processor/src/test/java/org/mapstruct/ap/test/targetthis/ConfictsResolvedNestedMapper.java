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
public interface ConfictsResolvedNestedMapper {

    ConfictsResolvedNestedMapper INSTANCE = Mappers.getMapper( ConfictsResolvedNestedMapper.class );

    @Mapping( target = "id", source = "customer.item.id" )
    @Mapping( target = ".", source = "customer.item" )
    @Mapping( target = "status", source = "item.status" )
    @Mapping( target = ".", source = "item" )
    OrderItem map(OrderDTO order);

}
