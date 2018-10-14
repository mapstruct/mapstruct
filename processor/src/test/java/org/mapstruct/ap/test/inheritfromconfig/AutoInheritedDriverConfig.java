/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * @author Andreas Gudian
 */
@MapperConfig(
    mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AutoInheritedDriverConfig {
    @Mapping(target = "primaryKey", source = "dto.id")
    @Mapping(target = "auditTrail", ignore = true)
    @Mapping(target = "driverName", source = "drv.name")
    CarWithDriverEntity baseDtoToEntity(DriverDto drv, BaseVehicleDto dto);
}
