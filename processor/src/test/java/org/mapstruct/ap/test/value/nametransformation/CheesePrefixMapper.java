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
public interface CheesePrefixMapper {

    CheesePrefixMapper INSTANCE = Mappers.getMapper( CheesePrefixMapper.class );

    @EnumMapping(nameTransformationStrategy = MappingConstants.PREFIX_TRANSFORMATION, configuration = "SWISS_")
    CheeseTypePrefixed map(CheeseType cheese);

    @InheritInverseConfiguration
    @ValueMapping(source = "DEFAULT", target = MappingConstants.NULL)
    CheeseType mapInheritInverse(CheeseTypePrefixed cheese);

    @ValueMapping(source = "DEFAULT", target = MappingConstants.NULL)
    @EnumMapping(nameTransformationStrategy = MappingConstants.STRIP_PREFIX_TRANSFORMATION, configuration = "SWISS_")
    CheeseType mapStripPrefix(CheeseTypePrefixed cheese);
}
