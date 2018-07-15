/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.jsr330;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.decorator.Address;
import org.mapstruct.ap.test.decorator.AddressDto;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;

@Mapper(componentModel = "jsr330")
@DecoratedWith(PersonMapperDecorator.class)
public interface PersonMapper {

    @Mapping( target = "name", ignore = true )
    PersonDto personToPersonDto(Person person);

    AddressDto addressToAddressDto(Address address);
}
