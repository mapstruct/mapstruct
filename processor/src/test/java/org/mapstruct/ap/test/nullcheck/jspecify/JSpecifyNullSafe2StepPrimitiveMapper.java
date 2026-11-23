/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JSpecifyNullSafe2StepPrimitiveMapper {

    JSpecifyNullSafe2StepPrimitiveMapper INSTANCE = Mappers.getMapper( JSpecifyNullSafe2StepPrimitiveMapper.class );

    @Named("stringToInteger")
    @Nullable
    default Integer stringToInteger(@Nullable String name) {
        return name == null ? null : Integer.valueOf( name );
    }

    @Mapping(target = "number", source = "name", qualifiedByName = "stringToInteger")
    JSpecifyNullSafe2StepPrimitiveTargetBean map(JSpecifyNullSafe2StepSourceBean source);
}
