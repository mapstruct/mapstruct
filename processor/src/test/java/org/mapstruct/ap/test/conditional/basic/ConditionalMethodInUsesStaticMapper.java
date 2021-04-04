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
@Mapper(uses = ConditionalMethodInUsesStaticMapper.PresenceUtils.class)
public interface ConditionalMethodInUsesStaticMapper {

    ConditionalMethodInUsesStaticMapper INSTANCE = Mappers.getMapper( ConditionalMethodInUsesStaticMapper.class );

    BasicEmployee map(BasicEmployeeDto employee);

    interface PresenceUtils {

        @Condition
        static boolean isNotBlank(String value) {
            return value != null && !value.trim().isEmpty();
        }

    }
}
