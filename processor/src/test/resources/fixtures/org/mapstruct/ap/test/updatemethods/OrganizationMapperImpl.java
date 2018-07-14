/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-12-30T19:11:45+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class OrganizationMapperImpl implements OrganizationMapper {

    private final DepartmentEntityFactory departmentEntityFactory = new DepartmentEntityFactory();

    @Override
    public void toOrganizationEntity(OrganizationDto dto, OrganizationEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCompany() != null ) {
            if ( entity.getCompany() == null ) {
                entity.setCompany( new CompanyEntity() );
            }
            toCompanyEntity( dto.getCompany(), entity.getCompany() );
        }
        else {
            entity.setCompany( null );
        }

        if ( entity.getType() == null ) {
            entity.setType( new OrganizationTypeEntity() );
        }
        toName( "commercial", entity.getType() );
        if ( entity.getTypeNr() == null ) {
            entity.setTypeNr( new OrganizationTypeNrEntity() );
        }
        toNumber( Integer.parseInt( "5" ), entity.getTypeNr() );
    }

    @Override
    public void toCompanyEntity(CompanyDto dto, CompanyEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
        entity.setDepartment( toDepartmentEntity( dto.getDepartment() ) );
    }

    @Override
    public DepartmentEntity toDepartmentEntity(DepartmentDto dto) {
        if ( dto == null ) {
            return null;
        }

        DepartmentEntity departmentEntity = departmentEntityFactory.createDepartmentEntity();

        departmentEntity.setName( dto.getName() );

        return departmentEntity;
    }

    @Override
    public void toName(String type, OrganizationTypeEntity entity) {
        if ( type == null ) {
            return;
        }

        entity.setType( type );
    }

    @Override
    public void toNumber(Integer number, OrganizationTypeNrEntity entity) {
        if ( number == null ) {
            return;
        }

        entity.setNumber( number );
    }
}
