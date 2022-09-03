/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.off;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface SimpleWithBuilderMapper {

    @Mapping(target = "name", source = "fullName")
    SimpleNotRealyImmutablePerson toNotRealyImmutable(SimpleMutablePerson source);
}
