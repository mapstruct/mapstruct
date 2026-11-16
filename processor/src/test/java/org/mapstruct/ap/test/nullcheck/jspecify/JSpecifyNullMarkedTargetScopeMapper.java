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
 * Target is a {@code @NullMarked} bean with an unannotated setter parameter
 * ({@code setNonNullByDefault}), which JSpecify semantics promote to {@code @NonNull}.
 * The source getter is explicitly {@code @Nullable}, so a null check must be generated.
 */
@Mapper
public interface JSpecifyNullMarkedTargetScopeMapper {

    JSpecifyNullMarkedTargetScopeMapper INSTANCE =
        Mappers.getMapper( JSpecifyNullMarkedTargetScopeMapper.class );

    @Mapping(target = "nonNullByDefault", source = "nullableValue")
    @Mapping(target = "explicitlyNullable", ignore = true)
    NullMarkedTargetBean map(SourceBean source);
}
