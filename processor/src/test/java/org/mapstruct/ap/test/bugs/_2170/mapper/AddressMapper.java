/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2170.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._2170.dto.AddressDto;
import org.mapstruct.ap.test.bugs._2170.entity.Address;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
//CHECKSTYLE:OFF
@Mapper(uses = { PersonMapper.class })
//CHECKSTYLE:ON
public interface AddressMapper extends EntityMapper<AddressDto, Address> {

    AddressMapper INSTANCE = Mappers.getMapper( AddressMapper.class );

}
