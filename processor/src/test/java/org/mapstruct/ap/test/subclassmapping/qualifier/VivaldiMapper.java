/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.qualifier;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface VivaldiMapper {
    VivaldiDto toDto(Vivaldi vivaldi);

    @Light
    @Mapping(target = "seasons", ignore = true)
    VivaldiDto toDtoLight(Vivaldi vivaldi);

    @Named("light")
    @Mapping(target = "seasons", ignore = true)
    VivaldiDto toDtoLightNamed(Vivaldi vivaldi);

    @Unused
    @BeanMapping(ignoreByDefault = true)
    VivaldiDto toDtoUnused(Vivaldi vivaldi);

    @Named("unused")
    @BeanMapping(ignoreByDefault = true)
    VivaldiDto toDtoUnusedNamed(Vivaldi vivaldi);

    Vivaldi fromDto(VivaldiDto vivaldi);

    @Light
    @Mapping(target = "seasons", ignore = true)
    Vivaldi fromDtoLight(VivaldiDto vivaldi);

    @Named("light")
    @Mapping(target = "seasons", ignore = true)
    Vivaldi fromDtoLightNamed(VivaldiDto vivaldi);

    @Unused
    @BeanMapping(ignoreByDefault = true)
    Vivaldi fromDtoUnused(VivaldiDto vivaldi);

    @Named("unused")
    @BeanMapping(ignoreByDefault = true)
    Vivaldi fromDtoUnusedNamed(VivaldiDto vivaldi);
}
