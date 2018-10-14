/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ErroneousOrganizationMapper2 {

    ErroneousOrganizationMapper2 INSTANCE = Mappers.getMapper( ErroneousOrganizationMapper2.class );

    @Mapping(target = "type", constant = "commercial")
    void toOrganizationEntity(OrganizationDto dto, @MappingTarget OrganizationWithoutTypeGetterEntity entity);

    void toCompanyEntity(CompanyDto dto, @MappingTarget CompanyEntity entity);

    @Mapping(source = "type", target = "type")
    void toName(String type, @MappingTarget OrganizationTypeEntity entity);

    @Mapping( target = "employees", ignore = true )
    @Mapping( target = "secretaryToEmployee", ignore = true )
    DepartmentEntity toDepartmentEntity(DepartmentDto dto);

}
