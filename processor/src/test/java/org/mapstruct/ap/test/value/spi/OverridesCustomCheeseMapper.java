/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface OverridesCustomCheeseMapper {

    OverridesCustomCheeseMapper INSTANCE = Mappers.getMapper( OverridesCustomCheeseMapper.class );

    @ValueMapping(source = "CUSTOM_BRIE", target = "ROQUEFORT")
    @ValueMapping(source = MappingConstants.NULL, target = "ROQUEFORT")
    CheeseType map(CustomCheeseType cheese);

    @ValueMapping(source = "BRIE", target = "CUSTOM_ROQUEFORT")
    @ValueMapping(source = MappingConstants.NULL, target = "CUSTOM_ROQUEFORT")
    CustomCheeseType map(CheeseType cheese);

    @ValueMapping(source = "CUSTOM_ROQUEFORT", target = "BRIE")
    @ValueMapping(source = MappingConstants.NULL, target = "ROQUEFORT")
    String mapToString(CustomCheeseType cheeseType);

    @ValueMapping(source = "ROQUEFORT", target = "BRIE")
    String mapToString(CheeseType cheeseType);

    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "CUSTOM_BRIE")
    @ValueMapping(source = "BRIE", target = "CUSTOM_ROQUEFORT")
    @ValueMapping(source = MappingConstants.NULL, target = "CUSTOM_ROQUEFORT")
    CustomCheeseType mapStringToCustom(String cheese);

    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "BRIE")
    @ValueMapping(source = "BRIE", target = "ROQUEFORT")
    @ValueMapping(source = MappingConstants.NULL, target = "ROQUEFORT")
    CheeseType mapStringToCheese(String cheese);
}
