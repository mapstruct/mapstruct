/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.targetpropertyname;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.TargetPropertyName;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 * @author Nikola Ivačič
 */
@Mapper(uses = ConditionalMethodInUsesMapperWithTargetPropertyName.PresenceUtils.class)
public interface ConditionalMethodInUsesMapperWithTargetPropertyName {

    ConditionalMethodInUsesMapperWithTargetPropertyName INSTANCE
        = Mappers.getMapper( ConditionalMethodInUsesMapperWithTargetPropertyName.class );

    Employee map(EmployeeDto employee);

    class PresenceUtils {

        @Condition
        public boolean isNotBlank(String value, @TargetPropertyName String propName) {
            if ( propName.equalsIgnoreCase( "lastName" ) ) {
                return false;
            }
            return value != null && !value.trim().isEmpty();
        }

    }
}
