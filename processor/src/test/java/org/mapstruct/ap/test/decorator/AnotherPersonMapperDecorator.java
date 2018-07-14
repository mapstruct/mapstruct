/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator;

public abstract class AnotherPersonMapperDecorator implements AnotherPersonMapper {

    @Override
    public PersonDto personToPersonDto(Person person) {
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressLine( person.getAddress().getAddressLine() );

        PersonDto dto = new PersonDto();
        dto.setDateOfBirth( person.getDateOfBirth() );
        dto.setName( person.getFirstName() + " " + person.getLastName() );
        dto.setAddress( addressDto );

        return dto;
    }
}
