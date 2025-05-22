/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3809;

import org.mapstruct.Condition;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetPropertyName;

@org.mapstruct.Mapper
public interface ClassMapper {
    void updateMappingFails(ClassA source, @MappingTarget ClassB target);

    @Condition
    default boolean canMap(Object source, @TargetPropertyName String propertyName) {
        return true;
    }
}
