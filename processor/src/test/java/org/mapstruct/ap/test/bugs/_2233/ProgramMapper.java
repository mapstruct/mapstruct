/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2233;

import java.util.Collection;
import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ProgramMapper {

    ProgramMapper INSTANCE = Mappers.getMapper( ProgramMapper.class );

    ProgramResponseDto map(ProgramAggregate programAggregate);

    ProgramDto map(Program sourceProgramDto);

    Collection<ProgramDto> mapPrograms(Collection<Program> sourcePrograms);

    default <T> T fromOptional(Optional<T> optional) {
        return optional.orElse( null );
    }
}
