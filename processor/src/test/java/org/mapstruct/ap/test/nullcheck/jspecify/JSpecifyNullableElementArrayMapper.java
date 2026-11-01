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

/**
 * Form A (JSpecify): {@code @Nullable T[]} means the <em>array itself</em> is non-null but the
 * <em>elements</em> may be null. In contrast to {@link JSpecifyNonNullReturnArrayMapper}
 * (form {@code T @Nullable []}), here no array-level null check is expected, but a per-element
 * null check is, because the scalar {@code map} method requires a non-null argument.
 */
@NullMarked
@Mapper
public interface JSpecifyNullableElementArrayMapper {

    JSpecifyNullableElementArrayMapper INSTANCE = Mappers.getMapper( JSpecifyNullableElementArrayMapper.class );

    @Nullable NullMarkedTargetBean[] map( @Nullable NullMarkedSourceBean[] sources );

    NullMarkedTargetBean[]@Nullable[] map2dim(NullMarkedSourceBean[]@Nullable[] sourceBeans);

    NullMarkedTargetBean map( NullMarkedSourceBean source );
}
