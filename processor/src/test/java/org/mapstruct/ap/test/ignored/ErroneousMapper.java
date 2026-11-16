/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignored;

import org.mapstruct.Ignored;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousMapper {

    ErroneousMapper INSTANCE = Mappers.getMapper( ErroneousMapper.class );

    @Mapping(target = "name", ignore = true)
    @Ignored(targets = { "name", "color", "publicColor" })
    AnimalDto ignoredAndMappingAnimalToDto( Animal animal );

    @Mapping(target = "publicColor", source = "publicColour")
    @Ignored(targets =  { "publicColor", "color" })
    AnimalDto ignoredAndMappingAnimalToDtoMap( Animal animal );

}
