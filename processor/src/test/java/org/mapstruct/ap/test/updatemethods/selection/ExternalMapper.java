/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.selection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.updatemethods.DepartmentDto;
import org.mapstruct.ap.test.updatemethods.DepartmentEntity;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ExternalMapper {

    ExternalMapper INSTANCE = Mappers.getMapper( ExternalMapper.class );

    @Mappings({
        @Mapping( target = "employees", ignore = true ),
        @Mapping( target = "secretaryToEmployee", ignore = true )
    })
    void toDepartmentEntity(DepartmentDto dto, @MappingTarget DepartmentEntity entity);

}
