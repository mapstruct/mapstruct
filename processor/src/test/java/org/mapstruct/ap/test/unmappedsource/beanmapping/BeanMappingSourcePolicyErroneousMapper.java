/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedsource.beanmapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN)
public interface BeanMappingSourcePolicyErroneousMapper {

    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.ERROR)
    Target map(Source source);

}
