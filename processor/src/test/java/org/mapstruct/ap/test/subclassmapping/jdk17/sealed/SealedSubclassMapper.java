/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.jdk17.sealed;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SealedSubclassMapper {
    SealedSubclassMapper INSTANCE = Mappers.getMapper( SealedSubclassMapper.class );

    VehicleCollectionDto map(VehicleCollection vehicles);

    @SubclassMapping( source = Vehicle.Car.class, target = VehicleDto.CarDto.class )
    @SubclassMapping( source = Vehicle.Bike.class, target = VehicleDto.BikeDto.class )
    @SubclassMapping( source = Motor.Harley.class, target = MotorDto.HarleyDto.class )
    @SubclassMapping( source = Motor.Davidson.class, target = MotorDto.DavidsonDto.class )
    @Mapping( source = "vehicleManufacturingCompany", target = "maker")
    VehicleDto map(Vehicle vehicle);

    VehicleCollection mapInverse(VehicleCollectionDto vehicles);

    @InheritInverseConfiguration
    Vehicle mapInverse(VehicleDto dto);
}
