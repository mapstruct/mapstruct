/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubClassMapping;
import org.mapstruct.ap.test.subclassmapping.mappables.Bike;
import org.mapstruct.ap.test.subclassmapping.mappables.BikeDto;
import org.mapstruct.ap.test.subclassmapping.mappables.Car;
import org.mapstruct.ap.test.subclassmapping.mappables.CarDto;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousSubClassMapper1 {
    ErroneousSubClassMapper1 INSTANCE = Mappers.getMapper( ErroneousSubClassMapper1.class );

    @SubClassMapping( source = Bike.class, target = BikeDto.class )
    @Mapping( target = "maker", ignore = true )
    CarDto map(Car vehicle);
}
