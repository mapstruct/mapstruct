/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.jsr330;

import org.mapstruct.Mapper;
import org.mapstruct.ComponentModel;
import org.mapstruct.DecoratedWith;
import org.mapstruct.itest.jsr330.other.DateMapper;

@Mapper(componentModel = ComponentModel.JSR330, uses = DateMapper.class)
@DecoratedWith(SecondSourceTargetMapperDecorator.class)
public interface SecondDecoratedSourceTargetMapper {

    Target sourceToTarget(Source source);

    Target undecoratedSourceToTarget(Source source);
}
