/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Erroneous mapper: a {@code @Nullable} method parameter is mapped directly (no property entries)
 * to a {@code @NonNull} constructor parameter without {@code defaultValue}.
 */
@Mapper
public interface ErroneousJSpecifyDirectParamConstructorMapper {

    ErroneousJSpecifyDirectParamConstructorMapper INSTANCE =
        Mappers.getMapper( ErroneousJSpecifyDirectParamConstructorMapper.class );

    @Mapping(target = "nonNullParam", source = "nullableValue")
    @Mapping(target = "nullableParam", source = "nullableValue")
    @Mapping(target = "unannotatedParam", source = "nullableValue")
    ConstructorTargetBean map(@Nullable String nullableValue);
}
