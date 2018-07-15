/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.jsr330;

import org.mapstruct.Mapper;
import org.mapstruct.itest.jsr330.other.DateMapper;

@Mapper(componentModel = "jsr330", uses = DateMapper.class)
public interface SourceTargetMapper {

    Target sourceToTarget(Source source);
}
