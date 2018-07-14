/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    TargetExt sourceToTarget(SourceExt source);

    void sourceToTargetWithTargetParameter(@MappingTarget TargetExt target, SourceExt source);

    TargetBase sourceToTargetWithTargetParameterAndReturn(SourceExt source, @MappingTarget TargetExt target);

    SourceExt targetToSource(TargetExt target);
}
