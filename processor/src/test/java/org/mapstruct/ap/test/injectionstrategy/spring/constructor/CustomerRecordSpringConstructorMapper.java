/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.constructor;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerRecordDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerRecordEntity;

/**
 * @author Kevin Grüneberg
 */
@Mapper(componentModel = "spring",
    uses = { CustomerSpringConstructorMapper.class, GenderSpringConstructorMapper.class },
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    disableSubMappingMethodsGeneration = true)
public interface CustomerRecordSpringConstructorMapper {

    CustomerRecordDto asTarget(CustomerRecordEntity customerRecordEntity);
}
