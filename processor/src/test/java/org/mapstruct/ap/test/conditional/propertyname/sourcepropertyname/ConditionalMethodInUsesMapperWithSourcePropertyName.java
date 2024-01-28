/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.propertyname.sourcepropertyname;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SourcePropertyName;
import org.mapstruct.ap.test.conditional.propertyname.Employee;
import org.mapstruct.ap.test.conditional.propertyname.EmployeeDto;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 * @author Nikola Ivačič
 * @author Mohammad Al Zouabi
 * @author Oliver Erhart
 */
@Mapper(uses = ConditionalMethodInUsesMapperWithSourcePropertyName.PresenceUtils.class)
public interface ConditionalMethodInUsesMapperWithSourcePropertyName {

    ConditionalMethodInUsesMapperWithSourcePropertyName INSTANCE
        = Mappers.getMapper( ConditionalMethodInUsesMapperWithSourcePropertyName.class );

    @Mapping(target = "country", source = "originCountry")
    @Mapping(target = "addresses", source = "originAddresses")
    Employee map(EmployeeDto employee);

    class PresenceUtils {

        @Condition
        public boolean isNotBlank(String value, @SourcePropertyName String propName) {
            if ( propName.equalsIgnoreCase( "originCountry" ) ) {
                return false;
            }
            return value != null && !value.trim().isEmpty();
        }

    }
}
