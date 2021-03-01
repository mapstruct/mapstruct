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

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerNvpmsOnMapperMapper {

    CustomerNvpmsOnMapperMapper INSTANCE = Mappers.getMapper( CustomerNvpmsOnMapperMapper.class );

    void map(Customer customer, @MappingTarget CustomerDTO mappingTarget);

    @Mapping(target = "houseNo", source = "houseNumber")
    void mapCustomerHouse(Address address, @MappingTarget AddressDTO addrDTO);

}
