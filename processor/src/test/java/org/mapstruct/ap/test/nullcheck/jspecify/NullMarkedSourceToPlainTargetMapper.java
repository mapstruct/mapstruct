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
 * Maps from a @NullMarked source to a plain (unannotated) target.
 * Source unannotated getters are effectively @NonNull -> skip null check.
 * Source @Nullable getter to unannotated target -> defer to strategy.
 */
@Mapper
public interface NullMarkedSourceToPlainTargetMapper {

    NullMarkedSourceToPlainTargetMapper INSTANCE = Mappers.getMapper( NullMarkedSourceToPlainTargetMapper.class );

    @Mapping(target = "unannotatedTarget", source = "nonNullByDefault")
    @Mapping(target = "nullableTarget", source = "explicitlyNullable")
    @Mapping(target = "nonNullTarget", ignore = true)
    @Mapping(target = "nonNullTargetFromNullable", ignore = true)
    TargetBean map(NullMarkedSourceBean source);
}
