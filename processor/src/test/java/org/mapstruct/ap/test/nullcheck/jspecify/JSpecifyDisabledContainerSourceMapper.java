/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.List;

import org.jspecify.annotations.NullMarked;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Dedicated to {@link JSpecifyDisabledTest} so its generated implementation is not shared (and pre-loaded) by an
 * enabled test in the same JVM. With JSpecify enabled the {@code @NonNull} source would skip the method-level guard.
 */
@NullMarked
@Mapper
public interface JSpecifyDisabledContainerSourceMapper {

    JSpecifyDisabledContainerSourceMapper INSTANCE =
        Mappers.getMapper( JSpecifyDisabledContainerSourceMapper.class );

    List<NullMarkedTargetBean> mapAll(List<NullMarkedSourceBean> sources);

    NullMarkedTargetBean map(NullMarkedSourceBean source);
}
