/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedtarget;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BeanMappingSourceTargetMapper {

    BeanMappingSourceTargetMapper INSTANCE = Mappers.getMapper( BeanMappingSourceTargetMapper.class );

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.WARN)
    Target sourceToTarget(Source source);
}
