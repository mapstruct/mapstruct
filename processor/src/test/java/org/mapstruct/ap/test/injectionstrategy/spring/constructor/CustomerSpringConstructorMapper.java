/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.constructor;

import org.mapstruct.ComponentModel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;

/**
 * @author Kevin Grüneberg
 */
@Mapper( componentModel = ComponentModel.SPRING,
    uses = GenderSpringConstructorMapper.class,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR )
public interface CustomerSpringConstructorMapper {

    @Mapping( source = "gender", target = "gender" )
    CustomerDto asTarget(CustomerEntity customerEntity);
}
