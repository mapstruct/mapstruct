/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-12-30T19:11:46+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class CompanyMapperImpl implements CompanyMapper {

    private final DepartmentEntityFactory departmentEntityFactory = new DepartmentEntityFactory();

    @Override
    public void toCompanyEntity(CompanyDto dto, CompanyEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
        if ( dto.getDepartment() != null ) {
            if ( entity.getDepartment() == null ) {
                entity.setDepartment( departmentEntityFactory.createDepartmentEntity() );
            }
            toDepartmentEntity( toInBetween( dto.getDepartment() ), entity.getDepartment() );
        }
        else {
            entity.setDepartment( null );
        }
    }

    @Override
    public DepartmentInBetween toInBetween(DepartmentDto dto) {
        if ( dto == null ) {
            return null;
        }

        DepartmentInBetween departmentInBetween = new DepartmentInBetween();

        departmentInBetween.setName( dto.getName() );

        return departmentInBetween;
    }

    @Override
    public void toDepartmentEntity(DepartmentInBetween dto, DepartmentEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
    }
}
