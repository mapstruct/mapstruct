/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring._default;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;

/**
 * @author Filip Hrisafov
 */
@Mapper(componentModel = "spring")
public interface GenderSpringDefaultMapper {

    @ValueMapping(source = "MALE", target = "M")
    @ValueMapping(source = "FEMALE", target = "F")
    GenderDto mapToDto(Gender gender);
}
