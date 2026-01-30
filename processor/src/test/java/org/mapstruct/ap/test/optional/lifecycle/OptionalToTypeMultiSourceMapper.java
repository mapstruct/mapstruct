/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.lifecycle;

import java.util.Optional;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface OptionalToTypeMultiSourceMapper {

    OptionalToTypeMultiSourceMapper INSTANCE = Mappers.getMapper( OptionalToTypeMultiSourceMapper.class );

    @BeanMapping(builder = @Builder(disableBuilder = true))
    Target map(Optional<Source> source, String otherValue, @Context MappingContext context);

}
