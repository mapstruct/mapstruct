/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.kora;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.KORA,
        uses = GenderKoraConstructorMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR )
public interface CustomerKoraConstructorMapper {

    @Mapping(target = "gender", source = "gender")
    CustomerDto asTarget(CustomerEntity customerEntity);
}