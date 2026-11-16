/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper with NullValueCheckStrategy.ALWAYS that should be overridden by JSpecify annotations.
 * Source @NonNull getter -> should NOT have null check despite ALWAYS strategy.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface JSpecifyOverridesStrategyMapper {

    JSpecifyOverridesStrategyMapper INSTANCE = Mappers.getMapper( JSpecifyOverridesStrategyMapper.class );

    @Mapping(target = "nonNullTarget", source = "nonNullValue")
    @Mapping(target = "nullableTarget", source = "nullableValue")
    @Mapping(target = "unannotatedTarget", source = "unannotatedValue")
    @Mapping(target = "nonNullTargetFromNullable", source = "nullableValue")
    TargetBean map(SourceBean source);
}
