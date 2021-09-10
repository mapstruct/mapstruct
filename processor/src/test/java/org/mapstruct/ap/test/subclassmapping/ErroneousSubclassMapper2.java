/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.SubclassMapping;
import org.mapstruct.ap.test.subclassmapping.mappables.Car;
import org.mapstruct.ap.test.subclassmapping.mappables.CarDto;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleDto;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousSubclassMapper2 {
    ErroneousSubclassMapper2 INSTANCE = Mappers.getMapper( ErroneousSubclassMapper2.class );

    @SubclassMapping( source = Car.class, target = CarDto.class )
    @Mapping( source = "vehicleManufacturingCompany", target = "maker" )
    void map(@MappingTarget VehicleDto target, Car vehicle);
}
