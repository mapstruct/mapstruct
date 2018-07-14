/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator;

public abstract class Person2MapperDecorator extends Person2Mapper {

    private final Person2Mapper delegate;

    public Person2MapperDecorator(Person2Mapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public PersonDto2 personToPersonDto(Person2 person) {

        PersonDto2 dto = delegate.personToPersonDto( person );
        dto.setName( person.getFirstName() + " " + person.getLastName() );

        return dto;
    }
}
