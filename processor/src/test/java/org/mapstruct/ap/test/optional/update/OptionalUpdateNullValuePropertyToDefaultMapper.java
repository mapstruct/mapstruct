/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.update;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface OptionalUpdateNullValuePropertyToDefaultMapper {

    OptionalUpdateNullValuePropertyToDefaultMapper INSTANCE = Mappers.getMapper(
        OptionalUpdateNullValuePropertyToDefaultMapper.class );

    void map(Source source, @MappingTarget Target target);
}
