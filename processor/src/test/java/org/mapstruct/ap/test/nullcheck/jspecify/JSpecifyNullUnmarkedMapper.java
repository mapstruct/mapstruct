/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Source is a {@code @NullUnmarked} class nested inside a {@code @NullMarked} outer class.
 * The unannotated getter must be treated as unknown nullability (not promoted to {@code @NonNull}).
 * With {@code NullValueCheckStrategy.ALWAYS}, a null check must be generated.
 */
@Mapper(
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface JSpecifyNullUnmarkedMapper {

    JSpecifyNullUnmarkedMapper INSTANCE = Mappers.getMapper( JSpecifyNullUnmarkedMapper.class );

    @Mapping(target = "unannotatedTarget", source = "value")
    TargetBean map(NullUnmarkedSourceBean.Inner source);
}
