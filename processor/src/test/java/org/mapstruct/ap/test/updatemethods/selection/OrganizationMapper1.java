/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.selection;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ap.test.updatemethods.CompanyDto;
import org.mapstruct.ap.test.updatemethods.CompanyEntity;
import org.mapstruct.ap.test.updatemethods.DepartmentEntityFactory;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(uses = { ExternalMapper.class, DepartmentEntityFactory.class } )
public interface OrganizationMapper1 {

    OrganizationMapper1 INSTANCE = Mappers.getMapper( OrganizationMapper1.class );

    void toCompanyEntity(CompanyDto dto, @MappingTarget CompanyEntity entity);

}
