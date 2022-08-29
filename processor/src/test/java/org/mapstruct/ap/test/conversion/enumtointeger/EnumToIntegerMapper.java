/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.enumtointeger;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EnumToIntegerMapper {
    EnumToIntegerMapper INSTANCE = Mappers.getMapper( EnumToIntegerMapper.class );

    EnumToIntegerTarget sourceToTarget(EnumToIntegerSource source);

    EnumToIntegerSource targetToSource(EnumToIntegerTarget target);
}

