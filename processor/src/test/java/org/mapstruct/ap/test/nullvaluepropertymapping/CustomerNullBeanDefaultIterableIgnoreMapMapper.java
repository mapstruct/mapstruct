/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluepropertymapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    nullValuePropertyIterableMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT,
    nullValuePropertyMapMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerNullBeanDefaultIterableIgnoreMapMapper {

    CustomerNullBeanDefaultIterableIgnoreMapMapper INSTANCE =
        Mappers.getMapper( CustomerNullBeanDefaultIterableIgnoreMapMapper.class );

    @Mapping(target = "homeDTO.addressDTO", source = "address")
    void mapCustomer(Customer customer, @MappingTarget UserDTO userDTO);

    @Mapping(target = "houseNo", defaultValue = "0", source = "houseNumber")
    void mapCustomerHouse(Address address, @MappingTarget AddressDTO addrDTO);

}
