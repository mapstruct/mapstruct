/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.selection;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ap.test.updatemethods.DepartmentDto;
import org.mapstruct.ap.test.updatemethods.DepartmentEntity;
import org.mapstruct.ap.test.updatemethods.EmployeeDto;
import org.mapstruct.ap.test.updatemethods.EmployeeEntity;
import org.mapstruct.ap.test.updatemethods.SecretaryDto;
import org.mapstruct.ap.test.updatemethods.SecretaryEntity;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( uses = ExternalHandWrittenMapper.class )
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper( DepartmentMapper.class );

    void toDepartmentEntity(DepartmentDto dto, @MappingTarget DepartmentEntity entity);

    EmployeeEntity toEmployeeEntity(EmployeeDto dto);

    SecretaryEntity toSecretaryEntity(SecretaryDto dto);

}
