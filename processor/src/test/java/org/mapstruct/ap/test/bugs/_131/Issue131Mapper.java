/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._131;

import org.mapstruct.Mapper;
import org.mapstruct.SubClassMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue131Mapper {
    Issue131Mapper INSTANCE = Mappers.getMapper( Issue131Mapper.class );

    org.mapstruct.ap.test.bugs._131.dto.VehicleCollection
                    map(org.mapstruct.ap.test.bugs._131.domain.VehicleCollection vehicles);

    org.mapstruct.ap.test.bugs._131.dto.Car mapCar(org.mapstruct.ap.test.bugs._131.domain.Car car);

    @SubClassMapping( sourceClass = org.mapstruct.ap.test.bugs._131.domain.Car.class,
                    targetClass = org.mapstruct.ap.test.bugs._131.dto.Car.class )
    @SubClassMapping( sourceClass = org.mapstruct.ap.test.bugs._131.domain.Bike.class,
                    targetClass = org.mapstruct.ap.test.bugs._131.dto.Bike.class )
    org.mapstruct.ap.test.bugs._131.dto.Vehicle map(org.mapstruct.ap.test.bugs._131.domain.Vehicle vehicle);
}
