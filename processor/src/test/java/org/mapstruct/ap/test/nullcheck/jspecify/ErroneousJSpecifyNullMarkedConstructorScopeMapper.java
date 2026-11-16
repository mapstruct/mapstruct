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
 * Maps an explicitly {@code @Nullable} source property to an unannotated constructor
 * parameter in a {@code @NullMarked} target class.
 */
@Mapper
public interface ErroneousJSpecifyNullMarkedConstructorScopeMapper {

    ErroneousJSpecifyNullMarkedConstructorScopeMapper INSTANCE =
        Mappers.getMapper( ErroneousJSpecifyNullMarkedConstructorScopeMapper.class );

    @Mapping(target = "value", source = "nullableValue")
    NullMarkedConstructorTargetBean map(SourceBean source);
}
