/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluemapping;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.nullvaluemapping._target.CarDto;
import org.mapstruct.ap.test.nullvaluemapping.source.Car;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface CarListMapper {

    CarListMapper INSTANCE = Mappers.getMapper( CarListMapper.class );

    @Mapping(target = "seatCount", ignore = true)
    @Mapping(target = "model", ignore = true)
    @Mapping(target = "catalogId", ignore = true)
    CarDto map(Car car);

    List<CarDto> carsToCarDtoList(List<Car> cars);
}
