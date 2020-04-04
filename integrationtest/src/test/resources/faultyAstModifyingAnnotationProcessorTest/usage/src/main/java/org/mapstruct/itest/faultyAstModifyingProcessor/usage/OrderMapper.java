/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.faultyAstModifyingProcessor.usage;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper( OrderMapper.class );

    OrderDto orderToDto(Order order);
}
