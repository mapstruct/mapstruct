/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.selection;

import javax.annotation.Generated;
import org.mapstruct.ap.test.updatemethods.CompanyDto;
import org.mapstruct.ap.test.updatemethods.CompanyEntity;
import org.mapstruct.ap.test.updatemethods.DepartmentEntityFactory;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-12-30T19:17:50+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class OrganizationMapper1Impl implements OrganizationMapper1 {

    private final ExternalMapper externalMapper = Mappers.getMapper( ExternalMapper.class );
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
            externalMapper.toDepartmentEntity( dto.getDepartment(), entity.getDepartment() );
        }
        else {
            entity.setDepartment( null );
        }
    }
}
