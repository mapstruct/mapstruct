/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.string2enum;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper( OrderMapper.class );

    @ValueMappings({
        @ValueMapping(source = "SPECIAL", target = "EXTRA" ),
        @ValueMapping(source = "DEFAULT", target = "STANDARD")
    })
    OrderType mapNormal(String orderType);

    @ValueMappings({
        @ValueMapping( source = MappingConstants.NULL, target = "STANDARD" ),
        @ValueMapping( source = "DEFAULT", target = MappingConstants.NULL ),
        @ValueMapping( source = MappingConstants.ANY_UNMAPPED, target = "RETAIL" )
    })
    OrderType mapWithAnyUnmapped(String orderType);
}
