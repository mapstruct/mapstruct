/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3331;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface Issue3331Mapper {

    Issue3331Mapper INSTANCE = Mappers.getMapper( Issue3331Mapper.class );

    @SubclassMapping(source = Vehicle.Car.class, target = VehicleDto.Car.class)
    @SubclassMapping(source = Vehicle.Motorbike.class, target = VehicleDto.Motorbike.class)
    @Mapping(target = "name", constant = "noname")
    VehicleDto map(Vehicle vehicle);

}
