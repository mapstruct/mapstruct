/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jsr330.constructor;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;

/**
 * @author Kevin Grüneberg
 */
@Mapper( componentModel = MappingConstants.ComponentModel.JSR330,
    uses = GenderJsr330ConstructorMapper.class,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR )
public interface CustomerJsr330ConstructorMapper {

    @Mapping(source = "gender", target = "gender")
    CustomerDto asTarget(CustomerEntity customerEntity);

}
