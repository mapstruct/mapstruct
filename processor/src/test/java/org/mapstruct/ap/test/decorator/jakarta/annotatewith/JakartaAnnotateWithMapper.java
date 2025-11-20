/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.jakarta.annotatewith;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.decorator.Address;
import org.mapstruct.ap.test.decorator.AddressDto;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;

/**
 * A mapper using Jakarta component model with a decorator.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
@DecoratedWith(JakartaAnnotateWithWithMapperDecorator.class)
public interface JakartaAnnotateWithMapper {

    /**
     * Maps a person to a person DTO.
     *
     * @param person the person to map
     * @return the person DTO
     */
    @Mapping(target = "name", ignore = true)
    PersonDto personToPersonDto(Person person);

    /**
     * Maps an address to an address DTO.
     *
     * @param address the address to map
     * @return the address DTO
     */
    AddressDto addressToAddressDto(Address address);
}
