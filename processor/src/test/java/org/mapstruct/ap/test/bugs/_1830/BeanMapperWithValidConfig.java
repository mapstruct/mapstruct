/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1830;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeanMapperWithValidConfig {

    BeanMapperWithValidConfig INSTANCE = Mappers.getMapper( BeanMapperWithValidConfig.class );

    @Mapping(source = "list", target = "list", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
    BeanDTO map(Bean source, @MappingTarget BeanDTO target);

    @Mapping(source = "bean.list", target = "list", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
    BeanDTO map(NestedBean source, @MappingTarget BeanDTO target);

}
