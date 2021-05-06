/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.nametransformation;

import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author jpbassinello
 */
@Mapper
public interface CheeseCaseMapper {

    CheeseCaseMapper INSTANCE = Mappers.getMapper( CheeseCaseMapper.class );

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    CheeseTypeLower mapToLower(CheeseType cheese);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    CheeseTypeLower mapToLower(CheeseTypeCapital cheese);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    CheeseType mapToUpper(CheeseTypeLower cheese);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    CheeseType mapToUpper(CheeseTypeCapital cheese);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "capital")
    CheeseTypeCapital mapToCapital(CheeseTypeLower cheese);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "capital")
    CheeseTypeCapital mapToCapital(CheeseType cheese);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    String mapToLowerString(CheeseType cheese);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    String mapToUpperString(CheeseType cheese);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "capital")
    String mapToCapitalString(CheeseType cheese);
}
