/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.List;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Iterable mapping where the return type is explicitly {@code @Nullable} but the
 * {@code NullValueMappingStrategy} is {@code RETURN_DEFAULT}:
 * {@code SourceMethod#getNullability} must force the method-level nullability to
 * {@code (NON_NULL, HARDCODED)} so that the generated implementation is NOT annotated
 * {@code @Nullable} on the return type.
 */
@NullMarked
@Mapper
public interface JSpecifyReturnDefaultIterableMapper {

    JSpecifyReturnDefaultIterableMapper INSTANCE =
        Mappers.getMapper( JSpecifyReturnDefaultIterableMapper.class );

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Nullable List<NullMarkedTargetBean> mapAll(@Nullable List<NullMarkedSourceBean> sources);

    NullMarkedTargetBean map(NullMarkedSourceBean source);
}
