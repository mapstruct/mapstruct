/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import org.mapstruct.ap.test.subclassmapping.mappables.Bike;
import org.mapstruct.ap.test.subclassmapping.mappables.BikeDto;
import org.mapstruct.ap.test.subclassmapping.mappables.Car;
import org.mapstruct.ap.test.subclassmapping.mappables.CarDto;

@SubclassMapping( source = Car.class, target = CarDto.class )
@SubclassMapping( source = Bike.class, target = BikeDto.class )
@Mapping( source = "vehicleManufacturingCompany", target = "maker")
public @interface CompositeSubclassMappingAnnotation {
}
