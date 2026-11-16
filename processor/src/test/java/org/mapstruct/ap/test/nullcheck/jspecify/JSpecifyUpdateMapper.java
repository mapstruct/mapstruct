/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper with update method and IGNORE strategy to test JSpecify interaction.
 * NVPMS=IGNORE means: when source is null, leave target unchanged.
 * Source @NonNull should still skip the null check (guaranteed non-null).
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface JSpecifyUpdateMapper {

    JSpecifyUpdateMapper INSTANCE = Mappers.getMapper( JSpecifyUpdateMapper.class );

    @Mapping(target = "nonNullTarget", source = "nonNullValue")
    @Mapping(target = "nullableTarget", source = "nullableValue")
    @Mapping(target = "unannotatedTarget", source = "unannotatedValue")
    @Mapping(target = "nonNullTargetFromNullable", source = "nullableValue")
    void update(SourceBean source, @MappingTarget TargetBean target);
}
