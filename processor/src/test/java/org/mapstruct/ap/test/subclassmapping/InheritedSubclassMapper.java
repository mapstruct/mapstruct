/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.ap.test.subclassmapping.mappables.Bike;
import org.mapstruct.ap.test.subclassmapping.mappables.BikeDto;
import org.mapstruct.ap.test.subclassmapping.mappables.Car;
import org.mapstruct.ap.test.subclassmapping.mappables.CarDto;
import org.mapstruct.ap.test.subclassmapping.mappables.HatchBack;
import org.mapstruct.ap.test.subclassmapping.mappables.Vehicle;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleDto;
import org.mapstruct.factory.Mappers;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface InheritedSubclassMapper {
    InheritedSubclassMapper INSTANCE = Mappers.getMapper( InheritedSubclassMapper.class );

    @SubclassMapping( source = HatchBack.class, target = CarDto.class )
    @SubclassMapping( source = Car.class, target = CarDto.class )
    @SubclassMapping( source = Bike.class, target = BikeDto.class )
    @Mapping( source = "vehicleManufacturingCompany", target = "maker" )
    VehicleDto map(Vehicle vehicle);

    @InheritConfiguration( name = "map" )
    VehicleDto mapInherited(Vehicle dto);

    @SubclassMapping( source = Bike.class, target = CarDto.class )
    @InheritConfiguration( name = "map" )
    VehicleDto mapInheritedOverride(Vehicle dto);
}
