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
public interface CompanyMapper1 {

    CompanyMapper1 INSTANCE = Mappers.getMapper( CompanyMapper1.class );

    void toCompanyEntity(UnmappableCompanyDto dto, @MappingTarget CompanyEntity entity);

    void  toInBetween(UnmappableDepartmentDto dto, @MappingTarget DepartmentInBetween entity);

    @Mappings({
        @Mapping( target = "employees", ignore = true ),
        @Mapping( target = "secretaryToEmployee", ignore = true )
    })
    void toDepartmentEntity(DepartmentInBetween dto, @MappingTarget DepartmentEntity entity);
}
