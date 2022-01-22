/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jakarta.compileoptionconstructor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;

/**
 * @author Filip Hrisafov
 */
@Mapper( componentModel = MappingConstants.ComponentModel.JAKARTA,
    uses = GenderJakartaCompileOptionConstructorMapper.class )
public interface CustomerJakartaCompileOptionConstructorMapper {

    @Mapping(target = "gender", source = "gender")
    CustomerDto asTarget(CustomerEntity customerEntity);

}
