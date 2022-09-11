/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.annotateWith.qualified;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;

/**
 * @author Filip Hrisafov
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = GenderSpringDefaultMapper.class )
public interface CustomerSpringDefaultMapper {

    CustomerDto asTarget(CustomerEntity customerEntity);
}
