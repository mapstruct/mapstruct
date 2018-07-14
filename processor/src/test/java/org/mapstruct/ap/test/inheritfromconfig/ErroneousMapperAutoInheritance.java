/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(
    config = AutoInheritedReverseConfig.class
)
public interface ErroneousMapperAutoInheritance {
    ErroneousMapperAutoInheritance INSTANCE = Mappers.getMapper( ErroneousMapperAutoInheritance.class );

    @Mapping(target = "color", source = "colour")
    CarEntity toCarEntity(CarDto carDto);
}
