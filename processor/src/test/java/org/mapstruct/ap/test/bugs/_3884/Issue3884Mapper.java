/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3884;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for testing null value property mapping strategy with Map properties.
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface Issue3884Mapper {
    Issue3884Mapper INSTANCE = Mappers.getMapper( Issue3884Mapper.class );

    void update(@MappingTarget DestinationType destination, SourceType source);
}
