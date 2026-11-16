/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousConditionalWithoutAppliesToMethodMapper {

    BasicEmployee map(BasicEmployeeDto employee);

    @Condition(appliesTo = {})
    default boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

}
