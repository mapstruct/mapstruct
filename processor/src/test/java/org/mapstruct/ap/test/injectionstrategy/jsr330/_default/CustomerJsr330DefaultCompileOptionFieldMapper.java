/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jsr330._default;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;

/**
 * @author Andrei Arlou
 */
@Mapper(componentModel = "jsr330", uses = GenderJsr330DefaultCompileOptionFieldMapper.class)
public interface CustomerJsr330DefaultCompileOptionFieldMapper {

    CustomerDto asTarget(CustomerEntity customerEntity);
}
