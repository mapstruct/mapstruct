/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.setter;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerRecordDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerRecordEntity;

/**
 * @author Lucas Resch
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    uses = { CustomerSpringSetterMapper.class, GenderSpringSetterMapper.class },
    injectionStrategy = InjectionStrategy.SETTER)
public interface CustomerRecordSpringSetterMapper {

    CustomerRecordDto asTarget(CustomerRecordEntity customerRecordEntity);
}
