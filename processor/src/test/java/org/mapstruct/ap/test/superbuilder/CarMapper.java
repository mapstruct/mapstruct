/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    @Mapping(target = "amountOfTires", source = "tireCount")
    Car carDtoToCar(CarDto source);

    @InheritInverseConfiguration(name = "carDtoToCar")
    CarDto carToCarDto(Car source);

    @Mapping(target = "amountOfTires", source = "tireCount")
    ChainedAccessorsCar carDtoToChainedAccessorsCar(CarDto source);

    @InheritInverseConfiguration(name = "carDtoToChainedAccessorsCar")
    CarDto chainedAccessorsCarToCarDto(ChainedAccessorsCar source);

    @Mapping(target = "amountOfTires", source = "tireCount")
    InheritedAbstractCar carDtoToInheritedAbstractCar(CarDto source);

    @InheritInverseConfiguration(name = "carDtoToInheritedAbstractCar")
    CarDto inheritedAbstractCarToCarDto(InheritedAbstractCar source);

    @Mapping(target = "amountOfTires", source = "tireCount")
    @Mapping(target = "horsePower", constant = "140.5f")
    MuscleCar carDtoToMuscleCar(CarDto source);

    @InheritInverseConfiguration(name = "carDtoToMuscleCar")
    CarDto muscleCarToCarDto(MuscleCar source);

}
