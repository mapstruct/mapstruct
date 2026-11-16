/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.simple;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface OptionalToOptionalNullValueDefaultMapper {

    OptionalToOptionalNullValueDefaultMapper INSTANCE = Mappers.getMapper(
        OptionalToOptionalNullValueDefaultMapper.class );

    Optional<Target> map(Optional<Source> source);

}
