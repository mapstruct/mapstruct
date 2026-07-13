/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluepropertymapping.containertype;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
    nullValueIterablePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT,
    nullValueMapPropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT
)
public interface BeanMapperPrecedence {

    BeanMapperPrecedence INSTANCE = Mappers.getMapper( BeanMapperPrecedence.class );

    @Mapping(target = "tags", nullValueIterablePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(Bean source, @MappingTarget BeanDTO target);
}
