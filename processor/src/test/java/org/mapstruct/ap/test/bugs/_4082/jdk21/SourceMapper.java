/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4082.jdk21;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceMapper {

    SourceMapper INSTANCE = Mappers.getMapper( SourceMapper.class );

    @Mapping(source = "items.first", target = "first")
    @Mapping(source = "items.last", target = "last")
    Target map(Source source);
}
