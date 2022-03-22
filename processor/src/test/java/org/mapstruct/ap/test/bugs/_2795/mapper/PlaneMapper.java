/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2795.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ap.test.bugs._2795.dto.PlaneDto;
import org.mapstruct.ap.test.bugs._2795.model.Plane;

@Mapper( uses = { MapperConfig.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR )
public abstract class PlaneMapper {

    public abstract PlaneDto planeToPlaneDto(Plane plane);

    public abstract Plane planeDtoToPlane(PlaneDto planeDto);

    @BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    @Mapping( target = "id", ignore = true )
    public abstract Plane updatePlaneWithPlaneDto(PlaneDto update, @MappingTarget Plane destination);

}
