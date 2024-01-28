/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.propertyname.sourcepropertyname;

import java.util.LinkedHashSet;
import java.util.Set;

import org.mapstruct.Condition;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SourcePropertyName;
import org.mapstruct.ap.test.conditional.propertyname.DomainModel;
import org.mapstruct.ap.test.conditional.propertyname.Employee;
import org.mapstruct.ap.test.conditional.propertyname.EmployeeDto;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 * @author Nikola Ivačič
 * @author Mohammad Al Zouabi
 * @author Oliver Erhart
 */
@Mapper
public interface ConditionalMethodInMapperWithAllExceptTarget {

    ConditionalMethodInMapperWithAllExceptTarget INSTANCE
        = Mappers.getMapper( ConditionalMethodInMapperWithAllExceptTarget.class );

    class PresenceUtils {
        Set<String> visited = new LinkedHashSet<>();
        Set<String> visitedSources = new LinkedHashSet<>();
    }

    @Mapping(target = "country", source = "originCountry")
    @Mapping(target = "addresses", source = "originAddresses")
    Employee map(EmployeeDto employee, @Context PresenceUtils utils);

    @Condition
    default boolean isNotBlank(String value,
                               DomainModel source,
                               @SourcePropertyName String propName,
                               @Context PresenceUtils utils) {
        utils.visited.add( propName );
        utils.visitedSources.add( source.getClass().getSimpleName() );
        if ( propName.equalsIgnoreCase( "firstName" ) ) {
            return true;
        }
        return value != null && !value.trim().isEmpty();
    }
}
