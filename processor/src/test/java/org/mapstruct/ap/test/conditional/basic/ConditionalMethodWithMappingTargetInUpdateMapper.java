/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface ConditionalMethodWithMappingTargetInUpdateMapper {

    ConditionalMethodWithMappingTargetInUpdateMapper INSTANCE =
        Mappers.getMapper( ConditionalMethodWithMappingTargetInUpdateMapper.class );

    void map(BasicEmployeeDto employee, @MappingTarget BasicEmployee targetEmployee);

    @Condition
    default boolean isNotBlankAndNotPresent(String value, @MappingTarget BasicEmployee targetEmployee) {
        return value != null && !value.trim().isEmpty() && targetEmployee.getName() == null;
    }
}
