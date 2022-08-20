/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2839;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Hakan Özkan
 */
@Mapper
public abstract class CarMapper {

    public static final CarMapper MAPPER = Mappers.getMapper( CarMapper.class );

    public abstract Car toEntity(CarDto dto);

    protected Id mapId(String id) throws Issue2839Exception {
        throw new Issue2839Exception("For id " + id);
    }

}
