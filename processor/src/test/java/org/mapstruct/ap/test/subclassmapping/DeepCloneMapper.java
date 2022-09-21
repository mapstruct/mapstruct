/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;
import org.mapstruct.ap.test.subclassmapping.mappables.Bike;
import org.mapstruct.ap.test.subclassmapping.mappables.Car;
import org.mapstruct.ap.test.subclassmapping.mappables.Vehicle;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

@Mapper(mappingControl = DeepClone.class)
public interface DeepCloneMapper {
    DeepCloneMapper INSTANCE = Mappers.getMapper( DeepCloneMapper.class );

    @SubclassMapping( source = Car.class, target = Car.class )
    @SubclassMapping( source = Bike.class, target = Bike.class )
    Vehicle map(Vehicle vehicle);
}
