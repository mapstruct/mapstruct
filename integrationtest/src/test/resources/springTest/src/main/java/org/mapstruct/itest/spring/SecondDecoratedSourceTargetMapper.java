/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.spring;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.DecoratedWith;
import org.mapstruct.itest.spring.other.DateMapper;

@Mapper( componentModel = MappingConstants.ComponentModel.SPRING, uses = DateMapper.class )
@DecoratedWith( SecondSourceTargetMapperDecorator.class )
public interface SecondDecoratedSourceTargetMapper {

    Target sourceToTarget(Source source);

    Target undecoratedSourceToTarget(Source source);
}
