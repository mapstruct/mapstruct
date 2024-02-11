/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.propertyname.targetpropertyname;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.TargetPropertyName;
import org.mapstruct.ap.test.conditional.propertyname.Employee;
import org.mapstruct.ap.test.conditional.propertyname.EmployeeDto;

@Mapper
public interface ErroneousNonStringTargetPropertyNameParameter {

    @Mapping(target = "country", source = "originCountry")
    @Mapping(target = "addresses", source = "originAddresses")
    Employee map(EmployeeDto employee);

    @Condition
    default boolean isNotBlank(String value, @TargetPropertyName int propName) {
        return value != null && !value.trim().isEmpty();
    }
}
