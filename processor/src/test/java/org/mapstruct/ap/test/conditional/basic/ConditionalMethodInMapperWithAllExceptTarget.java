/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.TargetPropertyName;
import org.mapstruct.factory.Mappers;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Filip Hrisafov
 * @author Nikola Ivačič
 */
@Mapper
public interface ConditionalMethodInMapperWithAllExceptTarget {

    ConditionalMethodInMapperWithAllExceptTarget INSTANCE
            = Mappers.getMapper( ConditionalMethodInMapperWithAllExceptTarget.class );

    class PresenceUtils {
        Set<String> visited = new LinkedHashSet<>();
        Set<String> visitedSources = new LinkedHashSet<>();
    }

    TargetPropertyNameModel.Employee map(TargetPropertyNameModel.EmployeeDto employee, @Context PresenceUtils utils);

    @Condition
    default boolean isNotBlank(String value,
                               TargetPropertyNameModel source,
                               @TargetPropertyName String propName,
                               @Context PresenceUtils utils) {
        utils.visited.add( propName );
        utils.visitedSources.add( source.getClass().getSimpleName() );
        if ( propName.equalsIgnoreCase( "firstName" ) ) {
            return true;
        }
        return value != null && !value.trim().isEmpty();
    }
}
