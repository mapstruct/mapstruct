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
 * Mapper using @NullMarked source and target beans.
 */
@Mapper
public interface NullMarkedMapper {

    NullMarkedMapper INSTANCE = Mappers.getMapper( NullMarkedMapper.class );

    @Mapping(target = "nonNullByDefault", source = "nonNullByDefault")
    @Mapping(target = "explicitlyNullable", source = "explicitlyNullable")
    NullMarkedTargetBean map(NullMarkedSourceBean source);
}
