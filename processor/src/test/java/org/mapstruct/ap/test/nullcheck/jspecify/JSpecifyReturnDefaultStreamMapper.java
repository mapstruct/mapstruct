/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.stream.Stream;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Stream mapping where the return type is explicitly {@code @Nullable} but the
 * {@code NullValueMappingStrategy} is {@code RETURN_DEFAULT}:
 * {@code SourceMethod#getNullability} must force the method-level nullability to
 * {@code (NON_NULL, HARDCODED)} so that the generated implementation is NOT annotated
 * {@code @Nullable} on the return type.
 */
@NullMarked
@Mapper
public interface JSpecifyReturnDefaultStreamMapper {

    JSpecifyReturnDefaultStreamMapper INSTANCE =
        Mappers.getMapper( JSpecifyReturnDefaultStreamMapper.class );

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Nullable Stream<NullMarkedTargetBean> mapAll(@Nullable Stream<NullMarkedSourceBean> sources);

    NullMarkedTargetBean map(NullMarkedSourceBean source);
}
