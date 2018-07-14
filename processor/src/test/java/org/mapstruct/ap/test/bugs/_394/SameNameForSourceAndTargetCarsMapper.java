/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._394;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.bugs._394._target.AnotherCar;
import org.mapstruct.ap.test.bugs._394.source.Cars;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SameNameForSourceAndTargetCarsMapper {

    SameNameForSourceAndTargetCarsMapper INSTANCE = Mappers.getMapper( SameNameForSourceAndTargetCarsMapper.class );

    @Mappings({
        @Mapping(source = "numberOfSeats", target = "seatCount")
    })
    AnotherCar sourceCarToTargetCar(org.mapstruct.ap.test.bugs._394.source.AnotherCar car);

    List<AnotherCar> sourceCarListToTargetCarList(List<org.mapstruct.ap.test.bugs._394.source.AnotherCar> cars);

    org.mapstruct.ap.test.bugs._394._target.Cars sourceCarsToTargetCars(Cars source);

    // Reverse mehtods

    @InheritInverseConfiguration
    org.mapstruct.ap.test.bugs._394.source.AnotherCar targetCarToSourceCar(AnotherCar car);

    List<org.mapstruct.ap.test.bugs._394.source.AnotherCar> targetCarListToSourceCarList(List<AnotherCar> cars);

    Cars targetCarsToSourceCars(org.mapstruct.ap.test.bugs._394._target.Cars source);

}
