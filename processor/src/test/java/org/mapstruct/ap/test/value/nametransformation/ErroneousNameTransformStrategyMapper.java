/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.nametransformation;

import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousNameTransformStrategyMapper {

    ErroneousNameTransformStrategyMapper INSTANCE = Mappers.getMapper( ErroneousNameTransformStrategyMapper.class );

    @EnumMapping(nameTransformStrategy = "custom", configuration = "_TYPE")
    CheeseTypeCustomSuffix map(CheeseType cheese);
}
