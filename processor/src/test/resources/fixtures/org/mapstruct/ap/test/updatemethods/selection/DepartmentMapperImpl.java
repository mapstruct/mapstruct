/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.selection;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.annotation.Generated;
import org.mapstruct.ap.test.updatemethods.DepartmentDto;
import org.mapstruct.ap.test.updatemethods.DepartmentEntity;
import org.mapstruct.ap.test.updatemethods.EmployeeDto;
import org.mapstruct.ap.test.updatemethods.EmployeeEntity;
import org.mapstruct.ap.test.updatemethods.SecretaryDto;
import org.mapstruct.ap.test.updatemethods.SecretaryEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-12-30T19:17:50+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class DepartmentMapperImpl implements DepartmentMapper {

    private final ExternalHandWrittenMapper externalHandWrittenMapper = new ExternalHandWrittenMapper();

    @Override
    public void toDepartmentEntity(DepartmentDto dto, DepartmentEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
        if ( dto.getEmployees() != null ) {
            if ( entity.getEmployees() == null ) {
                entity.setEmployees( new ArrayList<EmployeeEntity>() );
            }
            externalHandWrittenMapper.toEmployeeEntityList( dto.getEmployees(), entity.getEmployees() );
        }
        else {
            entity.setEmployees( null );
        }
        if ( dto.getSecretaryToEmployee() != null ) {
            if ( entity.getSecretaryToEmployee() == null ) {
                entity.setSecretaryToEmployee( new LinkedHashMap<SecretaryEntity, EmployeeEntity>() );
            }
            externalHandWrittenMapper.toSecretaryEmployeeEntityMap( dto.getSecretaryToEmployee(), entity.getSecretaryToEmployee() );
        }
        else {
            entity.setSecretaryToEmployee( null );
        }
    }

    @Override
    public EmployeeEntity toEmployeeEntity(EmployeeDto dto) {
        if ( dto == null ) {
            return null;
        }

        EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setName( dto.getName() );

        return employeeEntity;
    }

    @Override
    public SecretaryEntity toSecretaryEntity(SecretaryDto dto) {
        if ( dto == null ) {
            return null;
        }

        SecretaryEntity secretaryEntity = new SecretaryEntity();

        secretaryEntity.setName( dto.getName() );

        return secretaryEntity;
    }
}
