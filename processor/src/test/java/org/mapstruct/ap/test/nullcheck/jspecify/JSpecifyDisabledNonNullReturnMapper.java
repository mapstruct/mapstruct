/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.List;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Dedicated to {@link JSpecifyDisabledTest} so its generated implementation is not shared (and pre-loaded) by an
 * enabled test in the same JVM. With JSpecify enabled the {@code @NonNull} return would force RETURN_DEFAULT.
 */
@NullMarked
@Mapper
public interface JSpecifyDisabledNonNullReturnMapper {

    JSpecifyDisabledNonNullReturnMapper INSTANCE =
        Mappers.getMapper( JSpecifyDisabledNonNullReturnMapper.class );

    List<NullMarkedTargetBean> mapAll(@Nullable List<NullMarkedSourceBean> sources);

    NullMarkedTargetBean map(NullMarkedSourceBean source);
}
