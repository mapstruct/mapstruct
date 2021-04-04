/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.expression;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.conditional.Employee;
import org.mapstruct.ap.test.conditional.EmployeeDto;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(imports = ConditionalMethodsInUtilClassMapper.StaticUtil.class)
public interface ConditionalMethodsInUtilClassMapper {

    ConditionalMethodsInUtilClassMapper INSTANCE = Mappers.getMapper( ConditionalMethodsInUtilClassMapper.class );

    @Mapping(target = "ssid", source = "uniqueIdNumber",
        conditionExpression = "java(StaticUtil.isAmericanCitizen( employee ))")
    @Mapping(target = "nin", source = "uniqueIdNumber",
        conditionExpression = "java(StaticUtil.isBritishCitizen( employee ))")
    Employee map(EmployeeDto employee);

    interface StaticUtil {

        static boolean isAmericanCitizen(EmployeeDto employeeDto) {
            return "US".equals( employeeDto.getCountry() );
        }

        static boolean isBritishCitizen(EmployeeDto employeeDto) {
            return "UK".equals( employeeDto.getCountry() );
        }
    }
}
