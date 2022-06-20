/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2901;

import java.util.List;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.TargetType;

@Mapper
public interface ConditionWithTargetTypeOnCollectionMapper {

    Target map(Source source);

    @Condition
    default boolean check(List<String> test, @TargetType Class<?> type) {
        return type.isInstance( test );
    }
}
