/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.targetpropertyname;

import org.mapstruct.Condition;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetPropertyName;
import org.mapstruct.factory.Mappers;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Filip Hrisafov
 * @author Nikola Ivačič
 */
@Mapper
public interface ConditionalMethodInMapperWithAllOptions {

    ConditionalMethodInMapperWithAllOptions INSTANCE
        = Mappers.getMapper( ConditionalMethodInMapperWithAllOptions.class );

    class PresenceUtils {
        Set<String> visited = new LinkedHashSet<>();
        Set<String> visitedSources = new LinkedHashSet<>();
        Set<String> visitedTargets = new LinkedHashSet<>();
    }

    void map(EmployeeDto employeeDto,
             @MappingTarget Employee employee,
             @Context PresenceUtils utils);

    @Condition
    default boolean isNotBlank(String value,
                               DomainModel source,
                               @MappingTarget DomainModel target,
                               @TargetPropertyName String propName,
                               @Context PresenceUtils utils) {
        utils.visited.add( propName );
        utils.visitedSources.add( source.getClass().getSimpleName() );
        utils.visitedTargets.add( target.getClass().getSimpleName() );
        if ( propName.equalsIgnoreCase( "lastName" ) ) {
            return false;
        }
        return value != null && !value.trim().isEmpty();
    }
}
