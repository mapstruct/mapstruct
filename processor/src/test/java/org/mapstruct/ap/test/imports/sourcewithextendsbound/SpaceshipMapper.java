/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.sourcewithextendsbound;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.astronautmapper.AstronautMapper;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.dto.SpaceshipDto;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.entity.Spaceship;
import org.mapstruct.factory.Mappers;

@Mapper(uses = AstronautMapper.class)
public interface SpaceshipMapper {

    SpaceshipMapper INSTANCE = Mappers.getMapper( SpaceshipMapper.class );

    SpaceshipDto spaceshipToDto(Spaceship spaceship);
}
