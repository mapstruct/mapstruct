/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.ap.test.subclassmapping.mappables.Bike;
import org.mapstruct.ap.test.subclassmapping.mappables.Car;
import org.mapstruct.ap.test.subclassmapping.mappables.Vehicle;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeepCloneMethodMapper {
    DeepCloneMethodMapper INSTANCE = Mappers.getMapper( DeepCloneMethodMapper.class );

    @SubclassMapping( source = Car.class, target = Car.class )
    @SubclassMapping( source = Bike.class, target = Bike.class )
    @BeanMapping( mappingControl = DeepClone.class, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL )
    Vehicle map(Vehicle vehicle);
}
