/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@NullMarked
@Mapper
public interface JSpecifyNonNullReturnBeanMapper {

    JSpecifyNonNullReturnBeanMapper INSTANCE = Mappers.getMapper( JSpecifyNonNullReturnBeanMapper.class );

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "nonNullByDefault", source = "value")
    NullMarkedTargetBean map(@Nullable JSpecifyNonNullReturnBeanSourceBean source);
}
