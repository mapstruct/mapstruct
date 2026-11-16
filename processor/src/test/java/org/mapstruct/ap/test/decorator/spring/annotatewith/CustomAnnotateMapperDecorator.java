/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.spring.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@AnnotateWith(value = CustomComponent.class, elements = @AnnotateWith.Element(strings = "customComponentDecorator"))
@AnnotateWith(value = CustomPrimary.class)
public abstract class CustomAnnotateMapperDecorator implements CustomAnnotateMapper {

    @Autowired
    @Qualifier("delegate")
    private CustomAnnotateMapper delegate;

    @Override
    public PersonDto personToPersonDto(Person person) {
        PersonDto dto = delegate.personToPersonDto( person );
        dto.setName( person.getFirstName() + " " + person.getLastName() );
        return dto;
    }
}
