/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.Mapper;
import org.mapstruct.SubClassMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleSubclassMapper {
    SimpleSubclassMapper INSTANCE = Mappers.getMapper( SimpleSubclassMapper.class );

    VehicleCollectionDto map(VehicleCollection vehicles);

    @SubClassMapping( sourceClass = Car.class, targetClass = CarDto.class )
    @SubClassMapping( sourceClass = Bike.class, targetClass = BikeDto.class )
    VehicleDto map(Vehicle vehicle);
}
