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
@Mapper( uses = DepartmentEntityFactory.class )
public interface OrganizationMapper {

    OrganizationMapper INSTANCE = Mappers.getMapper( OrganizationMapper.class );

    @Mappings({
        @Mapping(target = "type", constant = "commercial"),
        @Mapping(target = "typeNr", constant = "5")
    })
    void toOrganizationEntity(OrganizationDto dto, @MappingTarget OrganizationEntity entity);

    void toCompanyEntity(CompanyDto dto, @MappingTarget CompanyEntity entity);

    @Mappings({
        @Mapping( target = "employees", ignore = true ),
        @Mapping( target = "secretaryToEmployee", ignore = true )
    })
    DepartmentEntity toDepartmentEntity(DepartmentDto dto);

    @Mapping(source = "type", target = "type")
    void toName(String type, @MappingTarget OrganizationTypeEntity entity);

    @Mapping(source = "number", target = "number")
    void toNumber(Integer number, @MappingTarget OrganizationTypeNrEntity entity);
}
