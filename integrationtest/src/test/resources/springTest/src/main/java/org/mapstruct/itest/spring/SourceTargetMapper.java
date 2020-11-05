/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.spring;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.itest.spring.other.DateMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = DateMapper.class)
public interface SourceTargetMapper {

    Target sourceToTarget(Source source);
}
