/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1968;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue1968ConditionMapper {

    Issue1968ConditionMapper INSTANCE = Mappers.getMapper( Issue1968ConditionMapper.class );

    @Mapping(target = "specialNames", source = "otherNames", conditionQualifiedByName = "notEmpty")
    Animal dtoToEntity(AnimalDto dto);

    @Condition
    @Named("notEmpty")
    default boolean isNotEmpty(String[] values) {
        return values != null && values.length > 0;
    }
}
