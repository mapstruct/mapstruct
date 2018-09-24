/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.strategy;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(config = HouseMapperConfig.class)
public interface HouseMapperWithConfig {

    HouseMapperWithConfig INSTANCE = Mappers.getMapper( HouseMapperWithConfig.class );

    HouseEntity mapWithNvcsOnMapper(HouseDto in);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
    HouseEntity mapWithNvcsOnBean(HouseDto in);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
    @Mapping(target = "number", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    HouseEntity mapWithNvcsOnMapping(HouseDto in);

}
