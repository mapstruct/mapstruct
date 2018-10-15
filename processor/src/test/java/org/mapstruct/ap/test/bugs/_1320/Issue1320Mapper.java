/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1320;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1320Mapper {

    Issue1320Mapper INSTANCE = Mappers.getMapper( Issue1320Mapper.class );

    @Mappings({
        @Mapping(target = "address.city.cityName", constant = "myCity"),
        @Mapping(target = "address.city.stateName", constant = "myState")
    })
    Target map(Integer dummy);
}
