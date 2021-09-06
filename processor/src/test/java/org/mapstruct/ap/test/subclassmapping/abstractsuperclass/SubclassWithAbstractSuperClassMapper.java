/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.abstractsuperclass;

import org.mapstruct.Mapper;
import org.mapstruct.SubClassMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubclassWithAbstractSuperClassMapper {
    SubclassWithAbstractSuperClassMapper INSTANCE = Mappers.getMapper( SubclassWithAbstractSuperClassMapper.class );

    VehicleCollectionDto map(VehicleCollection vehicles);

    CarDto map(Car car);

    BikeDto map(Bike bike);

    @SubClassMapping( source = Car.class, target = CarDto.class )
    @SubClassMapping( source = Bike.class, target = BikeDto.class )
    VehicleDto map(AbstractVehicle vehicle);
}
