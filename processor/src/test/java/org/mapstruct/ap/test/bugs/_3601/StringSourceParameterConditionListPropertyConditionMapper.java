/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3601;

import java.util.List;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SourceParameterCondition;
import org.mapstruct.factory.Mappers;

/**
 * @author Oliver Erhart
 */
@Mapper
public interface StringSourceParameterConditionListPropertyConditionMapper {

    StringSourceParameterConditionListPropertyConditionMapper INSTANCE = Mappers.getMapper( StringSourceParameterConditionListPropertyConditionMapper.class );

    @Mapping(target = "currentId", source = "source.uuid")
    @Mapping(target = "targetIds", source = "sourceIds")
    Target map(Source source, List<String> sourceIds);

    @SourceParameterCondition
    default boolean stringCondition(String str) {
        return str != null;
    }

    @Condition
    default boolean isNotEmpty(List<String> elements) {
        return elements != null && !elements.isEmpty();
    }

}
