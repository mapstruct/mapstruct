/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluepropertymapping.clear;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
public interface BeanMapperWithNullValuePropertyMappingStrategy {

    @Mapping(source = "list", target = "list")
    BeanDTO map(Bean source, @MappingTarget BeanDTO target);

    @Mapping(source = "bean.list", target = "list")
    BeanDTO map(NestedBean source, @MappingTarget BeanDTO target);
}
