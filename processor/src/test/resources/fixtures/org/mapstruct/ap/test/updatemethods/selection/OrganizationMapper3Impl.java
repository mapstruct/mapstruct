/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.selection;

import javax.annotation.Generated;
import org.mapstruct.ap.test.updatemethods.BossDto;
import org.mapstruct.ap.test.updatemethods.BossEntity;
import org.mapstruct.ap.test.updatemethods.ConstructableDepartmentEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-12-30T19:17:49+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class OrganizationMapper3Impl implements OrganizationMapper3 {

    private final ExternalMapper externalMapper = ExternalMapper.INSTANCE;

    @Override
    public void toBossEntity(BossDto dto, BossEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
        if ( dto.getDepartment() != null ) {
            if ( entity.getDepartment() == null ) {
                entity.setDepartment( new ConstructableDepartmentEntity() );
            }
            externalMapper.toDepartmentEntity( dto.getDepartment(), entity.getDepartment() );
        }
        else {
            entity.setDepartment( null );
        }
    }
}
