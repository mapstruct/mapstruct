/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import org.mapstruct.ap.test.subclassmapping.mappables.Bike;
import org.mapstruct.ap.test.subclassmapping.mappables.BikeDto;
import org.mapstruct.ap.test.subclassmapping.mappables.Car;
import org.mapstruct.ap.test.subclassmapping.mappables.CarDto;
import org.mapstruct.ap.test.subclassmapping.mappables.Vehicle;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleDto;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubclassIgnoreByDefaultMapper {
    SubclassIgnoreByDefaultMapper INSTANCE = Mappers.getMapper( SubclassIgnoreByDefaultMapper.class );

    @BeanMapping(ignoreByDefault = true)
    @SubclassMapping(source = Car.class, target = CarDto.class)
    @SubclassMapping(source = Bike.class, target = BikeDto.class)
    @Mapping(target = "name")
    VehicleDto map(Vehicle vehicle);
}
