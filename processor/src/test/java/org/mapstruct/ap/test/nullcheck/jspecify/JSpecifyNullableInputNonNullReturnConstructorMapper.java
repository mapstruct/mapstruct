/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JSpecifyNullableInputNonNullReturnConstructorMapper {

    JSpecifyNullableInputNonNullReturnConstructorMapper INSTANCE =
        Mappers.getMapper( JSpecifyNullableInputNonNullReturnConstructorMapper.class );

    @Mapping(target = "value", source = "value")
    ReusedConstructorTarget map(ReusedConstructorSource source);

    default @NonNull ReusedTargetValue map(@Nullable ReusedSourceValue source) {
        return new ReusedTargetValue( source == null ? "fallback" : source.value() );
    }
}
