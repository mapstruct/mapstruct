/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.iterable;

import java.util.List;

import org.mapstruct.IterableElementCondition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.conditional.Employee;
import org.mapstruct.ap.test.conditional.EmployeeDto;
import org.mapstruct.factory.Mappers;

/**
 * @author Oliver Erhart
 */
@Mapper
public interface IterableElementConditionMapper {

    IterableElementConditionMapper INSTANCE =
        Mappers.getMapper( IterableElementConditionMapper.class );

    @Mapping(target = "nin", source = "name")
    @Mapping(target = "ssid", source = "uniqueIdNumber")
    Employee map(EmployeeDto employee);

    List<Employee> mapListToList(List<EmployeeDto> employees);

    List<Employee> mapArrayToList(EmployeeDto[] employees);

//    Employee[] mapListToArray(List<EmployeeDto> employees);
//
//    Employee[] mapArrayToArray(EmployeeDto[] employees);


    @IterableElementCondition
    default boolean countryIsNotNull(EmployeeDto value) {
        return value.getCountry() != null;
    }

}
