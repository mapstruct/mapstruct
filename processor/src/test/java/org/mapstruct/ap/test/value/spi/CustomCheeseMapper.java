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
public interface CustomCheeseMapper {

    CustomCheeseMapper INSTANCE = Mappers.getMapper( CustomCheeseMapper.class );

    CheeseType map(CustomCheeseType cheese);

    CustomCheeseType map(CheeseType cheese);

    String mapToString(CustomCheeseType cheeseType);

    String mapToString(CheeseType cheeseType);

    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "CUSTOM_BRIE")
    CustomCheeseType mapStringToCustom(String cheese);

    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "BRIE")
    CheeseType mapStringToCheese(String cheese);
}
