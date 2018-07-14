/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritedmappingmethod;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.inheritedmappingmethod._target.FastCarDto;
import org.mapstruct.ap.test.inheritedmappingmethod.source.FastCar;
import org.mapstruct.factory.Mappers;

//CHECKSTYLE:OFF
@Mapper
public interface FastCarMapper extends BoundMappable<FastCarDto, FastCar> {
    FastCarMapper INSTANCE = Mappers.getMapper( FastCarMapper.class );
}
// CHECKSTYLE:ON
