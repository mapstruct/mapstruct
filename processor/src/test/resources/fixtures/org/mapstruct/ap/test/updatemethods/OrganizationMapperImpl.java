/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
