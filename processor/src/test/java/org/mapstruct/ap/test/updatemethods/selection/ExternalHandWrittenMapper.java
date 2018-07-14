/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.selection;

import java.util.List;
import java.util.Map;

import org.mapstruct.MappingTarget;
import org.mapstruct.ap.test.updatemethods.DepartmentDto;
import org.mapstruct.ap.test.updatemethods.DepartmentEntity;
import org.mapstruct.ap.test.updatemethods.EmployeeDto;
import org.mapstruct.ap.test.updatemethods.EmployeeEntity;
import org.mapstruct.ap.test.updatemethods.SecretaryDto;
import org.mapstruct.ap.test.updatemethods.SecretaryEntity;

/**
 *
 * @author Sjaak Derksen
 */
public class ExternalHandWrittenMapper {

    public void toDepartmentEntity(DepartmentDto dto, @MappingTarget DepartmentEntity entity) {
        if ( entity != null && dto != null ) {
            entity.setName( dto.getName() );
        }
    }

    public void toEmployeeEntityList(List<EmployeeDto> dtos, @MappingTarget List<EmployeeEntity> entities) {

        if ( entities != null && dtos != null ) {
            for ( EmployeeDto dto : dtos) {
                entities.add( DepartmentMapper.INSTANCE.toEmployeeEntity( dto ) );
            }
        }

    }

    public void toSecretaryEmployeeEntityMap(Map<SecretaryDto, EmployeeDto> dtoMap,
        @MappingTarget Map<SecretaryEntity, EmployeeEntity> entityMap) {

        if ( entityMap != null && dtoMap != null ) {
            for ( Map.Entry<SecretaryDto, EmployeeDto> dtoEntry : dtoMap.entrySet() ) {
                entityMap.put(
                    DepartmentMapper.INSTANCE.toSecretaryEntity( dtoEntry.getKey() ),
                    DepartmentMapper.INSTANCE.toEmployeeEntity( dtoEntry.getValue() )  );
            }
        }
    }
}
