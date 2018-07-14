/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.ongeneratedmethods;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CompanyMapperPostProcessing.class )
public interface CompanyMapper {

   CompanyMapper INSTANCE = Mappers.getMapper( CompanyMapper.class );

   AddressDto toAddressDto(Address address);

   CompanyDto toCompanyDto(Company company);

   List<EmployeeDto> toEmployeeDtoList(List<Employee> employee);

   EmployeeDto toEmployeeDto(Employee employee);

}
