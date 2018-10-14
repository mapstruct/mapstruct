/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig.multiple;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Shared configuration from Entities to Dto instances
 */
@MapperConfig
public interface EntityToDtoMappingConfig {

    @Mapping(target = "dbId", source = "id")
    @Mapping(target = "links", ignore = true)
    BaseDto entityToDto(BaseEntity entity);

    @Mapping(target = "id", source = "dbId")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "lastModifiedBy", constant = "restApiUser") // force modifiedBy with restApiUser constant
    @Mapping(target = "lastModifiedDate", ignore = true)
    BaseEntity dtoToEntity(BaseDto dto);

}
