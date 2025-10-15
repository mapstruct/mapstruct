/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignored;

import org.mapstruct.Ignored;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnimalMapper {

    AnimalMapper INSTANCE = Mappers.getMapper( AnimalMapper.class );

    @Ignored( targets = { "publicAge", "age", "publicColor", "color" } )
    AnimalDto animalToDto( Animal animal );

}
