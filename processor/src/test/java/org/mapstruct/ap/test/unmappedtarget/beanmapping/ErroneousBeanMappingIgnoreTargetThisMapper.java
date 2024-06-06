/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedtarget.beanmapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousBeanMappingIgnoreTargetThisMapper {

    ErroneousBeanMappingIgnoreTargetThisMapper INSTANCE =
            Mappers.getMapper( ErroneousBeanMappingIgnoreTargetThisMapper.class );

    @BeanMapping( ignoreTargets = { "." } )
    @Mapping( ignore = true, target = "address" )
    Target convert(Source source);
}
