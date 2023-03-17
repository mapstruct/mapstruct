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
public interface RossiniMapper {
    RossiniDto toDto(Rossini rossini);

    @Light
    @Mapping(target = "crescendo", ignore = true)
    RossiniDto toDtoLight(Rossini source);

    @Named("light")
    @Mapping(target = "crescendo", ignore = true)
    RossiniDto toDtoLightNamed(Rossini source);

    @Unused
    @BeanMapping(ignoreByDefault = true)
    RossiniDto toDtoUnused(Rossini source);

    @Named("unused")
    @BeanMapping(ignoreByDefault = true)
    RossiniDto toDtoUnusedNamed(Rossini source);

    Rossini fromDto(RossiniDto rossini);

    @Light
    @Mapping(target = "crescendo", ignore = true)
    Rossini fromDtoLight(RossiniDto source);

    @Named("light")
    @Mapping(target = "crescendo", ignore = true)
    Rossini fromDtoLightNamed(RossiniDto source);

    @Unused
    @BeanMapping(ignoreByDefault = true)
    Rossini fromDtoUnused(RossiniDto source);

    @Named("unused")
    @BeanMapping(ignoreByDefault = true)
    Rossini fromDtoUnusedNamed(RossiniDto source);
}
