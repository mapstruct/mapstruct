/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring._default;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;

/**
 * @author Filip Hrisafov
 */
@Mapper(componentModel = "spring", uses = GenderSpringDefaultMapper.class )
public interface CustomerSpringDefaultMapper {

    CustomerDto asTarget(CustomerEntity customerEntity);
}
