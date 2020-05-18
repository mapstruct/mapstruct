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
public interface CheeseEnumToStringSuffixMapper {

    CheeseEnumToStringSuffixMapper INSTANCE = Mappers.getMapper( CheeseEnumToStringSuffixMapper.class );

    @EnumMapping(nameTransformationStrategy = MappingConstants.SUFFIX_TRANSFORMATION, configuration = "_CHEESE_TYPE")
    String map(CheeseType cheese);

    @InheritInverseConfiguration
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL)
    CheeseType map(String cheese);

    @EnumMapping(
        nameTransformationStrategy = MappingConstants.STRIP_SUFFIX_TRANSFORMATION,
        configuration = "_CHEESE_TYPE"
    )
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL)
    CheeseTypeSuffixed mapStripSuffix(String cheese);
}
