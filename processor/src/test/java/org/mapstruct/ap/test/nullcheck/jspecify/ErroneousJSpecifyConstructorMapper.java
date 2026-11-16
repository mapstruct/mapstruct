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
 * Erroneous mapper: maps @Nullable source to @NonNull constructor parameter without default value.
 */
@Mapper
public interface ErroneousJSpecifyConstructorMapper {

    ErroneousJSpecifyConstructorMapper INSTANCE = Mappers.getMapper( ErroneousJSpecifyConstructorMapper.class );

    @Mapping(target = "nonNullParam", source = "nullableValue")
    @Mapping(target = "nullableParam", source = "nullableValue")
    @Mapping(target = "unannotatedParam", source = "nonNullValue")
    ConstructorTargetBean map(SourceBean source);
}
