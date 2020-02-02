/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(mappingControl = UseComplex.class)
public interface ErroneousDirectMapper {

    ErroneousDirectMapper INSTANCE = Mappers.getMapper( ErroneousDirectMapper.class );

    @Mapping(target = "shelve", source = "shelve")
    FridgeDTO map(FridgeDTO in);

}
