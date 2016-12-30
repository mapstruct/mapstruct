/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.updatemethods.selection;

import java.util.ArrayList;
import java.util.HashMap;
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
        if ( entity.getEmployees() == null ) {
            entity.setEmployees( new ArrayList<EmployeeEntity>() );
        }
        externalHandWrittenMapper.toEmployeeEntityList( dto.getEmployees(), entity.getEmployees() );
        if ( entity.getSecretaryToEmployee() == null ) {
            entity.setSecretaryToEmployee( new HashMap<SecretaryEntity, EmployeeEntity>() );
        }
        externalHandWrittenMapper.toSecretaryEmployeeEntityMap( dto.getSecretaryToEmployee(), entity.getSecretaryToEmployee() );
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
