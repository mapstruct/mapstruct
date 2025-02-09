/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.index;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ListIndexMapper {

    @Mapping(target = "manufacturer", source = "make")
    @Mapping(target = "driver", source = "personList[0]")
    CarDto mapList(Car source);

    @Mapping(target = "manufacturer", source = "make")
    @Mapping(target = "driver", source = "personList[0]")
    void updateList(Car source, @MappingTarget CarDto target);

    @Mapping(target = "manufacturer", source = "make")
    @Mapping(target = "driver", source = "personList[0]")
    CarDto mapArrayList(CarWithArrayList source);

}
