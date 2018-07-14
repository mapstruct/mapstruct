/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritedmappingmethod;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.inheritedmappingmethod._target.CarDto;
import org.mapstruct.ap.test.inheritedmappingmethod.source.Car;
import org.mapstruct.factory.Mappers;

//CHECKSTYLE:OFF
@Mapper
public interface CarMapper extends UnboundMappable<CarDto, Car> {
    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );
}
// CHECKSTYLE:ON
