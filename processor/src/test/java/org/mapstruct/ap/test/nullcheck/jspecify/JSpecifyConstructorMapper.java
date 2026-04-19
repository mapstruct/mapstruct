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
 * Mapper with constructor target to test JSpecify on constructor parameters.
 * Only valid mappings: @NonNull source to @NonNull target, @Nullable to @Nullable.
 */
@Mapper
public interface JSpecifyConstructorMapper {

    JSpecifyConstructorMapper INSTANCE = Mappers.getMapper( JSpecifyConstructorMapper.class );

    @Mapping(target = "nonNullParam", source = "nonNullValue")
    @Mapping(target = "nullableParam", source = "nullableValue")
    @Mapping(target = "unannotatedParam", source = "unannotatedValue")
    ConstructorTargetBean map(SourceBean source);
}
