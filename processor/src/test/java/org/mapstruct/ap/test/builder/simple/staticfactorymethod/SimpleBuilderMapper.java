/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.simple.staticfactorymethod;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.builder.simple.SimpleMutablePerson;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface SimpleBuilderMapper {

    @Mappings({
        @Mapping(target = "name", source = "fullName"),
        @Mapping(target = "job", constant = "programmer"),
        @Mapping(target = "city", expression = "java(\"Bengalore\")")
    })
    SimpleImmutablePersonWithStaticFactoryMethodBuilder toImmutable(SimpleMutablePerson source);
}
