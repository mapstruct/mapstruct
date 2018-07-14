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
@Mapper(uses = { ExternalHandWrittenMapper.class, DepartmentEntityFactory.class } )
public interface OrganizationMapper2 {

    OrganizationMapper2 INSTANCE = Mappers.getMapper( OrganizationMapper2.class );

    void toCompanyEntity(CompanyDto dto, @MappingTarget CompanyEntity entity);

}
