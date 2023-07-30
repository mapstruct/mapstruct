/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.spring.setter;

import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class PersonMapperDecorator implements PersonMapper {

    private PersonMapper decoratorDelegate;

    @Override
    public PersonDto personToPersonDto(Person person) {
        PersonDto dto = decoratorDelegate.personToPersonDto( person );
        dto.setName( person.getFirstName() + " " + person.getLastName() );

        return dto;
    }

    @Autowired
    public void setDecoratorDelegate(@Qualifier("delegate") PersonMapper decoratorDelegate) {
        this.decoratorDelegate = decoratorDelegate;
    }
}
