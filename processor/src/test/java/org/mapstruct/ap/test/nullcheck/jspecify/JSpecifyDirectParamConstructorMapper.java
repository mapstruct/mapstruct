/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper that maps direct method parameters (no property entries) to constructor parameters.
 * Verifies that a {@code @NonNull} source parameter is accepted by a {@code @NonNull}
 * constructor parameter target.
 */
@Mapper
public interface JSpecifyDirectParamConstructorMapper {

    JSpecifyDirectParamConstructorMapper INSTANCE = Mappers.getMapper( JSpecifyDirectParamConstructorMapper.class );

    @Mapping(target = "nonNullParam", source = "nonNullValue")
    @Mapping(target = "nullableParam", source = "nullableValue")
    @Mapping(target = "unannotatedParam", source = "unannotatedValue")
    ConstructorTargetBean map(@NonNull String nonNullValue,
                              @Nullable String nullableValue,
                              String unannotatedValue);
}
