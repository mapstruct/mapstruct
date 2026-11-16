/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.qualifier;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { RossiniMapper.class, VivaldiMapper.class })
public interface SubclassQualifiedByNameMapper {
    SubclassQualifiedByNameMapper INSTANCE = Mappers.getMapper( SubclassQualifiedByNameMapper.class );

    @SubclassMapping(source = Rossini.class, target = RossiniDto.class)
    @SubclassMapping(source = Vivaldi.class, target = VivaldiDto.class)
    ComposerDto toDto(Composer composer);

    @SubclassMapping(source = Rossini.class, target = RossiniDto.class, qualifiedByName = "light")
    @SubclassMapping(source = Vivaldi.class, target = VivaldiDto.class, qualifiedByName = "light")
    ComposerDto toDtoLight(Composer composer);

    @SubclassMapping(source = Rossini.class, target = RossiniDto.class)
    @SubclassMapping(source = Vivaldi.class, target = VivaldiDto.class, qualifiedByName = "light")
    ComposerDto toDtoLightJustVivaldi(Composer composer);

    @InheritInverseConfiguration(name = "toDto")
    Composer fromDto(ComposerDto composer);

    @InheritInverseConfiguration(name = "toDtoLight")
    Composer fromDtoLight(ComposerDto composer);

    @InheritInverseConfiguration(name = "toDtoLightJustVivaldi")
    Composer fromDtoLightJustVivaldi(ComposerDto composer);
}
