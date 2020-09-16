/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.style;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.mapstruct.itest.immutables.TopLevelDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FixedTopLevelMapper {
    FixedTopLevelMapper INSTANCE = Mappers.getMapper( FixedTopLevelMapper.class );

    FixedTopLevelWithStyle toImmutable(TopLevelDto dto);
    FixedTopLevelWithStyle.Child toImmutable(TopLevelDto.ChildDto dto);

    TopLevelDto fromImmutable(FixedTopLevelWithStyle domain);
}
