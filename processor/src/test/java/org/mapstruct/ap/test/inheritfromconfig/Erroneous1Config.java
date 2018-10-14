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
 * Leads to ambiguous prototype methods error.
 *
 * @author Andreas Gudian
 */
@MapperConfig(
    mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,
    unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface Erroneous1Config {
    @Mapping(target = "primaryKey", source = "id")
    @Mapping(target = "auditTrail", ignore = true)
    BaseVehicleEntity baseDtoToEntity(BaseVehicleDto dto);

    @Mapping(target = "primaryKey", ignore = true)
    BaseVehicleEntity anythingToEntity(Object anyting);
}
