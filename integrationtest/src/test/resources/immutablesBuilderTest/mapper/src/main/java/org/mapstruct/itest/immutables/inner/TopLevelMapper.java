/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.inner;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.mapstruct.itest.immutables.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TopLevelMapper {

    TopLevelMapper INSTANCE = Mappers.getMapper( TopLevelMapper.class );

    TopLevel toImmutable(TopLevelDto dto);
    TopLevel.Child toImmutable(TopLevelDto.ChildDto dto);
    TopLevelDto fromImmutable(TopLevel domain);
    TopLevelDto.ChildDto fromImmutable(TopLevel.Child domain);
}
