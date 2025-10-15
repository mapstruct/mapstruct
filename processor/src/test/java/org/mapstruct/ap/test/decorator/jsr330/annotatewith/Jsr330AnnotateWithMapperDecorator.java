/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.jsr330.annotatewith;

import javax.inject.Inject;
import javax.inject.Named;

import org.mapstruct.AnnotateWith;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;
import org.mapstruct.ap.test.decorator.TestAnnotation;

/**
 * A decorator for {@link Jsr330AnnotateWithMapper}.
 */
@AnnotateWith(value = TestAnnotation.class)
public abstract class Jsr330AnnotateWithMapperDecorator implements Jsr330AnnotateWithMapper {

    @Inject
    @Named("org.mapstruct.ap.test.decorator.jsr330.annotatewith.Jsr330AnnotateWithMapperImpl_")
    private Jsr330AnnotateWithMapper delegate;

    @Override
    public PersonDto personToPersonDto(Person person) {
        PersonDto dto = delegate.personToPersonDto( person );
        dto.setName( person.getFirstName() + " " + person.getLastName() );
        return dto;
    }
}
