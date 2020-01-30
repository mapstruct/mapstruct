/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DirectMapper {

    DirectMapper INSTANCE = Mappers.getMapper( DirectMapper.class );

    @Mapping(target = "shelve", source = "shelve")
    FridgeDTO map(FridgeDTO in);

}
