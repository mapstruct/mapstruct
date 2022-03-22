/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2795.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._2795.dto.ShipDto;
import org.mapstruct.ap.test.bugs._2795.model.Ship;

@Mapper( uses = { MapperConfig.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR )
public abstract class ShipMapper {

    public abstract ShipDto shipToShipDto(Ship ship);

    public abstract Ship shipDtoToShip(ShipDto shipDto);

}
