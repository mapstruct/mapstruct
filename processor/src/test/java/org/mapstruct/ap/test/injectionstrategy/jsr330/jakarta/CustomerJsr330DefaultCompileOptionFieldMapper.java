/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jsr330.jakarta;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;

/**
 * @author Andrei Arlou
 */
@Mapper(componentModel = MappingConstants.ComponentModel.JSR330,
    uses = GenderJsr330DefaultCompileOptionFieldMapper.class)
public interface CustomerJsr330DefaultCompileOptionFieldMapper {

    CustomerDto asTarget(CustomerEntity customerEntity);
}
