/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import org.mapstruct.ap.test.subclassmapping.mappables.Bike;
import org.mapstruct.ap.test.subclassmapping.mappables.Car;
import org.mapstruct.ap.test.subclassmapping.mappables.Vehicle;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleDto;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousInverseSubclassMapper {
    ErroneousInverseSubclassMapper INSTANCE = Mappers.getMapper( ErroneousInverseSubclassMapper.class );

    @SubclassMapping( source = Bike.class, target = VehicleDto.class )
    @SubclassMapping( source = Car.class, target = VehicleDto.class )
    @Mapping( target = "maker", source = "vehicleManufacturingCompany" )
    VehicleDto map(Vehicle vehicle);

    @InheritInverseConfiguration
    Vehicle mapInverse(VehicleDto dto);
}
