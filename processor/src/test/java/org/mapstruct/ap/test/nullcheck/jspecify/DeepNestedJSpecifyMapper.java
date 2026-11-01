/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper whose source and target types are nested static inner classes three levels deep.
 */
@Mapper
public interface DeepNestedJSpecifyMapper {

    DeepNestedJSpecifyMapper INSTANCE = Mappers.getMapper( DeepNestedJSpecifyMapper.class );

    DeepNestedTargetBean.Middle.@NonNull Inner mapNonNull(DeepNestedSourceBean.Middle.@NonNull Inner source);

    @NullMarked
    DeepNestedTargetBean.Middle.@Nullable Inner mapNullable(DeepNestedSourceBean.Middle.@Nullable Inner source);
}
