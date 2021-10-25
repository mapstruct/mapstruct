/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluemapping;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.nullvaluemapping._target.CarDto;
import org.mapstruct.ap.test.nullvaluemapping._target.DriverAndCarDto;
import org.mapstruct.ap.test.nullvaluemapping.source.Car;
import org.mapstruct.ap.test.nullvaluemapping.source.Driver;
import org.mapstruct.factory.Mappers;

@Mapper(imports = UUID.class)
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    @BeanMapping(nullValueMappingStrategy = RETURN_DEFAULT)
    @Mapping(target = "seatCount", source = "numberOfSeats")
    @Mapping(target = "model", constant = "ModelT")
    @Mapping(target = "catalogId", expression = "java( UUID.randomUUID().toString() )")
    CarDto carToCarDto(Car car);

    @BeanMapping(nullValueMappingStrategy = RETURN_DEFAULT)
    @Mapping(target = "seatCount", source = "car.numberOfSeats")
    @Mapping(target = "catalogId", expression = "java( UUID.randomUUID().toString() )")
    CarDto carToCarDto(Car car, String model);

    @IterableMapping(nullValueMappingStrategy = RETURN_DEFAULT)
    List<CarDto> carsToCarDtos(List<Car> cars);

    @MapMapping(nullValueMappingStrategy = RETURN_DEFAULT)
    Map<Integer, CarDto> carsToCarDtoMap(Map<Integer, Car> cars);

    @BeanMapping(nullValueMappingStrategy = RETURN_DEFAULT)
    DriverAndCarDto driverAndCarToDto(Driver driver, Car car);

    DriverAndCarDto driverAndCarToDtoReturningNull(Driver driver, Car car);
}
