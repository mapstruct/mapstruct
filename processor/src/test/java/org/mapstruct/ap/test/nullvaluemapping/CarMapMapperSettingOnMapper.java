/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluemapping;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ap.test.nullvaluemapping._target.CarDto;
import org.mapstruct.ap.test.nullvaluemapping.source.Car;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface CarMapMapperSettingOnMapper {

    CarMapMapperSettingOnMapper INSTANCE = Mappers.getMapper( CarMapMapperSettingOnMapper.class );

    @Mapping(target = "seatCount", ignore = true)
    @Mapping(target = "model", ignore = true)
    @Mapping(target = "catalogId", ignore = true)
    CarDto map(Car car);

    Map<Integer, CarDto> carsToCarDtoMap(Map<Integer, Car> cars);
}
