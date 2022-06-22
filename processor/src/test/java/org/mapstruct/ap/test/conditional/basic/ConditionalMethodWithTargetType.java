/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface ConditionalMethodWithTargetType {

    ConditionalMethodWithTargetType INSTANCE = Mappers.getMapper( ConditionalMethodWithTargetType.class );

    BasicEmployee map(BasicEmployeeDto employee);

    @Condition
    default boolean isNotBlank(String value, @TargetType Class<?> targetType) {
        return value != null && !value.trim().isEmpty();
    }
}
