/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._366;

import org.mapstruct.Mapper;
import org.mapstruct.SubClassMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue366Mapper {
    Issue366Mapper INSTANCE = Mappers.getMapper( Issue366Mapper.class );

    org.mapstruct.ap.test.bugs._366.dto.VehicleCollection
                    map(org.mapstruct.ap.test.bugs._366.domain.VehicleCollection vehicles);

    org.mapstruct.ap.test.bugs._366.dto.Car map(org.mapstruct.ap.test.bugs._366.domain.Car car);

    org.mapstruct.ap.test.bugs._366.dto.Bike map(org.mapstruct.ap.test.bugs._366.domain.Bike bike);

    @SubClassMapping( sourceClass = org.mapstruct.ap.test.bugs._366.domain.Car.class,
                    targetClass = org.mapstruct.ap.test.bugs._366.dto.Car.class )
    @SubClassMapping( sourceClass = org.mapstruct.ap.test.bugs._366.domain.Bike.class,
                    targetClass = org.mapstruct.ap.test.bugs._366.dto.Bike.class )
    org.mapstruct.ap.test.bugs._366.dto.Vehicle map(org.mapstruct.ap.test.bugs._366.domain.Vehicle vehicle);
}
