/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.compileoptionconstructor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;

/**
 * @author Andrei Arlou
 */
@Mapper( componentModel = "spring",
    uses = GenderSpringCompileOptionConstructorMapper.class)
public interface CustomerSpringCompileOptionConstructorMapper {

    @Mapping( source = "gender", target = "gender" )
    CustomerDto asTarget(CustomerEntity customerEntity);
}
