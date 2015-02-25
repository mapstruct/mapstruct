/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.nullvaluemapping;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.MapMapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.nullvaluemapping._target.CarDto;
import org.mapstruct.ap.test.nullvaluemapping._target.DriverAndCarDto;
import org.mapstruct.ap.test.nullvaluemapping.source.Car;
import org.mapstruct.ap.test.nullvaluemapping.source.Driver;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

@Mapper(imports = UUID.class)
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    @BeanMapping(nullValueMappingStrategy = RETURN_DEFAULT)
    @Mappings({
        @Mapping(target = "seatCount", source = "numberOfSeats"),
        @Mapping(target = "model", constant = "ModelT"),
        @Mapping(target = "catalogId", expression = "java( UUID.randomUUID().toString() )")
    })
    CarDto carToCarDto(Car car);

    @BeanMapping(nullValueMappingStrategy = RETURN_DEFAULT)
    @Mappings({
        @Mapping(target = "seatCount", source = "car.numberOfSeats"),
        @Mapping(target = "model", source = "model"), // TODO,  should not be needed, must be made based on name only
        @Mapping(target = "catalogId", expression = "java( UUID.randomUUID().toString() )")
    })
    CarDto carToCarDto(Car car, String model);


    @IterableMapping(nullValueMappingStrategy = RETURN_DEFAULT)
    List<CarDto> carsToCarDtos(List<Car> cars);

    @MapMapping(nullValueMappingStrategy = RETURN_DEFAULT)
    Map<Integer, CarDto> carsToCarDtoMap(Map<Integer, Car> cars);

    @BeanMapping(nullValueMappingStrategy = RETURN_DEFAULT)
    DriverAndCarDto driverAndCarToDto(Driver driver, Car car);

    DriverAndCarDto driverAndCarToDtoReturningNull(Driver driver, Car car);
}
