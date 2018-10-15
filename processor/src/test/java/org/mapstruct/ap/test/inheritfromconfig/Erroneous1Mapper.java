/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(
    config = Erroneous1Config.class
)
public interface Erroneous1Mapper {
    Erroneous1Mapper INSTANCE = Mappers.getMapper( Erroneous1Mapper.class );

    @Mapping(target = "color", source = "colour")
    CarEntity toCarEntity(CarDto carDto);

    @Mappings({
        @Mapping(target = "color", source = "colour"),
        @Mapping(target = "auditTrail", constant = "fixed")
    })
    CarEntity toCarEntityWithFixedAuditTrail(CarDto carDto);
}
