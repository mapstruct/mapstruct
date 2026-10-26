/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.targettype;

import java.time.LocalDate;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.TargetType;

@Mapper
public interface TargetTypeConditionImportMapper {

    Target map(Source source);

    @Condition
    default boolean shouldMap(LocalDate value, @TargetType Class<?> targetType) {
        return value != null && targetType == LocalDate.class;
    }
}
