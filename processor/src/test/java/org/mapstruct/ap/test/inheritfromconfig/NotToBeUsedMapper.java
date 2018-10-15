/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface NotToBeUsedMapper {

    @Mappings({
        @Mapping(target = "primaryKey", ignore = true),
        @Mapping(target = "auditTrail", ignore = true),
        @Mapping(target = "color", ignore = true)
    })
    CarEntity toCarEntity(CarDto carDto);
}
