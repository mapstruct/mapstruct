/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedsource.beanmapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface BeanMappingSourcePolicyMapper {

    BeanMappingSourcePolicyMapper MAPPER =
            Mappers.getMapper( BeanMappingSourcePolicyMapper.class );

    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.WARN)
    Target map(Source source);

    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    Target map2(Source source);

}
