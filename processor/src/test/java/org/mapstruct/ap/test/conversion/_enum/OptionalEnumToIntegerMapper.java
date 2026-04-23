/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion._enum;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OptionalEnumToIntegerMapper {
    OptionalEnumToIntegerMapper INSTANCE = Mappers.getMapper( OptionalEnumToIntegerMapper.class );

    EnumToIntegerTarget sourceToTarget(OptionalEnumToIntegerSource source);

    OptionalEnumToIntegerSource targetToSource(EnumToIntegerTarget target);
}

