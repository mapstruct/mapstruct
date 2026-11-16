/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.sealedsubclass;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SealedSubclassMapper {
    SealedSubclassMapper INSTANCE = Mappers.getMapper( SealedSubclassMapper.class );

    VehicleCollectionDto map(VehicleCollection vehicles);

    @SubclassMapping( source = Car.class, target = CarDto.class )
    @SubclassMapping( source = Bike.class, target = BikeDto.class )
    @SubclassMapping( source = Harley.class, target = HarleyDto.class )
    @SubclassMapping( source = Davidson.class, target = DavidsonDto.class )
    @Mapping( source = "vehicleManufacturingCompany", target = "maker")
    VehicleDto map(Vehicle vehicle);

    VehicleCollection mapInverse(VehicleCollectionDto vehicles);

    @InheritInverseConfiguration
    Vehicle mapInverse(VehicleDto dto);
}
