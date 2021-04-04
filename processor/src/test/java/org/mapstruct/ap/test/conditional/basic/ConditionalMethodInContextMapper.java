/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ConditionalMethodInContextMapper {

    ConditionalMethodInContextMapper INSTANCE = Mappers.getMapper( ConditionalMethodInContextMapper.class );

    BasicEmployee map(BasicEmployeeDto employee, @Context PresenceUtils utils);

    class PresenceUtils {
        @Condition
        public boolean isNotBlank(String value) {
            return value != null && !value.trim().isEmpty();
        }
    }
}
