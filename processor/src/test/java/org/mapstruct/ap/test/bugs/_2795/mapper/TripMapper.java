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
import org.mapstruct.ap.test.bugs._2795.dto.TripDto;
import org.mapstruct.ap.test.bugs._2795.model.Trip;

@Mapper( uses = { MapperConfig.class, CarMapper.class, PlaneMapper.class,
    ShipMapper.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR )
public abstract class TripMapper {

    public abstract TripDto tripToTripDto(Trip trip);

    public abstract Trip tripDtoToTrip(TripDto tripDto);

    @BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    @Mapping( target = "id", ignore = true )
    public abstract Trip updateTripWithTripDto(TripDto update, @MappingTarget Trip destination);

}
