/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.abstractsuperclass;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper( subclassExhaustiveStrategy = SubclassExhaustiveStrategy.COMPILE_ERROR )
public interface ErroneousSubclassWithAbstractSuperClassMapper {
    ErroneousSubclassWithAbstractSuperClassMapper INSTANCE =
        Mappers.getMapper( ErroneousSubclassWithAbstractSuperClassMapper.class );

    VehicleCollectionDto map(VehicleCollection vehicles);

    CarDto map(Car car);

    BikeDto map(Bike bike);

    @SubclassMapping( source = Car.class, target = CarDto.class )
    @SubclassMapping( source = Bike.class, target = BikeDto.class )
    VehicleDto map(AbstractVehicle vehicle);
}
