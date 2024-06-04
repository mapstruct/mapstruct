/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3601.single;

import java.util.List;

import org.mapstruct.Condition;
import org.mapstruct.ConditionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.bugs._3601.Source;
import org.mapstruct.ap.test.bugs._3601.Target;
import org.mapstruct.factory.Mappers;

/**
 * @author Oliver Erhart
 */
@Mapper
public interface OnlyListMultiConditionMapper {

    OnlyListMultiConditionMapper INSTANCE = Mappers.getMapper( OnlyListMultiConditionMapper.class );

    @Mapping(target = "currentId", source = "source.uuid")
    @Mapping(target = "targetIds", source = "sourceIds")
    Target map(Source source, List<String> sourceIds);

    @Condition(appliesTo = { ConditionStrategy.PROPERTIES, ConditionStrategy.SOURCE_PARAMETERS })
    default boolean isNotEmpty(List<String> elements) {
        return elements != null && !elements.isEmpty();
    }

}