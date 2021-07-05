/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.ve;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.mapstruct.itest.immutables.TopLevelDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TopLevelMapper {

    TopLevelMapper INSTANCE = Mappers.getMapper( TopLevelMapper.class );

    TopLevelWithValueEnclosing toImmutable(TopLevelDto dto);
    TopLevelWithValueEnclosing.Child toImmutable(TopLevelDto.ChildDto dto);
    TopLevelDto fromImmutable(TopLevelWithValueEnclosing domain);
    TopLevelDto.ChildDto fromImmutable(TopLevelWithValueEnclosing.Child domain);
}
