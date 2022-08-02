/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.style2;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.itest.immutables.AddressDto;

@Mapper
public interface AddressWithStyleMapper {

    AddressWithStyleMapper INSTANCE = Mappers.getMapper(AddressWithStyleMapper.class);

    AddressWithStyle fromDto(AddressDto addressDto);
    AddressDto toDto(AddressWithStyle address);
}
