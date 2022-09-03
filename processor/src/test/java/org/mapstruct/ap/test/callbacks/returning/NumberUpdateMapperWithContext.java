/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.returning;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Pascal Grün
 */
@Mapper( uses = NumberMapperContext.class )
public abstract class NumberUpdateMapperWithContext {
    public static final NumberUpdateMapperWithContext INSTANCE =
        Mappers.getMapper( NumberUpdateMapperWithContext.class );

    public abstract void integerToNumber(Integer number, @MappingTarget Number target);

    public abstract Map<String, Integer> longMapToIntegerMap(Map<String, Long> target);

    public abstract List<String> setToList(Set<Integer> target);
}
