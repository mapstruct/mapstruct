/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluepropertymapping.clear;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeanMapper {

    BeanMapper INSTANCE = Mappers.getMapper( BeanMapper.class );

    @Mapping(target = "list", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
    @Mapping(target = "map", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
    BeanDTO map(Bean source, @MappingTarget BeanDTO target);

    @Mapping(target = "id", source = "bean.id")
    @Mapping(target = "list", source = "bean.list",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
    @Mapping(target = "map", source = "bean.map",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
    BeanDTO map(NestedBean source, @MappingTarget BeanDTO target);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
    BeanDTO mapWithBeanMapping(Bean source, @MappingTarget BeanDTO target);

}
