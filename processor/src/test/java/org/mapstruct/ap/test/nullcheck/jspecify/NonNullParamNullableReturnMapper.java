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
public interface NonNullParamNullableReturnMapper {

    NonNullParamNullableReturnMapper INSTANCE = Mappers.getMapper( NonNullParamNullableReturnMapper.class );

    @Nullable
    default String transform( @NonNull String s ) {
        return "toNull".equals( s ) ? null : s;
    }

    @Mapping(target = "unannotatedTarget", ignore = true)
    @Mapping(target = "nullableTarget", ignore = true)
    @Mapping(target = "nonNullTarget", ignore = true)
    @Mapping( target = "nonNullTargetFromNullable", source = "nullableValue" )
    TargetBean map( SourceBean source );
}
