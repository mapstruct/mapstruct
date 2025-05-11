/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.index;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ErroneousListIndexIsNoNumberMapper {

    @Mapping(target = "manufacturer", source = "make")
    @Mapping(target = "driver", source = "personList[x]")
    CarDto mapList(Car source);

}
