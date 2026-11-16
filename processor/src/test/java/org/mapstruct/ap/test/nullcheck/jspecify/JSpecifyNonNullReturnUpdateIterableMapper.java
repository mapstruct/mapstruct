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
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@NullMarked
@Mapper
public interface JSpecifyNonNullReturnUpdateIterableMapper {

    JSpecifyNonNullReturnUpdateIterableMapper INSTANCE =
        Mappers.getMapper( JSpecifyNonNullReturnUpdateIterableMapper.class );

    List<NullMarkedTargetBean> mapAll(@Nullable List<NullMarkedSourceBean> sources,
                                      @MappingTarget List<NullMarkedTargetBean> target);

    NullMarkedTargetBean map(NullMarkedSourceBean source);
}
