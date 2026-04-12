/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for testing JSpecify @NonNull on source parameter.
 * The method-level null guard (if source == null return null) should be skipped.
 */
@Mapper
public interface JSpecifyNonNullParamMapper {

    JSpecifyNonNullParamMapper INSTANCE = Mappers.getMapper( JSpecifyNonNullParamMapper.class );

    @Mapping(target = "nonNullTarget", source = "nonNullValue")
    @Mapping(target = "nullableTarget", source = "nullableValue")
    @Mapping(target = "unannotatedTarget", source = "unannotatedValue")
    @Mapping(target = "nonNullTargetFromNullable", source = "nullableValue")
    TargetBean map(@NonNull SourceBean source);
}
