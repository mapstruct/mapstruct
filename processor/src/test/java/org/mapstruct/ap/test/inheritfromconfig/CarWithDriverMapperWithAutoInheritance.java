/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(
    config = AutoInheritedDriverConfig.class
)
public interface CarWithDriverMapperWithAutoInheritance {
    CarWithDriverMapperWithAutoInheritance INSTANCE = Mappers.getMapper( CarWithDriverMapperWithAutoInheritance.class );

    @Mapping(target = "color", source = "carDto.colour")
    CarWithDriverEntity toCarWithDriverEntity(CarDto carDto, DriverDto driverDto);
}
