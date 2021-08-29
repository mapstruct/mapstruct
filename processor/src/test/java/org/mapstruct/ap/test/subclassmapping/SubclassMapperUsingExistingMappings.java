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
public interface SubclassMapperUsingExistingMappings {
    SubclassMapperUsingExistingMappings INSTANCE = Mappers.getMapper( SubclassMapperUsingExistingMappings.class );

    VehicleCollectionDto map(VehicleCollection vehicles);

    default CarDto existingMappingMethod(Car domein) {
        CarDto dto = new CarDto();
        dto.setName( "created through existing mapping." );
        return dto;
    }

    @SubClassMapping( sourceClass = Car.class, targetClass = CarDto.class )
    @SubClassMapping( sourceClass = Bike.class, targetClass = BikeDto.class )
    VehicleDto map(Vehicle vehicle);
}
