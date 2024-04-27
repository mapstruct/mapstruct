/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.ConditionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.TargetPropertyName;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousSourceConditionalWithTargetPropertyNameMapper {

    BasicEmployee map(BasicEmployeeDto employee);

    @Condition(appliesTo = ConditionStrategy.SOURCE_PARAMETERS)
    default boolean isNotBlank(String value, @TargetPropertyName String targetProperty) {
        return value != null && !value.trim().isEmpty();
    }

}
