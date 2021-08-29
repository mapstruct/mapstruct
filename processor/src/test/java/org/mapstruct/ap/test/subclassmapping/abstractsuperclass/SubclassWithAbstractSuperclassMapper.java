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
public interface SubclassWithAbstractSuperclassMapper {
    SubclassWithAbstractSuperclassMapper INSTANCE = Mappers.getMapper( SubclassWithAbstractSuperclassMapper.class );

    org.mapstruct.ap.test.subclassmapping.abstractsuperclass.VehicleCollectionDto
                    map(org.mapstruct.ap.test.subclassmapping.abstractsuperclass.VehicleCollection vehicles);

    org.mapstruct.ap.test.subclassmapping.abstractsuperclass.CarDto map(org.mapstruct.ap.test.subclassmapping.abstractsuperclass.Car car);

    org.mapstruct.ap.test.subclassmapping.abstractsuperclass.BikeDto map(org.mapstruct.ap.test.subclassmapping.abstractsuperclass.Bike bike);

    @SubClassMapping( sourceClass = org.mapstruct.ap.test.subclassmapping.abstractsuperclass.Car.class,
                    targetClass = org.mapstruct.ap.test.subclassmapping.abstractsuperclass.CarDto.class )
    @SubClassMapping( sourceClass = org.mapstruct.ap.test.subclassmapping.abstractsuperclass.Bike.class,
                    targetClass = org.mapstruct.ap.test.subclassmapping.abstractsuperclass.BikeDto.class )
    org.mapstruct.ap.test.subclassmapping.abstractsuperclass.VehicleDto map(org.mapstruct.ap.test.subclassmapping.abstractsuperclass.AbstractVehicle vehicle);
}
