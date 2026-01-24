/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.lifecycle;

import java.util.Optional;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface OptionalToOptionalWithBuilderMapper {

    OptionalToOptionalWithBuilderMapper INSTANCE = Mappers.getMapper( OptionalToOptionalWithBuilderMapper.class );

    Optional<Target> map(Optional<Source> source, @Context MappingContext context);

}
