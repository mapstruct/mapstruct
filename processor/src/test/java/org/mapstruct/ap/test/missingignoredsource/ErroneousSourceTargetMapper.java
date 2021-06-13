/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.missingignoredsource;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ErroneousSourceTargetMapper {
    ErroneousSourceTargetMapper INSTANCE = Mappers.getMapper( ErroneousSourceTargetMapper.class );

    @BeanMapping(ignoreUnmappedSourceProperties = "bar")
    Object sourceToTarget(Object source);
}
