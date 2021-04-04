/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ConditionalMethodInMapper {

    ConditionalMethodInMapper INSTANCE = Mappers.getMapper( ConditionalMethodInMapper.class );

    BasicEmployee map(BasicEmployeeDto employee);

    @Condition
    default boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
