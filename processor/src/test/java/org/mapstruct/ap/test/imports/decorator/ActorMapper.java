/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.decorator;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.imports.decorator.other.ActorMapperDecorator;
import org.mapstruct.factory.Mappers;

/**
 * @author Gunnar Morling
 */
@Mapper
@DecoratedWith( ActorMapperDecorator.class )
public interface ActorMapper {

    ActorMapper INSTANCE = Mappers.getMapper( ActorMapper.class );

    @Mapping( target = "famous", ignore = true )
    ActorDto actorToDto(Actor actor);
}
