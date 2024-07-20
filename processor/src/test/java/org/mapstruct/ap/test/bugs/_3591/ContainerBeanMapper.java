/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3591;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContainerBeanMapper {

    ContainerBeanMapper INSTANCE = Mappers.getMapper( ContainerBeanMapper.class );

    @Mapping(source = "beanMap", target = "beanMap")
    @Mapping(source = "beanStream", target = "beanStream")
    ContainerBeanDto mapWithMapMapping(ContainerBean containerBean, @MappingTarget ContainerBeanDto containerBeanDto);

}
