/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
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
public interface CarMapperWithAutoInheritance {
    CarMapperWithAutoInheritance INSTANCE = Mappers.getMapper( CarMapperWithAutoInheritance.class );

    @Mapping(target = "color", source = "colour")
    CarEntity toCarEntity(CarDto carDto);

    @InheritInverseConfiguration(name = "toCarEntity")
    CarDto toCarDto(CarEntity entity);

    @Mappings({
        @Mapping(target = "color", source = "colour"),
        @Mapping(target = "auditTrail", constant = "fixed")
    })
    CarEntity toCarEntityWithFixedAuditTrail(CarDto carDto);

    @Mapping(target = "color", source = "colour")
    void intoCarEntityOnItsOwn(CarDto carDto, @MappingTarget CarEntity entity);

    @InheritConfiguration(name = "toCarEntity")
    void intoCarEntity(CarDto carDto, @MappingTarget CarEntity entity);
}
