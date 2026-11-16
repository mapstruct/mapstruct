/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface annotated with {@code @NullMarked} but the mapping method itself is
 * {@code @NullUnmarked}. The unannotated source parameter inside the {@code @NullUnmarked}
 * method must be treated as unknown nullability, so with {@code NullValueCheckStrategy.ALWAYS}
 * the method-level null guard must still be generated.
 */
@NullMarked
@Mapper(
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface JSpecifyNullUnmarkedMethodMapper {

    JSpecifyNullUnmarkedMethodMapper INSTANCE = Mappers.getMapper( JSpecifyNullUnmarkedMethodMapper.class );

    @NullUnmarked
    @Mapping(target = "unannotatedTarget", source = "unannotatedValue")
    TargetBean map(SourceBean source);
}
