/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.jsr330.annotatewith;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.decorator.Address;
import org.mapstruct.ap.test.decorator.AddressDto;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;

/**
 * A mapper using JSR-330 component model with a decorator.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.JSR330)
@DecoratedWith(Jsr330AnnotateWithMapperDecorator.class)
public interface Jsr330AnnotateWithMapper {

    @Mapping(target = "name", ignore = true)
    PersonDto personToPersonDto(Person person);

    AddressDto addressToAddressDto(Address address);
}
