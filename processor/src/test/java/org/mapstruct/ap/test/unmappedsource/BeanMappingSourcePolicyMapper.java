/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedsource;

import org.mapstruct.ap.test.unmappedtarget.Source;
import org.mapstruct.ap.test.unmappedtarget.Target;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR
)
public interface BeanMappingSourcePolicyMapper {

    @BeanMapping(
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            unmappedSourcePolicy = ReportingPolicy.WARN
    )
    Target map(Source source);
    
}
