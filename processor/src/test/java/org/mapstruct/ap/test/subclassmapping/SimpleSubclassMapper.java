/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import org.mapstruct.ap.test.subclassmapping.mappables.Bike;
import org.mapstruct.ap.test.subclassmapping.mappables.BikeDto;
import org.mapstruct.ap.test.subclassmapping.mappables.Car;
import org.mapstruct.ap.test.subclassmapping.mappables.CarDto;
import org.mapstruct.ap.test.subclassmapping.mappables.Vehicle;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleCollection;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleCollectionDto;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleDto;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleSubclassMapper {
    SimpleSubclassMapper INSTANCE = Mappers.getMapper( SimpleSubclassMapper.class );

    VehicleCollectionDto map(VehicleCollection vehicles);

    @SubclassMapping( source = Car.class, target = CarDto.class )
    @SubclassMapping( source = Bike.class, target = BikeDto.class )
    @Mapping( source = "vehicleManufacturingCompany", target = "maker")
    VehicleDto map(Vehicle vehicle);

    VehicleCollection mapInverse(VehicleCollectionDto vehicles);

    @InheritInverseConfiguration
    Vehicle mapInverse(VehicleDto dto);
}
