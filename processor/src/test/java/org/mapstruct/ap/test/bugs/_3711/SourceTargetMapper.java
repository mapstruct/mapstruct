/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3711;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceTargetMapper extends BaseMapper<ParentDto, ParentEntity> {
    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    ParentEntity toDTO(ParentDto dto);
}
