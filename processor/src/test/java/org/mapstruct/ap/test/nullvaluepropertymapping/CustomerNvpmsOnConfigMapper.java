/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluepropertymapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = NvpmsConfig.class)
public interface CustomerNvpmsOnConfigMapper {

    CustomerNvpmsOnConfigMapper INSTANCE = Mappers.getMapper( CustomerNvpmsOnConfigMapper.class );

    void map(Customer customer, @MappingTarget CustomerDTO mappingTarget);

    @Mapping(source = "houseNumber", target = "houseNo")
    void mapCustomerHouse(Address address, @MappingTarget AddressDTO addrDTO);

}

