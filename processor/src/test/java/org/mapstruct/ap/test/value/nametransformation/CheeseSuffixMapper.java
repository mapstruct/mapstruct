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
public interface CheeseSuffixMapper {

    CheeseSuffixMapper INSTANCE = Mappers.getMapper( CheeseSuffixMapper.class );

    @EnumMapping(nameTransformationStrategy = MappingConstants.SUFFIX_TRANSFORMATION, configuration = "_CHEESE_TYPE")
    CheeseTypeSuffixed map(CheeseType cheese);

    @InheritInverseConfiguration
    @ValueMapping(source = "DEFAULT", target = MappingConstants.NULL)
    CheeseType mapInheritInverse(CheeseTypeSuffixed cheese);

    @EnumMapping(nameTransformationStrategy = MappingConstants.STRIP_SUFFIX_TRANSFORMATION, configuration = "_CHEESE_TYPE")
    @ValueMapping(source = "DEFAULT", target = MappingConstants.NULL)
    CheeseType mapStripSuffix(CheeseTypeSuffixed cheese);
}
