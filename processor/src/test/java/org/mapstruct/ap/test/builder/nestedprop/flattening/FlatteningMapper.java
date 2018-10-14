/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.nestedprop.flattening;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FlatteningMapper {

    FlatteningMapper INSTANCE = Mappers.getMapper( FlatteningMapper.class );

    @Mapping(target = "articleCount", source = "count")
    @Mapping(target = "article1", source = "first.description")
    @Mapping(target = "article2", ignore = true)
    ImmutableFlattenedStock writeToFlatProperty(Stock source);
}
