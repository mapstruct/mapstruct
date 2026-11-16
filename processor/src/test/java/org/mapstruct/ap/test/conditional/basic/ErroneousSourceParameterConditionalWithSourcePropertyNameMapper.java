/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.ConditionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.SourcePropertyName;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousSourceParameterConditionalWithSourcePropertyNameMapper {

    BasicEmployee map(BasicEmployeeDto employee);

    @Condition(appliesTo = ConditionStrategy.SOURCE_PARAMETERS)
    default boolean isNotBlank(String value, @SourcePropertyName String sourceProperty) {
        return value != null && !value.trim().isEmpty();
    }

}
