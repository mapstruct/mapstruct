/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.value.spi.dto.CheeseTypePostfixed;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CheeseTypeMapper {

    CheeseTypeMapper INSTANCE = Mappers.getMapper( CheeseTypeMapper.class );

    CheeseTypePostfixed mapToPostfixed(CheeseType source);

    CheeseType mapFromPostfixed(CheeseTypePostfixed source);
}
