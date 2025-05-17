/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.abstractsuperclass;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomExceptionSubclassMapper {
    CustomExceptionSubclassMapper INSTANCE = Mappers.getMapper( CustomExceptionSubclassMapper.class );

    @BeanMapping(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION,
        subclassExhaustiveException = CustomSubclassMappingException.class)
    @SubclassMapping(source = Car.class, target = CarDto.class)
    @SubclassMapping(source = Bike.class, target = BikeDto.class)
    VehicleDto map(AbstractVehicle vehicle);

    VehicleCollectionDto mapInverse(VehicleCollection vehicles);
}
