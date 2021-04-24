/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2421;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper( CompanyMapper.class );

    @Mapping(target = "companyTypeLatinTitle", source = "companyType.latinTitle")
    CompanyDto toDto(Company entity);

    @InheritInverseConfiguration
    @Mapping( target = "companyType", source =  "companyType")
    Company toEntity(CompanyDto dto);

}
