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
 * A {@code defaultValue} on the mapping of a {@code @Nullable} source to a {@code @NonNull}
 * constructor parameter must suppress the "potentially nullable source" compile error.
 */
@Mapper
public interface JSpecifyConstructorDefaultValueMapper {

    JSpecifyConstructorDefaultValueMapper INSTANCE =
        Mappers.getMapper( JSpecifyConstructorDefaultValueMapper.class );

    @Mapping(target = "nonNullParam", source = "nullableValue", defaultValue = "fallback")
    @Mapping(target = "nullableParam", source = "nullableValue")
    @Mapping(target = "unannotatedParam", source = "nonNullValue")
    ConstructorTargetBean map(SourceBean source);
}
