/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.Qualifier;
import org.mapstruct.ap.test.conditional.Employee;
import org.mapstruct.ap.test.conditional.EmployeeDto;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(uses = ConditionalMethodWithClassQualifiersMapper.StaticUtil.class)
public interface ConditionalMethodWithClassQualifiersMapper {

    ConditionalMethodWithClassQualifiersMapper INSTANCE =
        Mappers.getMapper( ConditionalMethodWithClassQualifiersMapper.class );

    @Mapping(target = "ssid", source = "uniqueIdNumber",
        conditionQualifiedBy = UtilConditions.class, conditionQualifiedByName = "american")
    @Mapping(target = "nin", source = "uniqueIdNumber",
        conditionQualifiedBy = UtilConditions.class, conditionQualifiedByName = "british")
    Employee map(EmployeeDto employee);

    @Condition
    default boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    @UtilConditions
    interface StaticUtil {

        @Condition
        @Named("american")
        static boolean isAmericanCitizen(EmployeeDto employerDto) {
            return "US".equals( employerDto.getCountry() );
        }

        @Condition
        @Named("british")
        static boolean isBritishCitizen(EmployeeDto employeeDto) {
            return "UK".equals( employeeDto.getCountry() );
        }
    }

    @Qualifier
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.CLASS)
    @interface UtilConditions {
    }
}
