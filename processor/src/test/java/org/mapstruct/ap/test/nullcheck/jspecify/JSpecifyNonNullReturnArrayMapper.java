/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@NullMarked
@Mapper
public interface JSpecifyNonNullReturnArrayMapper {

    JSpecifyNonNullReturnArrayMapper INSTANCE = Mappers.getMapper( JSpecifyNonNullReturnArrayMapper.class );

    NullMarkedTargetBean[] mapAll(@Nullable NullMarkedSourceBean[] sources);

    NullMarkedTargetBean map(NullMarkedSourceBean source);
}
