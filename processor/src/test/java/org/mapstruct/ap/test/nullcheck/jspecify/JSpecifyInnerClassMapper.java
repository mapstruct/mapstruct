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
 * Mapper whose source and target types are static inner classes.
 */
@Mapper
public interface JSpecifyInnerClassMapper {

    JSpecifyInnerClassMapper INSTANCE = Mappers.getMapper( JSpecifyInnerClassMapper.class );

    InnerClassTargetBean.@NonNull Inner mapNonNull(InnerClassSourceBean.@NonNull Inner source);

    @NullMarked
    InnerClassTargetBean.@Nullable Inner mapNullable(InnerClassSourceBean.@Nullable Inner source);
}
