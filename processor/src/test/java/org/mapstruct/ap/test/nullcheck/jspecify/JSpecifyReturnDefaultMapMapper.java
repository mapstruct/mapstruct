/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.Map;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Map mapping where the return type is explicitly {@code @Nullable} but the
 * {@code NullValueMappingStrategy} is {@code RETURN_DEFAULT}:
 * {@code SourceMethod#getNullability} must force the method-level nullability to
 * {@code (NON_NULL, HARDCODED)} so that the generated implementation is NOT annotated
 * {@code @Nullable} on the return type.
 */
@NullMarked
@Mapper
public interface JSpecifyReturnDefaultMapMapper {

    JSpecifyReturnDefaultMapMapper INSTANCE =
        Mappers.getMapper( JSpecifyReturnDefaultMapMapper.class );

    @MapMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Nullable Map<String, NullMarkedTargetBean> mapAll(@Nullable Map<String, NullMarkedSourceBean> sources);

    NullMarkedTargetBean map(NullMarkedSourceBean source);
}
