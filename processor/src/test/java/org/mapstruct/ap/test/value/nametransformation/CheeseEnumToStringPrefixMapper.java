/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.nametransformation;

import org.mapstruct.EnumMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface CheeseEnumToStringPrefixMapper {

    CheeseEnumToStringPrefixMapper INSTANCE = Mappers.getMapper( CheeseEnumToStringPrefixMapper.class );

    @EnumMapping(nameTransformStrategy = "prefix", configuration = "SWISS_")
    String map(CheeseType cheese);

    @InheritInverseConfiguration
    @EnumMapping(nameTransformStrategy = "prefix", configuration = "FRENCH_")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL)
    CheeseType map(String cheese);

    @EnumMapping(nameTransformStrategy = "stripPrefix", configuration = "SWISS_")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL)
    CheeseTypePrefixed mapStripPrefix(String cheese);
}
