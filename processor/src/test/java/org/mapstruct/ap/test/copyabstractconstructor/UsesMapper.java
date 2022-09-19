/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.copyabstractconstructor;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = AbstractMapper.class )
public abstract class UsesMapper {

    public UsesMapper(Integer reference) {

    }

    abstract CTarget targetToSourceReversed(CSource source);

}
