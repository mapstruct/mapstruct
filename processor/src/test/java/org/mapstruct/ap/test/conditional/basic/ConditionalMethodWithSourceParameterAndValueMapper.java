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
public interface ConditionalMethodWithSourceParameterAndValueMapper {

    ConditionalMethodWithSourceParameterAndValueMapper INSTANCE = Mappers.getMapper(
        ConditionalMethodWithSourceParameterAndValueMapper.class );

    BasicEmployee map(BasicEmployeeDto employee);

    @Condition
    default boolean isPresent(BasicEmployeeDto source, String value) {
        if ( value == null ) {
            return false;
        }
        switch ( source.getStrategy() ) {
            case "blank":
                return !value.trim().isEmpty();
            case "empty":
                return !value.isEmpty();
            default:
                return true;
        }
    }

}
