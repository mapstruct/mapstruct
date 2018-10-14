/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(
    config = AutoInheritedConfig.class
)
public interface Erroneous2Mapper {
    Erroneous2Mapper INSTANCE = Mappers.getMapper( Erroneous2Mapper.class );

    @InheritConfiguration(name = "toCarEntity2")
    CarEntity toCarEntity1(CarDto carDto);

    @InheritConfiguration(name = "toCarEntity3")
    CarEntity toCarEntity2(CarDto carDto);

    @InheritConfiguration(name = "toCarEntity1")
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "auditTrail", ignore = true)
    @Mapping(target = "primaryKey", ignore = true)
    void toCarEntity3(CarDto carDto, @MappingTarget CarEntity entity);
}
