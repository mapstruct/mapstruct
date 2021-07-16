/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.dagger2._default;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;

@Mapper(componentModel = MappingConstants.ComponentModel.DAGGER2)
public interface GenderDagger2DefaultCompileOptionFieldMapper {

    @ValueMappings({
        @ValueMapping(source = "MALE", target = "M"),
        @ValueMapping(source = "FEMALE", target = "F")
    })
    GenderDto mapToDto(Gender gender);
}
