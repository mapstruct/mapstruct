/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.nestedprop.expanding;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpandingMapper {

    ExpandingMapper INSTANCE = Mappers.getMapper( ExpandingMapper.class );

    @Mapping(target = "articleCount", source = "count")
    @Mapping(target = "first.description", source = "article1")
    @Mapping(target = "second", ignore = true)
    ImmutableExpandedStock writeToNestedProperty(FlattenedStock source);
}
