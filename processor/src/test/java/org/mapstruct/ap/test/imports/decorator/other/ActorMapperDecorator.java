/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.decorator.other;

import org.mapstruct.ap.test.imports.decorator.Actor;
import org.mapstruct.ap.test.imports.decorator.ActorDto;
import org.mapstruct.ap.test.imports.decorator.ActorMapper;

/**
 * @author Gunnar Morling
 */
public class ActorMapperDecorator implements ActorMapper {

    private final ActorMapper delegate;

    public ActorMapperDecorator(ActorMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public ActorDto actorToDto(Actor actor) {
        ActorDto dto = delegate.actorToDto( actor );
        dto.setFamous( actor.getAwards() >= 3 );
        return dto;
    }
}
