/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig.multiple;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class CarMapper2 {
    public static final CarMapper2 MAPPER = Mappers.getMapper( CarMapper2.class );

    @Mapping(target = "maker", source = "manufacturer")
    @Mapping(target = "seatCount", source = "numberOfSeats")
    public abstract Car2Dto mapToBase(Car2Entity car);

    @Mapping(target = "manufacturer", constant = "ford")
    @Mapping(target = "numberOfSeats", ignore = true)
    public abstract Car2Entity mapFromBase(Car2Dto carDto);

    @InheritConfiguration(name = "mapToBase")
    @InheritInverseConfiguration(name = "mapFromBase")
    public abstract Car2Dto mapTo(Car2Entity car);

    @InheritConfiguration(name = "mapFromBase")
    @InheritInverseConfiguration(name = "mapToBase")
    public abstract Car2Entity mapFrom(Car2Dto carDto);

}
