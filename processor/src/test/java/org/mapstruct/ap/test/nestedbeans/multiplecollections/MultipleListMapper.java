/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.multiplecollections;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MultipleListMapper {

    MultipleListMapper INSTANCE = Mappers.getMapper( MultipleListMapper.class );

    GarageDto convert(Garage garage);

}
