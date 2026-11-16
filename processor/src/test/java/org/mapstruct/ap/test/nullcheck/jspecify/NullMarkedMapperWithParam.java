/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper annotated with @NullMarked.
 * Source parameter is effectively @NonNull -> method-level null guard should be skipped.
 */
@NullMarked
@Mapper
public interface NullMarkedMapperWithParam {

    NullMarkedMapperWithParam INSTANCE = Mappers.getMapper( NullMarkedMapperWithParam.class );

    @Mapping(target = "nonNullByDefault", source = "nonNullByDefault")
    @Mapping(target = "explicitlyNullable", source = "explicitlyNullable")
    NullMarkedTargetBean map(NullMarkedSourceBean source);
}
