/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.targetpropertyname;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Condition;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.TargetPropertyName;
import org.mapstruct.factory.Mappers;

import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author Nikola Ivačič
 */
@Mapper
public interface ConditionalMethodWithTargetPropertyNameInContextMapper {

    ConditionalMethodWithTargetPropertyNameInContextMapper INSTANCE
        = Mappers.getMapper( ConditionalMethodWithTargetPropertyNameInContextMapper.class );

    Employee map(EmployeeDto employee, @Context PresenceUtils utils);

    Address map(AddressDto addressDto, @Context PresenceUtils utils);

    class PresenceUtils {
        Set<String> visited = new LinkedHashSet<>();

        @Condition
        public boolean isNotBlank(String value, @TargetPropertyName String propName) {
            visited.add( propName );
            return value != null && !value.trim().isEmpty();
        }
    }

    Employee map(EmployeeDto employee, @Context PresenceUtilsAllProps utils);

    Address map(AddressDto addressDto, @Context PresenceUtilsAllProps utils);

    class PresenceUtilsAllProps {
        Set<String> visited = new LinkedHashSet<>();

        @Condition
        public boolean collect(@TargetPropertyName String propName) {
            visited.add( propName );
            return true;
        }
    }

    Employee map(EmployeeDto employee, @Context PresenceUtilsAllPropsWithSource utils);

    Address map(AddressDto addressDto, @Context PresenceUtilsAllPropsWithSource utils);

    @BeforeMapping
    default void before(DomainModel source, @Context PresenceUtilsAllPropsWithSource utils) {
        String lastProp = utils.visitedSegments.peekLast();
        if ( lastProp != null && source != null ) {
            utils.path.offerLast( lastProp );
        }
    }

    @AfterMapping
    default void after(@Context PresenceUtilsAllPropsWithSource utils) {
        utils.path.pollLast();
    }

    class PresenceUtilsAllPropsWithSource {
        Deque<String> visitedSegments = new LinkedList<>();
        Deque<String> visited = new LinkedList<>();
        Deque<String> path = new LinkedList<>();

        @Condition
        public boolean collect(@TargetPropertyName String propName) {
            visitedSegments.offerLast( propName );
            path.offerLast( propName );
            visited.offerLast( String.join( ".", path ) );
            path.pollLast();
            return true;
        }
    }
}
