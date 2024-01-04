/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.targetpropertyname;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.TargetPropertyName;

@Mapper
public interface ErroneousNonStringTargetPropertyNameParameter {

    Employee map(EmployeeDto employee);

    @Condition
    default boolean isNotBlank(String value, @TargetPropertyName int propName) {
        return value != null && !value.trim().isEmpty();
    }
}
