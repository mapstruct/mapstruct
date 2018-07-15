/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.cdi;

import org.mapstruct.Mapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.itest.cdi.other.DateMapper;

@Mapper( componentModel = "cdi", uses = DateMapper.class )
public interface DecoratedSourceTargetMapper {

    Target sourceToTarget(Source source);

    Target undecoratedSourceToTarget(Source source);
}
