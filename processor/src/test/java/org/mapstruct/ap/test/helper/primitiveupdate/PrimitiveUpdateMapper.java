/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.primitiveupdate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Same shape as {@code JSpecifyNullSafe2StepPrimitiveUpdateMapper}, but without JSpecify.
 */
@Mapper
public interface PrimitiveUpdateMapper {

    PrimitiveUpdateMapper INSTANCE = Mappers.getMapper( PrimitiveUpdateMapper.class );

    @Named("toInt")
    default Integer toInt(String name) {
        return name == null || name.isEmpty() ? null : Integer.valueOf( name );
    }

    @Mapping(target = "number", source = "name", qualifiedByName = "toInt")
    void update(PrimitiveUpdateSource source, @MappingTarget PrimitiveUpdateTarget target);
}
