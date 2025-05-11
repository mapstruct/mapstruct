/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.index;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ListIndexNestedPropertyMapper {

    @Mapping(target = "manufacturer", source = "make")
    @Mapping(target = "driver", source = "personList[0]")
    @Mapping(target = "driverName", source = "source.personList[0].name")
    CarWithDriverNameDto sourceToTarget(Car source);

    @Mapping(target = "personList", ignore = true)
    @InheritInverseConfiguration
    Car inverseInherited(CarWithDriverNameDto source);

}
