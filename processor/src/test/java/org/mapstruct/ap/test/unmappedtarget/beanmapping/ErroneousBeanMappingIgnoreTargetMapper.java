/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedtarget.beanmapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousBeanMappingIgnoreTargetMapper {

    ErroneousBeanMappingIgnoreTargetMapper INSTANCE =
            Mappers.getMapper( ErroneousBeanMappingIgnoreTargetMapper.class );

    @BeanMapping( ignoreTargets = { "unknownProperty" } )
    Target convert(Source source);
}
