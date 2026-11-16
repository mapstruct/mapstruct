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

@NullMarked
@Mapper
public interface JSpecifyReturnNullOverrideIterableMapper {

    JSpecifyReturnNullOverrideIterableMapper INSTANCE =
        Mappers.getMapper( JSpecifyReturnNullOverrideIterableMapper.class );

    // Explicit RETURN_NULL, but the @NonNull return type (NullMarked scope) wins and forces RETURN_DEFAULT.
    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<NullMarkedTargetBean> mapAll(@Nullable List<NullMarkedSourceBean> sources);

    NullMarkedTargetBean map(NullMarkedSourceBean source);
}
