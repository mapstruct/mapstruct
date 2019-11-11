/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.enum2enum;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface SpecialOrderMapper {

    SpecialOrderMapper INSTANCE = Mappers.getMapper( SpecialOrderMapper.class );

    @Mapping(target = "orderType", source = "orderType", qualifiedByName = "orderTypeToExternalOrderType")
    OrderDto orderEntityToDto(OrderEntity order);

    @Named("orderTypeToExternalOrderType")
    @ValueMappings({
        @ValueMapping( source = MappingConstants.NULL, target = "DEFAULT" ),
        @ValueMapping( source = "STANDARD", target = MappingConstants.NULL ),
        @ValueMapping( source = MappingConstants.ANY_REMAINING, target = "SPECIAL" )
    })
    ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);

    @InheritInverseConfiguration(name = "orderTypeToExternalOrderType")
    @ValueMapping( target = "EXTRA", source = "SPECIAL" )
    OrderType externalOrderTypeToOrderType(ExternalOrderType orderType);

    @ValueMappings({
        @ValueMapping(source = MappingConstants.NULL, target = "DEFAULT"),
        @ValueMapping(source = "STANDARD", target = MappingConstants.NULL),
        @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL)
    })
    ExternalOrderType anyRemainingToNull(OrderType orderType);
}
