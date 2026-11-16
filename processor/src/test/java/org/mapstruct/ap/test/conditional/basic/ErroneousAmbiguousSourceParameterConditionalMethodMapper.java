/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Mapper;
import org.mapstruct.SourceParameterCondition;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousAmbiguousSourceParameterConditionalMethodMapper {

    BasicEmployee map(BasicEmployeeDto employee);

    @SourceParameterCondition
    default boolean hasName(BasicEmployeeDto value) {
        return value != null && value.getName() != null;
    }

    @SourceParameterCondition
    default boolean hasStrategy(BasicEmployeeDto value) {
        return value != null && value.getStrategy() != null;
    }
}
