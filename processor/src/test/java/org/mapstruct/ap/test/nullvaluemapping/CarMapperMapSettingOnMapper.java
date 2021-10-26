/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluemapping;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.mapstruct.IterableMapping;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ap.test.nullvaluemapping._target.CarDto;
import org.mapstruct.ap.test.nullvaluemapping.source.Car;
import org.mapstruct.factory.Mappers;

@Mapper(
    imports = UUID.class,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface CarMapperMapSettingOnMapper {

    CarMapperMapSettingOnMapper INSTANCE = Mappers.getMapper( CarMapperMapSettingOnMapper.class );

    @Mapping(target = "seatCount", source = "numberOfSeats")
    @Mapping(target = "model", constant = "ModelT")
    @Mapping(target = "catalogId", expression = "java( UUID.randomUUID().toString() )")
    CarDto carToCarDto(Car car);

    @IterableMapping(dateFormat = "dummy")
    List<CarDto> carsToCarDtos(List<Car> cars);

    @MapMapping(valueDateFormat = "dummy")
    Map<Integer, CarDto> carsToCarDtoMap(Map<Integer, Car> cars);
}
