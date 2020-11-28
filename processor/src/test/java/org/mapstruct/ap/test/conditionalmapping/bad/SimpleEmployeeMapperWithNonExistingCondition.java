/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditionalmapping.bad;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.conditionalmapping.Employee;
import org.mapstruct.ap.test.conditionalmapping.EmployeeDto;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleEmployeeMapperWithNonExistingCondition {
    SimpleEmployeeMapperWithNonExistingCondition INSTANCE =
            Mappers.getMapper( SimpleEmployeeMapperWithNonExistingCondition.class );

    @Mapping(source = "uniqueIdNumber", target = "nin", condition = "nonExisting")
    Employee mapEmployee( EmployeeDto employeeDto );

}
