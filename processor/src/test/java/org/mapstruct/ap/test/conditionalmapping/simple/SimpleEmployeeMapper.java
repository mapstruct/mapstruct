/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditionalmapping.simple;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.conditionalmapping.Employee;
import org.mapstruct.ap.test.conditionalmapping.EmployeeDto;
import org.mapstruct.factory.Mappers;

@Mapper( uses = { StaticUtil.class } )
public interface SimpleEmployeeMapper {
    SimpleEmployeeMapper INSTANCE = Mappers.getMapper( SimpleEmployeeMapper.class );

    @Mapping(source = "uniqueIdNumber", target = "ssid", condition = "isAmericanCitizen")
    @Mapping(source = "uniqueIdNumber", target = "nin", condition = "StaticUtil.isBritishCitizen")
    Employee mapEmployee( EmployeeDto employeeDto );

    default boolean isAmericanCitizen( EmployeeDto employerDto ) {
        return "US".equals( employerDto.getCountry() );
    }

}
