/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Target is a {@code @NullUnmarked} class nested inside a {@code @NullMarked} outer class.
 * The unannotated setter parameter must stay unknown nullability.
 */
@Mapper
public interface JSpecifyNullUnmarkedTargetScopeMapper {

    JSpecifyNullUnmarkedTargetScopeMapper INSTANCE =
        Mappers.getMapper( JSpecifyNullUnmarkedTargetScopeMapper.class );

    @Mapping(target = "value", source = "nullableValue")
    NullUnmarkedTargetBean.Inner map(SourceBean source);
}
