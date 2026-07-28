package org.mapstruct.itest.inheritable;

import org.mapstruct.ap.test.ignore.AnimalMapper;

interface AnimalCustomMapper extends AnimalMapper {
    @Mappings({
            @Mapping(target = "publicAge", ignore = true),
            @Mapping(target = "age", source = "age"),
            @Mapping(target = "publicColor", source = "publicColour", ignore = true),
            @Mapping(target = "color", source = "colour", ignore = true)
    })
    AnimalDto animalToDtoCustom(Animal animal);
}