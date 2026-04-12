/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for testing JSpecify nullness annotation support on property level.
 */
@Mapper
public interface JSpecifyNullCheckMapper {

    JSpecifyNullCheckMapper INSTANCE = Mappers.getMapper( JSpecifyNullCheckMapper.class );

    @Mapping(target = "nonNullTarget", source = "nonNullValue")
    @Mapping(target = "nullableTarget", source = "nullableValue")
    @Mapping(target = "unannotatedTarget", source = "unannotatedValue")
    @Mapping(target = "nonNullTargetFromNullable", source = "nullableValue")
    TargetBean map(SourceBean source);
}
