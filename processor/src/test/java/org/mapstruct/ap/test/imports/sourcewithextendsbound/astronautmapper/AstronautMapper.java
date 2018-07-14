/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.sourcewithextendsbound.astronautmapper;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.dto.AstronautDto;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.entity.Astronaut;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AstronautMapper {

    AstronautMapper INSTANCE = Mappers.getMapper( AstronautMapper.class );

    AstronautDto astronautToDto(Astronaut astronaut);
}
