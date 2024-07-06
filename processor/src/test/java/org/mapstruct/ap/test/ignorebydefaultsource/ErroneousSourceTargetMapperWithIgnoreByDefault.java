/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignorebydefaultsource;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface ErroneousSourceTargetMapperWithIgnoreByDefault {
    ErroneousSourceTargetMapperWithIgnoreByDefault INSTANCE = Mappers.getMapper(
        ErroneousSourceTargetMapperWithIgnoreByDefault.class );

    @Mapping(source = "one", target = "one")
    @BeanMapping(ignoreByDefault = true)
    Target sourceToTarget(Source source);
}
