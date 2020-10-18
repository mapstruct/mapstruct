/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2236;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    @Mapping(target = "ownerA.name", source = "carDto.ownerNameA")
    @Mapping(target = "ownerA.city", source = "carDto.ownerCityA")
    @Mapping(target = "ownerB.name", source = "carDto.ownerNameB")
    @Mapping(target = "ownerB.city", source = "carDto.ownerCityB")
    @Mapping(target = "name", source = "carDto.name")
    @Mapping(target = "type", source = "type")
    Car vehicleToCar(Vehicle vehicle);
}
