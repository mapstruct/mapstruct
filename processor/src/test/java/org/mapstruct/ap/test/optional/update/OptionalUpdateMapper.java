/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.update;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface OptionalUpdateMapper {

    OptionalUpdateMapper INSTANCE = Mappers.getMapper( OptionalUpdateMapper.class );

    void map(Source source, @MappingTarget Target target);
}
