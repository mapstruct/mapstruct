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

import java.util.Collection;

/**
 * @author Nikola Ivačič
 */
@Mapper
public interface ConditionalMethodForCollectionMapperWithTargetPropertyName {

    ConditionalMethodForCollectionMapperWithTargetPropertyName INSTANCE
        = Mappers.getMapper( ConditionalMethodForCollectionMapperWithTargetPropertyName.class );

    Employee map(EmployeeDto employee);

    @Condition
    default <T> boolean isNotEmpty(Collection<T> collection, @TargetPropertyName String propName) {
        if ( "addresses".equalsIgnoreCase( propName ) ) {
            return false;
        }
        return collection != null && !collection.isEmpty();
    }

    @Condition
    default boolean isNotBlank(String value, @TargetPropertyName String propName) {
        if ( propName.equalsIgnoreCase( "lastName" ) ) {
            return false;
        }
        return value != null && !value.trim().isEmpty();
    }
}
