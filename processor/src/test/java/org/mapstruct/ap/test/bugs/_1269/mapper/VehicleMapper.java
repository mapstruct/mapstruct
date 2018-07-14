/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1269.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.bugs._1269.dto.VehicleDto;
import org.mapstruct.ap.test.bugs._1269.model.Vehicle;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper( VehicleMapper.class );

    @Mappings({
        @Mapping(target = "vehicleInfo", source = "vehicleTypeInfo"),
        @Mapping(target = "vehicleInfo.images", source = "images")
    })
    VehicleDto map(Vehicle in);
}
