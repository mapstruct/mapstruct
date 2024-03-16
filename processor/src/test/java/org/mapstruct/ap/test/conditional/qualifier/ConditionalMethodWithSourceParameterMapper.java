/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.qualifier;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ap.test.conditional.Employee;
import org.mapstruct.ap.test.conditional.EmployeeDto;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(uses = ConditionalMethodWithSourceParameterMapper.StaticUtil.class)
public interface ConditionalMethodWithSourceParameterMapper {

    ConditionalMethodWithSourceParameterMapper INSTANCE =
        Mappers.getMapper( ConditionalMethodWithSourceParameterMapper.class );

    @Mapping(target = "ssid", source = "uniqueIdNumber", conditionQualifiedByName = "isAmericanCitizen")
    @Mapping(target = "nin", source = "uniqueIdNumber", conditionQualifiedByName = "isBritishCitizen")
    Employee map(EmployeeDto employee);

    @Condition
    default boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    @Condition
    @Named("isAmericanCitizen")
    default boolean isAmericanCitizen(EmployeeDto employerDto) {
        return "US".equals( employerDto.getCountry() );
    }

    interface StaticUtil {

        @Condition
        @Named("isBritishCitizen")
        static boolean isBritishCitizen(EmployeeDto employeeDto) {
            return "UK".equals( employeeDto.getCountry() );
        }
    }
}
