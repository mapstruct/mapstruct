/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.compileoptionconstructor;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerRecordDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerRecordEntity;

/**
 * @author Andrei Arlou
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    uses = { CustomerSpringCompileOptionConstructorMapper.class, GenderSpringCompileOptionConstructorMapper.class },
    disableSubMappingMethodsGeneration = true)
public interface CustomerRecordSpringCompileOptionConstructorMapper {

    CustomerRecordDto asTarget(CustomerRecordEntity customerRecordEntity);
}
