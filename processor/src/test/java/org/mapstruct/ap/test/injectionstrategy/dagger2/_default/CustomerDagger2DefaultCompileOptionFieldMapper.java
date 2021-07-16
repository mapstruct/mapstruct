/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.dagger2._default;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.DAGGER2,
    uses = GenderDagger2DefaultCompileOptionFieldMapper.class)
public interface CustomerDagger2DefaultCompileOptionFieldMapper {

    CustomerDto asTarget(CustomerEntity customerEntity);
}
