/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.dagger2;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.itest.dagger2.other.DateMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.DAGGER2, uses = DateMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(SecondSourceTargetMapperDecorator.class)
public interface SecondDecoratedSourceTargetMapper {

    Target sourceToTarget(Source source);

    Target undecoratedSourceToTarget(Source source);
}
