/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-18T14:48:39+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
public class CompanyMapper1Impl implements CompanyMapper1 {

    private final DepartmentEntityFactory departmentEntityFactory = new DepartmentEntityFactory();

    @Override
    public void toCompanyEntity(UnmappableCompanyDto dto, CompanyEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
        if ( dto.getDepartment() != null ) {
            if ( entity.getDepartment() == null ) {
                entity.setDepartment( departmentEntityFactory.createDepartmentEntity() );
            }
            unmappableDepartmentDtoToDepartmentEntity( dto.getDepartment(), entity.getDepartment() );
        }
        else {
            entity.setDepartment( null );
        }
    }

    @Override
    public void toInBetween(UnmappableDepartmentDto dto, DepartmentInBetween entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
    }

    @Override
    public void toDepartmentEntity(DepartmentInBetween dto, DepartmentEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
    }

    protected SecretaryEntity secretaryDtoToSecretaryEntity(SecretaryDto secretaryDto) {
        if ( secretaryDto == null ) {
            return null;
        }

        SecretaryEntity secretaryEntity = new SecretaryEntity();

        secretaryEntity.setName( secretaryDto.getName() );

        return secretaryEntity;
    }

    protected EmployeeEntity employeeDtoToEmployeeEntity(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setName( employeeDto.getName() );

        return employeeEntity;
    }

    protected Map<SecretaryEntity, EmployeeEntity> secretaryDtoEmployeeDtoMapToSecretaryEntityEmployeeEntityMap(Map<SecretaryDto, EmployeeDto> map) {
        if ( map == null ) {
            return null;
        }

        Map<SecretaryEntity, EmployeeEntity> map1 = LinkedHashMap.newLinkedHashMap( map.size() );

        for ( java.util.Map.Entry<SecretaryDto, EmployeeDto> entry : map.entrySet() ) {
            SecretaryEntity key = secretaryDtoToSecretaryEntity( entry.getKey() );
            EmployeeEntity value = employeeDtoToEmployeeEntity( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }

    protected void unmappableDepartmentDtoToDepartmentEntity(UnmappableDepartmentDto unmappableDepartmentDto, DepartmentEntity mappingTarget) {
        if ( unmappableDepartmentDto == null ) {
            return;
        }

        mappingTarget.setName( unmappableDepartmentDto.getName() );
        if ( mappingTarget.getSecretaryToEmployee() != null ) {
            Map<SecretaryEntity, EmployeeEntity> map = secretaryDtoEmployeeDtoMapToSecretaryEntityEmployeeEntityMap( unmappableDepartmentDto.getSecretaryToEmployee() );
            if ( map != null ) {
                mappingTarget.getSecretaryToEmployee().clear();
                mappingTarget.getSecretaryToEmployee().putAll( map );
            }
            else {
                mappingTarget.setSecretaryToEmployee( null );
            }
        }
        else {
            Map<SecretaryEntity, EmployeeEntity> map = secretaryDtoEmployeeDtoMapToSecretaryEntityEmployeeEntityMap( unmappableDepartmentDto.getSecretaryToEmployee() );
            if ( map != null ) {
                mappingTarget.setSecretaryToEmployee( map );
            }
        }
    }
}
