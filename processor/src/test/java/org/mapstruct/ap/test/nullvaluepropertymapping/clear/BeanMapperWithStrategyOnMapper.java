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
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
public interface BeanMapperWithStrategyOnMapper {

    BeanMapperWithStrategyOnMapper INSTANCE = Mappers.getMapper( BeanMapperWithStrategyOnMapper.class );

    BeanDTO map(Bean source, @MappingTarget BeanDTO target);

    @Mapping(target = "id", source = "bean.id")
    @Mapping(target = "list", source = "bean.list")
    @Mapping(target = "map", source = "bean.map")
    BeanDTO map(NestedBean source, @MappingTarget BeanDTO target);
}
