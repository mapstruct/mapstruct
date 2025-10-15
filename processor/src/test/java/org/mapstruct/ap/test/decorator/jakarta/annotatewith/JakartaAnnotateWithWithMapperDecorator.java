/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.jakarta.annotatewith;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.mapstruct.AnnotateWith;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;
import org.mapstruct.ap.test.decorator.TestAnnotation;

/**
 * A decorator for {@link JakartaAnnotateWithMapper}.
 */
@AnnotateWith(value = TestAnnotation.class)
public abstract class JakartaAnnotateWithWithMapperDecorator implements JakartaAnnotateWithMapper {

    @Inject
    @Named("org.mapstruct.ap.test.decorator.jakarta.annotatewith.JakartaAnnotateWithMapperImpl_")
    private JakartaAnnotateWithMapper delegate;

    @Override
    public PersonDto personToPersonDto(Person person) {
        PersonDto dto = delegate.personToPersonDto( person );
        dto.setName( person.getFirstName() + " " + person.getLastName() );
        return dto;
    }
}
