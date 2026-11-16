/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.simple;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface OptionalToTypeMapper {

    OptionalToTypeMapper INSTANCE = Mappers.getMapper( OptionalToTypeMapper.class );

    Target map(Optional<Source> source);

}
