/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4145;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface Issue4145Mapper {

    Issue4145Mapper INSTANCE = Mappers.getMapper( Issue4145Mapper.class );

    @Mapping(target = "list", expression = "java(java.util.Arrays.asList( \"a\", source.getName() ))")
    Target mapWithExpression(Source source);

    @Mapping(target = "list", constant = "c")
    Target mapWithConstant(Source source);
}
