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

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper
public interface ErroneousCustomerMapper2 {

    ErroneousCustomerMapper2 INSTANCE = Mappers.getMapper( ErroneousCustomerMapper2.class );

    @Mapping(target = "details", expression = "java(getTest())", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "address", nullValuePropertyMappingStrategy = IGNORE)
    void map(Customer customer, @MappingTarget CustomerDTO mappingTarget);

    @Mapping(target = "houseNo", source = "houseNumber")
    void mapCustomerHouse(Address address, @MappingTarget AddressDTO addrDTO);

}

