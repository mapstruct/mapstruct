/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Maps an explicitly {@code @Nullable} source property inside a {@code @NullMarked} scope
 * to a {@code @NonNull} setter parameter. A null check must be generated because the
 * explicit {@code @Nullable} overrides the enclosing {@code @NullMarked} scope, keeping
 * the source nullability as {@code NULLABLE} instead of being promoted to {@code NON_NULL}.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JSpecifyNullableOverridesScopeMapper {

    JSpecifyNullableOverridesScopeMapper INSTANCE =
        Mappers.getMapper( JSpecifyNullableOverridesScopeMapper.class );

    @Mapping(target = "nonNullTargetFromNullable", source = "explicitlyNullable")
    TargetBean map(NullMarkedSourceBean source);
}
