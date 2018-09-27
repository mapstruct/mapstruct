/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluepropertymapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerNvpmsOnBeanMappingMethodMapper {

    CustomerNvpmsOnBeanMappingMethodMapper INSTANCE = Mappers.getMapper( CustomerNvpmsOnBeanMappingMethodMapper.class );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void map(Customer customer, @MappingTarget CustomerDTO mappingTarget);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "houseNumber", target = "houseNo")
    void mapCustomerHouse(Address address, @MappingTarget AddressDTO addrDTO);

}

