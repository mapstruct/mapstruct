/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.abstractBuilder;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );

    @Mapping(target = "settlementPrice", source = "price")
    @Mapping(target = "issuer", constant = "true")
    ImmutableProduct fromMutable(ProductDto source);

    ProductDto fromImmutable(ImmutableProduct source);
}
