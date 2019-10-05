/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.enum2enum;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface DefaultOrderMapper {

    DefaultOrderMapper INSTANCE = Mappers.getMapper( DefaultOrderMapper.class );

    OrderDto orderEntityToDto(OrderEntity order);

    @ValueMapping( source = MappingConstants.ANY_UNMAPPED, target = "DEFAULT" )
    ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
}
