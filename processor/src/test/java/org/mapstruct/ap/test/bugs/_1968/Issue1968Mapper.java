/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1968;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue1968Mapper {

    Issue1968Mapper INSTANCE = Mappers.getMapper( Issue1968Mapper.class );

    @Mapping(target = "specialNames", source = "otherNames")
    Animal dtoToEntity(AnimalDto dto);
}
