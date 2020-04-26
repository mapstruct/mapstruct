/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.unmapped;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface UnmappedConstructorMapper {

    UnmappedConstructorMapper INSTANCE = Mappers.getMapper( UnmappedConstructorMapper.class );

    Order map(OrderDto dto);
}
