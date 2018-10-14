/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignore;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnimalMapper {

    AnimalMapper INSTANCE = Mappers.getMapper( AnimalMapper.class );

    @Mapping(target = "publicAge", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "publicColor", source = "publicColour", ignore = true)
    @Mapping(target = "color", source = "colour", ignore = true)
    AnimalDto animalToDto(Animal animal);

    @BeanMapping( ignoreByDefault = true )
    AnimalDto animalToDtoIgnoreAll(Animal animal);

    @InheritInverseConfiguration( name = "animalToDto" )
    Animal animalDtoToAnimal(AnimalDto animalDto);
}
