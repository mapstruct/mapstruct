/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubClassMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleSubclassMapper {
    SimpleSubclassMapper INSTANCE = Mappers.getMapper( SimpleSubclassMapper.class );

    VehicleCollectionDto map(VehicleCollection vehicles);

    @SubClassMapping( source = Car.class, target = CarDto.class )
    @SubClassMapping( source = Bike.class, target = BikeDto.class )
    @Mapping( source = "vehicleManufacturingCompany", target = "maker")
    VehicleDto map(Vehicle vehicle);
}
