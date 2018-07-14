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
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(
    uses = NotToBeUsedMapper.class,
    config = AutoInheritedConfig.class,
    mappingInheritanceStrategy = MappingInheritanceStrategy.EXPLICIT
)
public abstract class CarMapperWithExplicitInheritance {
    public static final CarMapperWithExplicitInheritance INSTANCE =
        Mappers.getMapper( CarMapperWithExplicitInheritance.class );

    @InheritConfiguration(name = "baseDtoToEntity")
    @Mapping(target = "color", source = "colour")
    public abstract CarEntity toCarEntity(CarDto carDto);

    @InheritInverseConfiguration(name = "toCarEntity")
    public abstract CarDto toCarDto(CarEntity entity);

    @InheritConfiguration(name = "toCarEntity")
    @Mapping(target = "auditTrail", constant = "fixed")
    public abstract CarEntity toCarEntityWithFixedAuditTrail(CarDto carDto);

    // this method should not be considered. See issue #1013
    public void toCarEntity(CarDto carDto, @MappingTarget CarEntity carEntity) { }
}
