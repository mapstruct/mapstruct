/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3360;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(
    subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION,
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    unmappedSourcePolicy = ReportingPolicy.ERROR
)
public interface Issue3360Mapper {

    Issue3360Mapper INSTANCE = Mappers.getMapper( Issue3360Mapper.class );

    @SubclassMapping(target = VehicleDto.Car.class, source = Vehicle.Car.class)
    VehicleDto map(Vehicle vehicle);

    @Mapping(target = "model", source = "modelName")
    @BeanMapping(ignoreUnmappedSourceProperties = "computedName")
    VehicleDto.Car map(Vehicle.Car car);
}
