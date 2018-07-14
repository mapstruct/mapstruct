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
public abstract class NumberMapperWithContext {
    public static final NumberMapperWithContext INSTANCE = Mappers.getMapper( NumberMapperWithContext.class );

    public abstract Number integerToNumber(Integer number);

    public abstract void integerToNumber(Integer number, @MappingTarget Number target);

    public abstract Map<String, Integer> longMapToIntegerMap(Map<String, Long> target);

    public abstract List<String> setToList(Set<Integer> target);
}
