/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.enum2enum;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ValueMapping;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.factory.Mappers;

/**
 * @author Jude Niroshan
 */
@Mapper
public interface DefaultOrderThrowExceptionMapper {
    DefaultOrderThrowExceptionMapper INSTANCE = Mappers.getMapper( DefaultOrderThrowExceptionMapper.class );

    @Named("orderTypeToExternalOrderTypeAnyUnmappedToException")
    @ValueMapping(source = MappingConstants.ANY_UNMAPPED, target = MappingConstants.THROW_EXCEPTION)
    ExternalOrderType orderTypeToExternalOrderTypeAnyUnmappedToException(OrderType orderType);
}
