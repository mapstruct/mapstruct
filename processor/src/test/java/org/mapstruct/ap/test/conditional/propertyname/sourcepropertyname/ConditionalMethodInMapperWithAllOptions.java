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
import org.mapstruct.MappingTarget;
import org.mapstruct.SourcePropertyName;
import org.mapstruct.TargetPropertyName;
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
public interface ConditionalMethodInMapperWithAllOptions {

    ConditionalMethodInMapperWithAllOptions INSTANCE
        = Mappers.getMapper( ConditionalMethodInMapperWithAllOptions.class );

    class PresenceUtils {
        Set<String> visitedSourceNames = new LinkedHashSet<>();
        Set<String> visitedTargetNames = new LinkedHashSet<>();
        Set<String> visitedSources = new LinkedHashSet<>();
        Set<String> visitedTargets = new LinkedHashSet<>();
    }

    @Mapping(target = "country", source = "originCountry")
    @Mapping(target = "addresses", source = "originAddresses")
    void map(EmployeeDto employeeDto,
             @MappingTarget Employee employee,
             @Context PresenceUtils utils);

    @Condition
    default boolean isNotBlank(String value,
                               DomainModel source,
                               @MappingTarget DomainModel target,
                               @SourcePropertyName String sourcePropName,
                               @TargetPropertyName String targetPropName,
                               @Context PresenceUtils utils) {
        utils.visitedSourceNames.add( sourcePropName );
        utils.visitedTargetNames.add( targetPropName );
        utils.visitedSources.add( source.getClass().getSimpleName() );
        utils.visitedTargets.add( target.getClass().getSimpleName() );
        if ( sourcePropName.equalsIgnoreCase( "lastName" ) ) {
            return false;
        }
        return value != null && !value.trim().isEmpty();
    }
}
